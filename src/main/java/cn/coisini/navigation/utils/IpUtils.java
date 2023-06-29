package cn.coisini.navigation.utils;


import com.alibaba.fastjson2.JSONObject;
import eu.bitwalker.useragentutils.UserAgent;
import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Objects;

/**
 * Author: xiaoxiang
 * Description: 获取IP
 */
public class IpUtils {
    private IpUtils() {
        throw new IllegalArgumentException("IpUtils class");
    }

    private static final Logger logger = LoggerFactory.getLogger(IpUtils.class);
    private static final String FORMAT_URL = "https://apis.map.qq.com/ws/location/v1/ip?ip={}&key=4TPBZ-BMT6G-6MTQX-QLTMP-NL2GK-DLB7U";
    private static final String LOCAL_IP = "127.0.0.1";
    private static final String RESOURCE_NAME = "ip2region.xdb";
    public static final String UNKNOWN = "unknown";


    private static Searcher searcher = null;

    static {
        // 1、从 dbPath 加载整个 xdb 到内存。
        byte[] cBuff = new byte[0];
        try {
            String path = Objects.requireNonNull(IpUtils.class.getClassLoader().getResource(RESOURCE_NAME)).getPath();
            cBuff = Searcher.loadContentFromFile(path);
        } catch (Exception e) {
            logger.error("failed to load content from `%s`: %s\n", RESOURCE_NAME, e);
        }

        // 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
        try {
            searcher = Searcher.newWithBuffer(cBuff);
        } catch (Exception e) {
            logger.error("failed to create content cached searcher: %s\n", e);
        }
    }

    /**
     * 获取ip地址
     */
    public static String getIp(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || UNKNOWN.equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (LOCAL_IP.equals(ipAddress)) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                        ipAddress = inet.getHostAddress();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return "0:0:0:0:0:0:0:1".equals(ipAddress) ? LOCAL_IP : ipAddress;
    }

    /**
     * 解析ip地址
     *
     * @param ip ip地址
     * @return 解析后的ip地址
     */
    public static String getCityInfo(String ip) {
        // 解析ip地址，获取省市区
        String s = analyzeIp(ip);
        Map map = JSONObject.parseObject(s, Map.class);
        Integer status = (Integer) map.get("status");
        String address = "";
        if (status == 0) {
            Map result = (Map) map.get("result");
            Map addressInfo = (Map) result.get("ad_info");
            String nation = (String) addressInfo.get("nation");
            String province = (String) addressInfo.get("province");
            String city = (String) addressInfo.get("city");
            address = nation + "-" + province + "-" + city;
        }
        return address;
    }

    /**
     * 根据ip2region解析ip地址
     *
     * @param ip ip地址
     * @return 解析后的ip地址信息
     */
    public static String getIp2region(String ip) {

        if (searcher == null) {
            logger.error("Error: DbSearcher is null");
            return null;
        }

        try {
            String ipInfo = searcher.search(ip);
            if (!Objects.isNull(ipInfo)) {
                ipInfo = ipInfo.replace("|0", "");
                ipInfo = ipInfo.replace("0|", "");
            }
            return ipInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取访问设备
     *
     * @param request 请求
     * @return {@link UserAgent} 访问设备
     */
    public static UserAgent getUserAgent(HttpServletRequest request) {
        return UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
    }

    /**
     * 获取IP地址
     *
     * @return 本地IP地址
     */
    public static String getHostIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return LOCAL_IP;
        }
    }

    /**
     * 获取主机名
     *
     * @return 本地主机名
     */
    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "未知";
        }
    }

    /**
     * 根据在腾讯位置服务上申请的key进行请求解析ip
     *
     * @param ip ip地址
     * @return
     */
    public static String analyzeIp(String ip) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String url = FORMAT_URL.replace("{}", ip);
            URL realUrl = new URL(url);
            // 打开和URL之间的链接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 创建实际的链接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            logger.error("发送GET请求出现异常！异常信息为:{}", e.getMessage());
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result.toString();
    }
}

