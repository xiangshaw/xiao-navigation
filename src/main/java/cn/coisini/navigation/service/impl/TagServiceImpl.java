package cn.coisini.navigation.service.impl;

import cn.coisini.navigation.mapper.SortMapper;
import cn.coisini.navigation.mapper.SortTagMapper;
import cn.coisini.navigation.mapper.TagMapper;
import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.common.enums.ResultEnum;
import cn.coisini.navigation.model.pojos.Sort;
import cn.coisini.navigation.model.pojos.SortTag;
import cn.coisini.navigation.model.pojos.Tag;
import cn.coisini.navigation.model.vo.QueryVo;
import cn.coisini.navigation.model.vo.SortTagVo;
import cn.coisini.navigation.model.vo.TagVo;
import cn.coisini.navigation.service.TagService;
import cn.coisini.navigation.utils.IdWorker;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: xiaoxiang
 * Description: 标签 - 接口实现类
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    private final SortTagMapper sortTagMapper;
    private final SortMapper sortMapper;

    public TagServiceImpl(SortTagMapper sortTagMapper,SortMapper sortMapper) {
        this.sortTagMapper = sortTagMapper;
        this.sortMapper = sortMapper;
    }

    // 根据ID查询标签
    @Override
    public Result<Tag> findIdByTag(String id) {
        // 1.检查参数
        if (id == null){
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        Tag tag = getById(id);
        // 检查结果
        if (tag == null){
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        return Result.ok(tag);
    }

    // 查询所有标签
    @Override
    public Result<Tag> qbcTag(QueryVo queryVo) {
        // 获取条件
        String tagName = queryVo.getKeyword();
        // 封装条件
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        // 根据 标签名称 模糊查询
        if (!CharSequenceUtil.isEmpty(tagName)) {
            wrapper.like("tag_name", tagName);
        }
        // 排序
        wrapper.eq("status",0).orderByDesc("tag_id");
        // 分页条件 当前页-每页条数
        Page<Tag> page = new Page<>(queryVo.getCurrent(), queryVo.getLimit());
        Page<Tag> tagPage = page(page, wrapper);
        // 总条数
        tagPage.setTotal(tagPage.getRecords().size());
        // 返回结果
        return Result.ok(tagPage);
    }

    // 新增标签
    @Override
    public Result<Tag> saveTag(Tag tag) {
        // 1.检查参数
        if (tag == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.查询数据库
        List<Tag> list = list(Wrappers.lambdaQuery(Tag.class).eq(Tag::getTagName, tag.getTagName()));
        if (list != null && list.size() == 1) {
            return Result.error(ResultEnum.DATA_EXIST, "该标签已经添加");
        }
        // 3.检查结果
        IdWorker worker = new IdWorker();
        // 生成分布式ID
        tag.setTagId(String.valueOf(worker.nextId()));
        tag.setCreateTime(new Date());
        boolean b = save(tag);
        if (b){
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "标签添加失败");
    }

    // 标签修改
    @Override
    public Result<Tag> updateTag(Tag tag) {
        // 1.检查参数
        if (tag == null && tag.getTagId() == null){
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.修改并检查结果
        boolean b = updateById(tag);
        if (b){
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL,"修改失败");
    }

    /**
     * 修改标签
     * Author xiaoxiang
     */
    private boolean updateTags(TagVo tagVo) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_id",tagVo.getTagId());
        return updateById(wrapper.getEntity());
    }

    // 标签删除
    @Override
    public Result<Tag> removeTag(String id) {
        // 1.检查参数
        if (id == null){
            return Result.error(ResultEnum.PARAM_REQUIRE);
        }
        Tag tag = getById(id);
        if (tag == null ){
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }

        if (Boolean.TRUE.equals(tag.getStatus())){
            return Result.error(ResultEnum.FAIL,"标签有效，不能删除");
        }
        // 2.执行删除并检查结果
        boolean b = removeById(id);
        if (b){
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL,"删除失败");
    }

    // 根据标签Id获取类别数据
    @Override
    public Result<Map<String, Object>> getSortsByTagId(String id) {
        // 1.检查参数
        if (id == null){
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.获取所有类别
        List<Sort> sortList = sortMapper.selectList(null);
        // 3.根据标签id查询已经关联的类别
        List<SortTag> tagList = sortTagMapper.selectList(new QueryWrapper<SortTag>().eq("tag_id", id));
        // 4.获取所有类别id
        List<String> sortIds = tagList.stream().map(SortTag::getSortId).collect(Collectors.toList());
        // 5.封装到map
        HashMap<String, Object> map = new HashMap<>();
        // 5.1所有类别
        map.put("allSort", sortList);
        // 5.2标签分配类别id集合
        map.put("tagSortIds", sortIds);
        return Result.ok(map);
    }

    // 新增标签与类别
    @Override
    public Result<SortTag> saveSortTag(SortTagVo sortTagVo) {
        // 1.检查参数
        if (sortTagVo.getTagId() == null){
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.根据标签id删除原来分配的类别
        sortTagMapper.delete(new QueryWrapper<SortTag>().eq("tag_id", sortTagVo.getTagId()));
        // 3.获取所有类别id,添加类别标签关系表
        List<String> sortIdList = sortTagVo.getSortIdList();
        sortIdList.forEach(sortId ->{
            SortTag sortTag = new SortTag();
            sortTag.setId(String.valueOf(new IdWorker().nextId()));
            sortTag.setTagId(sortTagVo.getTagId());
            sortTag.setSortId(sortId);
            sortTagMapper.insert(sortTag);
        });
        return Result.ok(ResultEnum.SUCCESS);
    }
}
