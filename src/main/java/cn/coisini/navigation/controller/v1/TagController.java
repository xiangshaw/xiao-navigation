package cn.coisini.navigation.controller.v1;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.SortTag;
import cn.coisini.navigation.model.pojos.Tag;
import cn.coisini.navigation.model.vo.QueryVo;
import cn.coisini.navigation.model.vo.SortTagVo;
import cn.coisini.navigation.service.TagService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Author: xiaoxiang
 * Description: 标签 - 控制器层
 */
@Api(tags = "标签管理")
@Validated
@RestController
@RequestMapping("/api/v1/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    @ApiOperation("根据Id查询标签")
    Result<Tag> findIdByTag(@ApiParam("标签Id") @PathVariable("id") String id){
        return tagService.findIdByTag(id);
    }

    @GetMapping("/list")
    @ApiOperation("查询所有标签")
    public Result<Tag> qbcTag(QueryVo tagQueryVo){
        return tagService.qbcTag(tagQueryVo);
    }

    @PreAuthorize("hasAuthority('tag:add')")
    @PostMapping("/save")
    @ApiOperation("保存标签")
    public Result<Tag> saveTag(@RequestBody @Validated Tag tag){
        return tagService.saveTag(tag);
    }

    @PreAuthorize("hasAuthority('tag:update')")
    @PatchMapping("/update")
    @ApiOperation("更新标签")
    public Result<Tag> updateTag(@RequestBody Tag tag){
        return tagService.updateTag(tag);
    }

    @PreAuthorize("hasAuthority('tag:remove')")
    @DeleteMapping("/remove/{id}")
    @ApiOperation("删除标签")
    public Result<Tag> removeTag(@ApiParam("标签Id") @PathVariable("id") String id){
        return tagService.removeTag(id);
    }

    @PreAuthorize("hasAuthority('tag:batchRemove')")
    @ApiOperation("批量删除标签")
    @DeleteMapping("/batchRemove")
    public Result<List<Tag>> batchRemoveRole(@RequestBody List<String> ids) {
        return tagService.batchRemove(ids);
    }

    @PreAuthorize("hasAuthority('tag:status')")
    @GetMapping("/updateStatus/{id}/{status}")
    @ApiOperation("更改标签状态(0启用 1禁用)")
    public Result<Tag> status(@PathVariable("id") String id,
                               @PathVariable("status") Boolean status) {
        return tagService.updateStatus(id, status);
    }

    @ApiOperation("根据标签id获取类别信息数据")
    @GetMapping("/toAssign/{id}")
    public Result<Map<String, Object>> getRolesByUserId(@PathVariable("id") String id) {
        return tagService.getSortsByTagId(id);
    }
    @PreAuthorize("hasAuthority('tag:assignSort')")
    @ApiOperation("给标签分配类别")
    @PostMapping("/doAssign")
    public Result<SortTag> doAssign(@RequestBody SortTagVo sortTagVo) {
        return tagService.saveSortTag(sortTagVo);
    }

}
