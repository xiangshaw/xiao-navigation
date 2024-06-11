package cn.coisini.navigation.controller.v1;

import cn.coisini.navigation.common.log.annotation.Log;
import cn.coisini.navigation.common.log.enums.BusinessType;
import cn.coisini.navigation.common.log.service.AsyncOperLogService;
import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.AsyncOperLog;
import cn.coisini.navigation.model.vo.AsyncOperLogQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: xiaoxiang
 * Description: 操作日志记录 前端控制器
 */
@Api(tags = "操作日志记录")
@RestController
@RequestMapping("/api/v1/operLog")
public class AsyncOperLogController {
    private final AsyncOperLogService asyncOperLogService;

    public AsyncOperLogController(AsyncOperLogService asyncOperLogService) {
        this.asyncOperLogService = asyncOperLogService;
    }

    @ApiOperation("条件分页查询操作日志")
    @PreAuthorize("hasAuthority('operLog.list')")
    @GetMapping("/list")
    public Result<AsyncOperLog> pagingQuery(
           @RequestBody AsyncOperLogQueryVo asyncOperLogQueryVo) {
        return asyncOperLogService.pagingQuery(asyncOperLogQueryVo);
    }

    @ApiOperation("根据id查询操作日志")
    @GetMapping("/get/{id}")
    public Result<AsyncOperLog> getOpenLog(@PathVariable("id") String id) {
        return asyncOperLogService.get(id);
    }

    @ApiOperation("根据id删除操作日志")
    @PreAuthorize("hasAuthority('operLog.remove')")
    @Log(title = "操作日志管理",businessType = BusinessType.DELETE)
    @DeleteMapping("/remove/{id}")
    public Result<AsyncOperLog> removeOperLogById(@PathVariable("id") String id) {
        return asyncOperLogService.removeOperLogById(id);
    }

    @ApiOperation("批量删除操作日志")
    @PreAuthorize("hasAuthority('operLog.batchRemove')")
    @Log(title = "操作日志管理",businessType = BusinessType.BATCH_REMOVE)
    @DeleteMapping("/batchRemove")
    public Result<AsyncOperLog> batchRemove(@RequestBody List<String> ids) {
        return asyncOperLogService.batchRemove(ids);
    }
}
