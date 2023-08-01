package cn.coisini.navigation.controller.v1;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.Sort;
import cn.coisini.navigation.model.vo.QueryVo;
import cn.coisini.navigation.service.SortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

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
    Result<Sort> findIdBySort(@ApiParam("类别Id") @PathVariable("id") String id){
        return sortService.findIdBySort(id);
    }

    @GetMapping("/list")
    @ApiOperation("查询所有类别")
    public Result<Sort> qbcSort(@RequestBody QueryVo sortQueryVo){
        return sortService.qbcSort(sortQueryVo);
    }
    @PostMapping("/save")
    @ApiOperation("保存类别")
    public Result<Sort> saveSort(@RequestBody Sort sort){
        return sortService.saveSort(sort);
    }
    @PatchMapping("/update")
    @ApiOperation("更新类别")
    public Result<Sort> updateSort(@RequestBody Sort sort){
        return sortService.updateSort(sort);
    }
    @DeleteMapping("/remove/{id}")
    @ApiOperation("删除类别")
    public Result<Sort> removeSort(@ApiParam("类别Id") @PathVariable("id") String id){
        return sortService.removeSort(id);
    }

}
