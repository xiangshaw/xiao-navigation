package cn.coisini.navigation.controller.v1;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.common.enums.ResultEnum;
import cn.coisini.navigation.utils.FastDfsClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Author: xiaoxiang
 * Description: 文件上传
 */
@CrossOrigin
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
            // 只存路径
            Result<String> result = new Result<>();
            // http://192.168.20.128/
            result.setHost(fileServerUrl);
            // group1/M00/00/00/wKgUgGTdAg-AKc1bAALxXtyM8xw319.ico
            result.setData(fileId);
            return result;
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
