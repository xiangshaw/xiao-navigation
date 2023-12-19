package cn.coisini.navigation.utils;

import cn.coisini.navigation.model.common.dto.Result;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
     *
     * @param file 文件
     * @return java.lang.String
     * @author xiaoxiang
     */
    public String uploadFile(MultipartFile file) throws IOException {
        StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(),
                FilenameUtils.getExtension(file.getOriginalFilename()), null);
        return storePath.getFullPath();
    }

    /**
     * 删除
     *
     * @param filePath 地址
     * @author xiaoxiang
     */
    public void delFile(String filePath) {
        storageClient.deleteFile(filePath);
    }

    /**
     * 下载
     *
     * @param fileId 文件地址
     * @author xiaoxiang
     */
    public void downLoadFile(String fileId) {
        StorePath storePath = StorePath.parseFromUrl(fileId);
        String substring = fileId.substring(fileId.lastIndexOf("."));
        byte[] data = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置下载响应类型以及文件名
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        httpHeaders.setContentDispositionFormData("attachment", "file" + substring);
        Result.ok(data);
    }
}
