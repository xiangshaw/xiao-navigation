package cn.coisini.navigation.config;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Author: xiaoxiang
 * Description: FastDFSClient配置类
 */
@Configuration
@Import(FdfsClientConfig.class) // 导入FastDFS-Client组件
@PropertySource("classpath:fast_dfs.properties")
public class FdfsConfiguration {
}
