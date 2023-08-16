package cn.coisini.navigation.controller.v1;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.common.enums.ResultEnum;
import cn.coisini.navigation.utils.FastDfsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Author: xiaoxiang
 * Description: 文件上传
 */
@RestController
@RequestMapping("/api/v1/file")
public class FileController {

    private final FastDfsClient fastDfsClient;

    @Value("${fdfs.url}")
    private String fileServerUrl;

    public FileController(FastDfsClient fastDfsClient) {
        this.fastDfsClient = fastDfsClient;
    }

    @PostMapping("/fastdfs/upload")
    public Result<String> uploadFile(MultipartFile file) {/*形参 file*/
        try {
            //判断文件是否存在
            if (file == null) {
                throw new IllegalArgumentException("文件不存在");
            }
            String fileId = fastDfsClient.uploadFile(file);
            // http://192.168.20.128/group1/M00/00/00/wKgUgGTdAg-AKc1bAALxXtyM8xw319.ico
            return Result.ok(fileServerUrl + fileId);
        } catch (IOException e) {
            e.printStackTrace();
            return Result.error(ResultEnum.FAIL, "文件上传失败");
        }

    }

    @GetMapping("/fastdfs/remove")
    public Result<String> delFile(String fileId) {
        // /fastdfs/remove?fileId=group1/M00/00/00/wKgUgGTdAg-AKc1bAALxXtyM8xw319.ico
        try {
            fastDfsClient.delFile(fileId);
            return Result.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(ResultEnum.FAIL, "删除失败");
        }
    }
}
