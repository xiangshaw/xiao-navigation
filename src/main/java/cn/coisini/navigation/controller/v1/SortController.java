package cn.coisini.navigation.controller.v1;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.Sort;
import cn.coisini.navigation.model.vo.SortQueryVo;
import cn.coisini.navigation.service.SortService;
import io.swagger.annotations.Api;
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
    Result<Sort> findIdBySort(@PathVariable("id") String id){
        return sortService.findIdBySort(id);
    }

    @GetMapping("/list")
    public Result<Sort> qbcSort(@RequestBody SortQueryVo sortQueryVo){
        return sortService.qbcSort(sortQueryVo);
    }
    @PostMapping("/save")
    public Result<Sort> saveSort(@RequestBody Sort sort){
        return sortService.saveSort(sort);
    }
    @PutMapping("/update")
    public Result<Sort> updateSort(@RequestBody Sort sort){
        return sortService.updateSort(sort);
    }
    @DeleteMapping("/remove/{id}")
    public Result<Sort> removeSort(@PathVariable("id") String id){
        return sortService.removeSort(id);
    }

}
