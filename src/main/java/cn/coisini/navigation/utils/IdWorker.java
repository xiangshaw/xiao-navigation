package cn.coisini.navigation.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.NetworkInterface;

/**
 * 分布式自增长ID
 * 核心代码为其IdWorker这个类实现，其原理结构如下，我分别用一个0表示一位，用—分割开部分的作用：
 * 1||0---0000000000 0000000000 0000000000 0000000000 0 --- 00000 ---00000 ---000000000000
 * 在上面的字符串中，第一位为未使用（实际上也可作为long的符号位），接下来的41位为毫秒级时间，
 * 然后5位datacenter标识位，5位机器ID（并不算标识符，实际是为线程标识），
 * 然后12位该毫秒内的当前毫秒内的计数，加起来刚好64位，为一个Long型。
 * 这样的好处是，整体上按照时间自增排序，并且整个分布式系统内不会产生ID碰撞（由datacenter和机器ID作区分），
 * 并且效率较高，经测试，snowflake每秒能够产生26万ID左右，完全满足需要。
 * 64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
 */
public class IdWorker {
    private static final Logger logger = LoggerFactory.getLogger(IdWorker.class);
    // 时间起始标记点，作为基准，一般取系统的最近时间（一旦确定不能变动）
    /**
     * 属性不能为null 选long 可以为null 选Long
     * 基本类型long 不能为null 初始值为0
     * long的包装类Long 可以为null，初始值为null
     * long可给Long赋值 Long给long赋值时要判断是否null 如果null赋值给long 则会抛空指针异常
     */

    // 由于时间戳只能用69年，我们的计时又是从1970年开始的，所以这个TWEPOCH表示从项目开始的时间，用生成ID的时间减去TWEPOCH作为时间戳，可以使用更久。
    // 开始时间戳 2023-05-20 13:14:00
    private static final long TWEPOCH = 1684559640L;
    // 机器位或者机房位，一共10 bit，如果全部表示机器，那么可以表示1024台机器，如果拆分，5 bit 表示机房，5bit表示机房里面的机器，那么可以有32个机房，每个机房可以用32台机器。
    // 机器ID标识位数 最大5个bit 最大：11111（二进制）--> 31（十进制）
    private static final long WORKER_ID_BITS = 5L;
    // 数据中心的ID标识位数 最大5个bit 最大：11111（二进制）--> 31（十进制）
    private static final long DATA_CENTER_ID_BITS = 5L;
    // 机器ID最大值（这个移位算法可以很快计算出几位二进制数所能表示的最大十进制数，例如：5 bit最多有31个数字，则机器ID最多32以内）
    /**
     * -1L ^ (-1L << x) 表示什么？
     * 表示 x 位二进制可以表示多少个数值，假设x为3：
     * 在计算机中，第一位是符号位，负数的反码是除了符号位，1变0，0变1, 而补码则是反码+1：
     * -1L 原码：1000 0001
     * -1L 反码：1111 1110
     * -1L 补码：1111 1111
     * 从上面的结果可以知道，-1L其实在二进制里面其实就是全部为1,那么 -1L 左移动 3位，其实得到 1111 1000，也就是最后3位是0，
     * 再与-1L异或计算之后，其实得到的，就是后面3位全是1。
     * -1L ^ (-1L << x) 表示的其实就是x位全是1的值，也就是x位的二进制能表示的最大数值。
     */
    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    // 数据中心ID最大值
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    // 毫秒内自增位（同一时间的序列所占的位数 12个bit 111111111111 = 4095 同一毫秒最多生成4096个）
    private static final long SEQUENCE_BITS = 12L;
    // 机器ID偏左移12位
    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;
    // 数据中心ID左移17位（12+5）
    private static final long DATA_CENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;
    // 时间毫秒左移22位（5+5+12）
    private static final long TIME_STAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATA_CENTER_ID_BITS;
    // 生成序列的掩码（用于序号的与运算，保证序号最大值在0-4095之间）序列号掩码4095（0b111111111111=0xfff=4095）
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);
    // 上次生产ID时间戳
    private static long lastTimestamp = -1L;
    // 0，并发控制，毫秒内序列(0~生成序列的掩码)
    private long sequence = 0L;
    // 工作机器ID(0~31)
    private final long workerId;
    // 数据标识id部分，数据中心ID(0~31)
    private final long datacenterId;

    public IdWorker() {
        this.datacenterId = getDatacenterId();
        this.workerId = getMaxWorkerId(datacenterId);
    }

    /**
     * @param workerId     工作机器ID
     * @param datacenterId 序列号
     *                     在计算机的表示中，第一位是符号位，0表示整数，第一位如果是1则表示负数，我们用的ID默认就是正数，所以默认就是0，那么这一位默认就没有意义。
     */
    public IdWorker(long workerId, long datacenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", MAX_WORKER_ID));
        }
        if (datacenterId > MAX_DATA_CENTER_ID || datacenterId < 0) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", MAX_DATA_CENTER_ID));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 获取下一个ID（方法线程安全）
     */
    public synchronized long nextId() {
        long timestamp = timeGen();
        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }
        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            // 当前毫秒内，则+1
            sequence = (sequence + 1) & SEQUENCE_MASK;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 当前毫秒内计数满了，则等待下一秒
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            // 时间戳改变，毫秒内序列重置
            sequence = 0L;
        }
        // 上次生成ID的时间截
        lastTimestamp = timestamp;
        // ID偏移组合生成最终的ID，并返回ID（移位并通过或运算拼到一起组成64位的ID）
        // nextId
        return ((timestamp - TWEPOCH) << TIME_STAMP_LEFT_SHIFT)
                | (datacenterId << DATA_CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT) | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    private long tilNextMillis(final long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    private long timeGen() {
        return System.currentTimeMillis();
    }

    /**
     * 获取 maxWorkerId
     */
    protected static long getMaxWorkerId(long datacenterId) {
        // 同步
        StringBuffer mpid = new StringBuffer();
        mpid.append(datacenterId);
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (!name.isEmpty()) {
            /*
             * GET jvmPid
             */
            mpid.append(name.split("@")[0]);
        }
        /*
         * MAC + PID 的 hashcode 获取16个低位
         */
        return (mpid.toString().hashCode() & 0xffff) % (IdWorker.MAX_WORKER_ID + 1);
    }

    /**
     * 数据标识id部分，获取数据中心ID
     */
    protected static long getDatacenterId() {
        long id = 0L;
        try {
            InetAddress ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            if (network == null) {
                id = 1L;
            } else {
                byte[] mac = network.getHardwareAddress();
                id = ((0x000000FF & (long) mac[mac.length - 1])
                        | (0x0000FF00 & (((long) mac[mac.length - 2]) << 8))) >> 6;
                id = id % (IdWorker.MAX_DATA_CENTER_ID + 1);
            }
        } catch (Exception e) {
            logger.error("getDatacenterId: {}", e.getMessage());
        }
        return id;
    }


    public static void main(String[] args) {

        IdWorker idWorker = new IdWorker(0, 0);
        for (int i = 0; i < 10000; i++) {
            long nextId = idWorker.nextId();
            System.out.println(nextId);
        }
    }
}