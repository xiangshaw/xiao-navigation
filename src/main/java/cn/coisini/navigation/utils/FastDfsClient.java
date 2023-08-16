package cn.coisini.navigation.utils;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Author: xiaoxiang
 * Description: FastDFSClient客户端
 */
@Component
public class FastDfsClient {
    private final FastFileStorageClient storageClient;

    public FastDfsClient(FastFileStorageClient storageClient) {
        this.storageClient = storageClient;
    }

    /**
     * 上传
     * @author xiaoxiang
     * @param file 文件
     * @return java.lang.String
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return storePath.getFullPath();
    }

    /**
     * 删除
     * @author xiaoxiang
     * @param filePath 地址
     */
    public void delFile(String filePath) {
        storageClient.deleteFile(filePath);
    }
}
