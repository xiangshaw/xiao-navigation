package cn.coisini.navigation.controller.v1;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.Sort;
import cn.coisini.navigation.model.vo.QueryVo;
import cn.coisini.navigation.service.SortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author: xiaoxiang
 * Description: 类别 - 控制器层
 */
@Api(tags = "类别管理")
@RestController
@RequestMapping("/api/v1/sort")
public class SortController {
    private final SortService sortService;

    public SortController(SortService sortService) {
        this.sortService = sortService;
    }

    @GetMapping("/{id}")
    @ApiOperation("根据Id查询类别")
    Result<Sort> findIdBySort(@ApiParam("类别Id") @PathVariable("id") String id) {
        return sortService.findIdBySort(id);
    }

    @PreAuthorize("hasAuthority('sort:list')")
    @GetMapping("/list")
    @ApiOperation("查询所有类别")
    public Result<Sort> qbcSort(QueryVo sortQueryVo) {
        return sortService.qbcSort(sortQueryVo);
    }

    @PreAuthorize("hasAuthority('sort:add')")
    @PostMapping("/save")
    @ApiOperation("保存类别")
    public Result<Sort> saveSort(@RequestBody Sort sort) {
        return sortService.saveSort(sort);
    }

    @PreAuthorize("hasAuthority('sort:update')")
    @PatchMapping("/update")
    @ApiOperation("更新类别")
    public Result<Sort> updateSort(@RequestBody Sort sort) {
        return sortService.updateSort(sort);
    }

    @PreAuthorize("hasAuthority('sort:remove')")
    @DeleteMapping("/remove/{id}")
    @ApiOperation("删除类别")
    public Result<Sort> removeSort(@ApiParam("类别Id") @PathVariable("id") String id) {
        return sortService.removeSort(id);
    }

    @PreAuthorize("hasAuthority('sort:batchRemove')")
    @ApiOperation("批量删除类别")
    @DeleteMapping("/batchRemove")
    public Result<List<Sort>> batchRemoveRole(@RequestBody List<String> ids) {
        return sortService.batchRemove(ids);
    }

    @PreAuthorize("hasAuthority('sort:status')")
    @GetMapping("/updateStatus/{id}/{status}")
    @ApiOperation("更改类别状态(0启用 1禁用)")
    public Result<Sort> status(@PathVariable("id") String id,
                               @PathVariable("status") Boolean status) {
        return sortService.updateStatus(id, status);
    }
    @GetMapping("/sortTag")
    @ApiOperation("首页类别图标加载")
    public Result<Object> qbcSortTag(){
        return sortService.qbcSortTag();
    }

}
