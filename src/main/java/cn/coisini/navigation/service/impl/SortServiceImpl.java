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
import cn.coisini.navigation.model.vo.TagInfoVo;
import cn.coisini.navigation.service.SortService;
import cn.coisini.navigation.utils.IdWorker;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Author: xiaoxiang
 * Description: 类别 - 接口实现类
 */
@Service
public class SortServiceImpl extends ServiceImpl<SortMapper, Sort> implements SortService {

    private final SortTagMapper sortTagMapper;
    private final SortMapper sortMapper;
    private final TagMapper tagMapper;

    @Value("${fdfs.url}")
    private String fileServerUrl;


    public SortServiceImpl(SortTagMapper sortTagMapper, SortMapper sortMapper, TagMapper tagMapper) {
        this.sortTagMapper = sortTagMapper;
        this.sortMapper = sortMapper;
        this.tagMapper = tagMapper;
    }

    // 根据ID查询类别
    @Override
    public Result<Sort> findIdBySort(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        Sort sort = getById(id);
        // 检查结果
        if (sort == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        return Result.ok(sort);
    }

    // 查询所有类别
    @Override
    public Result<Sort> qbcSort(QueryVo queryVo) {
        // 获取条件
        String sortName = queryVo.getKeyword();
        // 封装条件
        QueryWrapper<Sort> wrapper = new QueryWrapper<>();
        // 根据 类别名称 模糊查询
        if (!CharSequenceUtil.isEmpty(sortName)) {
            wrapper.like("sort_name", sortName);
        }
        // 构建排序条件，首先按 ord 升序排序，然后按创建时间降序排序
        wrapper.orderByAsc("ord").orderByDesc("create_time");
        // 分页条件 当前页-每页条数
        Page<Sort> page = new Page<>(queryVo.getCurrent(), queryVo.getLimit());
        Page<Sort> sortPage = page(page, wrapper);
        // 总条数
        sortPage.setTotal(sortPage.getRecords().size());
        // 返回结果
        return Result.ok(sortPage);
    }

    // 新增类别
    @Override
    public Result<Sort> saveSort(Sort sort) {
        // 1.检查参数
        if (sort.getSortName() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.查询数据库
        List<Sort> list = list(Wrappers.lambdaQuery(Sort.class).eq(Sort::getSortName, sort.getSortName()));
        if (list != null && list.size() == 1) {
            return Result.error(ResultEnum.DATA_EXIST, "该类别已经添加");
        }
        // 3.检查结果
        IdWorker worker = new IdWorker();
        // 生成分布式ID
        sort.setId(String.valueOf(worker.nextId()));
        sort.setCreateTime(new Date());
        boolean b = save(sort);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "类别添加失败");
    }

    // 类别修改
    @Override
    public Result<Sort> updateSort(Sort sort) {
        // 1.检查参数
        if (sort == null && sort.getId() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.修改并检查结果
        boolean b = updateById(sort);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "修改失败");
    }

    // 类别删除
    @Override
    public Result<Sort> removeSort(String id) {
        // 1. 检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_REQUIRE);
        }
        // 2. 检查类别是否有效
        Sort sort = getById(id);
        if (sort == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        // 3. 检查类别状态是否有效
        if (Boolean.FALSE.equals(sort.getStatus())) {
            return Result.error(ResultEnum.FAIL, "类别有效，不能删除");
        }
        // 4. 删除前判断类别下是否有标签关联
        Long countTagBySort = countTagBySort(id);
        if (countTagBySort > 0) {
            return Result.error(ResultEnum.DATA_EXIST, "类别下存在标签，暂不能删除");
        }
        // 5.执行删除并检查结果
        boolean b = removeById(id);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "删除失败");
    }

    // 批量删除
    @Override
    @Transactional
    public Result<List<Sort>> batchRemove(List<String> ids) {
        // 1. 检查参数
        if (ids == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2. 检查类别是否存在
        List<Sort> sortList = listByIds(ids);
        if (sortList == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        // 3. 检查类别状态是否禁用
        Map<String, Object> sortStatusIds = checkSortStatusByIds(sortList);
        // 3.1 flag为true表示 有类别状态为false，不能删除
        if (Boolean.TRUE.equals(sortStatusIds.get("flag"))) {
            Object sortName = sortStatusIds.get("sortName");
            return Result.error(ResultEnum.FAIL, "删除类别失败：" + sortName + "有效！");
        }
        // 4. 检查类别下是否存在标签关联
        Map<String, Long> map = countTagsBySortIds(ids);
        for (String id : ids) {
            Long tagCount = map.get(id);
            if (tagCount != null && tagCount > 0) {
                return Result.error(ResultEnum.DATA_EXIST, "类别下存在标签，暂不能删除");
            }
        }
        // 5. 删除操作
        boolean removeByIds = removeByIds(ids);
        if (removeByIds) {
            return Result.ok(ResultEnum.SUCCESS);
        } else {
            return Result.error(ResultEnum.FAIL, "删除类别失败");
        }
    }

    // 修改类别状态
    @Override
    public Result<Sort> updateStatus(String id, Boolean status) {
        // 1.检查参数
        if (id == null && status == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.根据类别id查询
        Sort sort = getById(id);
        if (sort == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        // 3. 变更（禁用）类别状态前判断类别下是否有标签关联
        Long countTagBySort = countTagBySort(id);
        if (countTagBySort > 0) {
            return Result.error(ResultEnum.DATA_EXIST, "类别下存在标签，暂不能切换状态");
        }
        // 4.设置状态值
        sort.setStatus(status);
        sort.setUpdateTime(new Date());
        // 5.修改并判断结果
        boolean b = updateById(sort);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "修改失败");
    }

    // 根据类别id查询标签数量
    @Override
    public Long countTagBySort(String id) {
        QueryWrapper<SortTag> wrapper = new QueryWrapper<>();
        wrapper.eq("sort_id", id);
        return sortTagMapper.selectCount(wrapper);
    }

    // 根据多个类别id查询标签数量
    @Override
    public Map<String, Long> countTagsBySortIds(List<String> ids) {
        Map<String, Long> tagCountMap = new HashMap<>();
        for (String id : ids) {
            QueryWrapper<SortTag> wrapper = new QueryWrapper<>();
            wrapper.eq("sort_id", id);
            Long count = sortTagMapper.selectCount(wrapper);
            tagCountMap.put(id, count);
        }
        return tagCountMap;
    }

    // 检查状态为true的数据并返回
    private Map<String, Object> checkSortStatusByIds(List<Sort> sortList) {
        // 定义一个标志位flag，默认为false 没有查到类别状态为false的数据
        boolean flag = false;
        // 存储状态为false的类别名称
        StringBuilder sortNameBuilder = new StringBuilder();
        // 遍历传入的类别数组
        for (Sort sort : sortList) {
            // 如果类别状态为启用（status 为 false）
            if (Boolean.FALSE.equals(sort.getStatus())) {
                // 设置标志位为 true，表示有未禁用的类别
                flag = true;
                // 将未禁用（false状态）的类别名称添加到 StringBuilder 中
                sortNameBuilder.append(sort.getSortName()).append(", ");
            }
        }
        //  从 StringBuilder 构建的字符串中获取未禁用的类别名称 并去除空格
        String sortNames = sortNameBuilder.toString().trim();
        // 去除字符串末尾的逗号
        if (!sortNames.isEmpty()) {
            sortNames = sortNames.substring(0, sortNames.length() - 1);
        }
        // 创建一个 Map 用于存储标志位和类别名称
        Map<String, Object> map = new HashMap<>();
        map.put("flag", flag);
        map.put("sortName", sortNames);
        // 返回包含标志位和类别名称的 Map
        return map;
    }

    // 查询类别 并查询类别下的标签
    @Override
    public Result<Object> qbcSortTag() {
        HashMap<String, List<TagInfoVo>> map = new HashMap<>();

        QueryWrapper<SortTag> wrapper = new QueryWrapper<>();
        wrapper.select("distinct sort_id");
        List<SortTag> sortList = sortTagMapper.selectList(wrapper);

        List<String> allTagIds = getAllTagIds();

        if (allTagIds.isEmpty()){
            return Result.ok("暂无标签，快去添加吧~");
        }

        for (Iterator<SortTag> it = sortList.iterator(); it.hasNext(); ) {
            String sortId = it.next().getSortId();
            List<String> tagIdList = getTagIdsBySortId(sortId);
            List<TagInfoVo> tagInfoList = getTagInfoListByTagIds(tagIdList);
            String sortName = getSortNameBySortId(sortId);
            map.put(sortName, tagInfoList);
            allTagIds.removeAll(tagIdList);
        }

        List<TagInfoVo> unassignedTagInfoList = getTagInfoListByTagIds(allTagIds);
        if (!unassignedTagInfoList.isEmpty()){
            map.put("未分配的标签", unassignedTagInfoList);
        }
        Result<Object> result = new Result<>();
        result.setHost(fileServerUrl);
        result.setData(map);

        return result;
    }

    private List<TagInfoVo> getTagInfoListByTagIds(List<String> tagIds) {
        List<TagInfoVo> tagInfoList = new ArrayList<>();
        // 检查tagIds是否为空
        if (tagIds == null || tagIds.isEmpty()) {
            // 返回一个空的结果列表
            return tagInfoList;
        }
        QueryWrapper<Tag> tagWrapper = new QueryWrapper<>();
        tagWrapper.in("tag_id", tagIds);
        List<Tag> tagList = tagMapper.selectList(tagWrapper);
        // 根据查询结果构建TagInfoVo列表
        for (Tag tag : tagList) {
            TagInfoVo tagInfo = new TagInfoVo();
            tagInfo.setTagName(tag.getTagName());
            tagInfo.setTagIcon(tag.getTagIcon());
            tagInfo.setTagUrl(tag.getTagUrl());
            tagInfo.setDescription(tag.getDescription());
            tagInfo.setOrd(tag.getOrd());
            tagInfoList.add(tagInfo);
        }

        return tagInfoList;
    }


    // 查询未分配标签的名称
    private List<String> getAllTagIds() {
        QueryWrapper<Tag> tagWrapper = new QueryWrapper<>();
        tagWrapper.select("tag_id");
        List<Tag> allTags = tagMapper.selectList(tagWrapper);
        List<String> allTagIds = new ArrayList<>();
        for (Tag tag : allTags) {
            allTagIds.add(tag.getTagId());
        }
        return allTagIds;
    }

    // 根据sortId查询tagId列表的方法
    private List<String> getTagIdsBySortId(String sortId) {
        List<String> tagIds = new ArrayList<>();
        QueryWrapper<SortTag> wrapper = new QueryWrapper<>();
        wrapper.eq("sort_id", sortId);
        List<SortTag> sortTagList = sortTagMapper.selectList(wrapper);

        for (SortTag sortTag : sortTagList) {
            tagIds.add(sortTag.getTagId());
        }

        return tagIds;
    }

    // 根据sortId查询sortName
    private String getSortNameBySortId(String sortId) {
        QueryWrapper<Sort> sortWrapper = new QueryWrapper<>();
        sortWrapper.eq("sort_id", sortId);
        Sort sort = sortMapper.selectOne(sortWrapper);

        if (sort != null) {
            return sort.getSortName();
        } else {
            return "";
        }
    }

    // 根据tagId查询tagNames
    private List<Tag> getTagsByTagIds(List<String> tagIds) {
        QueryWrapper<Tag> tagWrapper = new QueryWrapper<>();
        tagWrapper.in("tag_id", tagIds);
        return tagMapper.selectList(tagWrapper);
    }
}
