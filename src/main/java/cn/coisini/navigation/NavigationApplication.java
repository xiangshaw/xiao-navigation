package cn.coisini.navigation;

import cn.coisini.navigation.utils.IdWorker;
import cn.hutool.core.text.CharSequenceUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.net.UnknownHostException;

/**
 * Author: xiaoxiang
 * Description: 启动类
 */
@SpringBootApplication
public class NavigationApplication {
    @Value("${workId}")
    private int workId;
    @Value("${datacenterId}")
    private int datacenterId;

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(NavigationApplication.class, args);
        //获取访问地址
        Environment environment = context.getBean(Environment.class);
        //environment.getProperty("server.servlet.context-path") 应用的上下文路径，也可以称为项目路径
        // 获取当前IP
//        String host = InetAddress.getLocalHost().getHostAddress();
        String name = environment.getProperty("spring.application.name");
        String host = environment.getProperty("ipaddr");
        String port = environment.getProperty("server.port");
        String path = environment.getProperty("server.servlet.context-path");
        if (CharSequenceUtil.isEmpty(path)) {
            path = "";
        }
        System.out.println("\n启动成功~ 欢迎使用：" + name);
        System.out.println("\n访问地址：" + host + ":" + port + path);
        System.out.println("\n接口文档地址：" + host + ":" + port + path + "/doc.html");
    }

    // 分布式ID
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(workId, datacenterId);
    }

}
