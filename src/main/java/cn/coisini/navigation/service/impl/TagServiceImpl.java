package cn.coisini.navigation.service.impl;

import cn.coisini.navigation.mapper.SortMapper;
import cn.coisini.navigation.mapper.SortTagMapper;
import cn.coisini.navigation.mapper.TagMapper;
import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.common.enums.ResultEnum;
import cn.coisini.navigation.model.pojos.Sort;
import cn.coisini.navigation.model.pojos.SortTag;
import cn.coisini.navigation.model.pojos.Tag;
import cn.coisini.navigation.model.pojos.User;
import cn.coisini.navigation.model.vo.QueryVo;
import cn.coisini.navigation.model.vo.SortTagVo;
import cn.coisini.navigation.model.vo.TagVo;
import cn.coisini.navigation.service.TagService;
import cn.coisini.navigation.utils.CheckUserUtil;
import cn.coisini.navigation.utils.FastDfsClient;
import cn.coisini.navigation.utils.IdWorker;
import cn.coisini.navigation.utils.UserThreadLocalUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Author: xiaoxiang
 * Description: 标签 - 接口实现类
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    private final SortTagMapper sortTagMapper;
    private final SortMapper sortMapper;
    private final FastDfsClient fastDfsClient;

    @Value("${fdfs.url}")
    private String fileServerUrl;

    public TagServiceImpl(SortTagMapper sortTagMapper, SortMapper sortMapper, FastDfsClient fastDfsClient) {
        this.sortTagMapper = sortTagMapper;
        this.sortMapper = sortMapper;
        this.fastDfsClient = fastDfsClient;
    }

    // 根据ID查询标签
    @Override
    public Result<Tag> findIdByTag(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        Tag tag = getById(id);
        // 检查结果
        if (tag == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        return Result.ok(tag);
    }

    // 查询所有标签
    @Override
    public Result<Object> qbcTag(QueryVo queryVo) {
        // 获取条件
        String tagName = queryVo.getKeyword();
        // 获取线程用户
        User user = UserThreadLocalUtil.getUser();
        // 根据标签名称模糊查询、用户Id精确查询、按照排序 和 创建时间排序
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        if (!CharSequenceUtil.isEmpty(tagName)) {
            wrapper.like("tag_name", tagName);
        }
        wrapper.eq("user_id", user.getId())
                .orderByAsc("ord")
                .orderByDesc("create_time");
        // 分页条件，当前页、每页条数
        Page<Tag> page = new Page<>(queryVo.getCurrent(), queryVo.getLimit());
        Page<Tag> tagPage = page(page, wrapper);
        // 获取类别名称
        getSortName(tagPage.getRecords());
        // 返回图标地址以及标签数据
        Result<Object> result = new Result<>();
        result.setHost(fileServerUrl);
        result.setData(tagPage);
        return result;
    }

    // 获取类别名称
    private void getSortName(List<Tag> tags) {
        // 获取标签Id
        List<String> tagIds = tags.stream().map(Tag::getTagId).collect(Collectors.toList());
        Map<String, List<String>> tagSortNamesMap = new HashMap<>();
        // 标签Id不为空
        if (!tagIds.isEmpty()) {
            // 获取标签类别关系数据
            List<SortTag> tagSortList = sortTagMapper.selectList(new QueryWrapper<SortTag>().in("tag_id", tagIds));
            // 获取类别Id
            for (SortTag tagSort : tagSortList) {
                tagSortNamesMap.computeIfAbsent(tagSort.getTagId(), k -> new ArrayList<>())
                        .add(tagSort.getSortId());
            }
            // 根据类别Id获取类别名称
            for (Tag tag : tags) {
                List<String> sortIds = tagSortNamesMap.getOrDefault(tag.getTagId(), Collections.emptyList());
                if (!sortIds.isEmpty()) {
                    List<String> sortNames = sortMapper.selectBatchIds(sortIds)
                            .stream()
                            .map(Sort::getSortName)
                            .collect(Collectors.toList());
                    tag.setSorts(sortNames);
                }
            }
        }
    }

    // 新增标签
    @Override
    public Result<Tag> saveTag(Tag tag) {
        // 1.检查参数
        if (tag.getTagName() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 获取线程用户
        String userId = UserThreadLocalUtil.getUser().getId();
        // 2.查询数据库
        List<Tag> tagList = list(Wrappers.lambdaQuery(Tag.class)
                .eq(Tag::getUserId,userId)
                .eq(Tag::getTagName, tag.getTagName()));
        if (!tagList.isEmpty()) {
            return Result.error(ResultEnum.DATA_EXIST, "该标签已经添加");
        }
        // 3.检查结果
        IdWorker worker = new IdWorker();
        // 生成分布式ID
        tag.setTagId(String.valueOf(worker.nextId()));
        tag.setUserId(userId);
        tag.setCreateTime(new Date());
        boolean b = save(tag);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "标签添加失败");
    }

    // 标签修改
    @Override
    public Result<Tag> updateTag(Tag tag) {
        // 1.检查参数
        if (tag == null || tag.getTagId() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 用户检测
        if (!CheckUserUtil.checkUser(tag.getUserId())){
            return Result.error(ResultEnum.NO_OPERATOR_AUTH, "账户异常操作，请退出后重试");
        }
        // 2.修改并检查结果
        boolean b = updateById(tag);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "修改失败");
    }

    /**
     * 修改标签
     * Author xiaoxiang
     */
    private boolean updateTags(TagVo tagVo) {
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_id", tagVo.getTagId());
        return updateById(wrapper.getEntity());
    }

    // 标签删除
    @Override
    public Result<Tag> removeTag(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_REQUIRE);
        }
        // 2.根据ID获取标签信息
        Tag tag = getById(id);
        if (tag == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        // 用户检测
        if (!CheckUserUtil.checkUser(tag.getUserId())){
            return Result.error(ResultEnum.NO_OPERATOR_AUTH, "账户异常操作，请退出后重试");
        }
        if (Boolean.FALSE.equals(tag.getStatus())) {
            return Result.error(ResultEnum.FAIL, "标签有效，不能删除");
        }
        // 3.先删除图标
        if (tag.getTagIcon() != null) {
            fastDfsClient.delFile(delIconUrl(tag.getTagIcon()));
        }
        // 4.检查是否关联类别,如果关联则删除
       boolean checkTagAssociation =  checkTagAssociation(id);
        if (checkTagAssociation){
            removeTagAssociation(id);
        }
        // 5.删除标签数据
        boolean b = removeById(id);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "删除失败");
    }
    // 检测sortTag关联数据
    private boolean checkTagAssociation(String tagId) {
        QueryWrapper<SortTag> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_id", tagId);
        return sortTagMapper.selectCount(wrapper) > 0;
    }
    // 删除sortTag关联数据
    private void removeTagAssociation(String tagId) {
        QueryWrapper<SortTag> wrapper = new QueryWrapper<>();
        wrapper.eq("tag_id", tagId);
        sortTagMapper.delete(wrapper);
    }
    // 批量删除
    @Override
    public Result<List<Tag>> batchRemove(List<String> ids) {
        // 1. 检查参数
        if (ids == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2. 检查标签是否存在
        List<Tag> tagList = listByIds(ids);
        if (tagList == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        // 3. 检查标签状态是否禁用
        Map<String, Object> tagStatusIds = checkTagStatusByIds(tagList);
        // 3.1 flag为true表示 有标签状态为false，不能删除
        if (Boolean.TRUE.equals(tagStatusIds.get("flag"))) {
            Object tagName = tagStatusIds.get("tagName");
            return Result.error(ResultEnum.FAIL, "删除标签失败：" + tagName + "有效！");
        }
        // 4. 遍历tagList并处理tagIcon字段
        for (Tag tag : tagList) {
            // 用户检测
            if (!CheckUserUtil.checkUser(tag.getUserId())){
                return Result.error(ResultEnum.NO_OPERATOR_AUTH, "账户异常操作，请退出后重试");
            }
            String tagIcon = tag.getTagIcon();
            if (tagIcon != null && !tagIcon.isEmpty()) {
                // 删除文件
                fastDfsClient.delFile(delIconUrl(tagIcon));
            }
        }
        // 检测是否存在sortTag关联数据
        List<String> associatedTags = getAssociatedTagsInSortTag(ids);
        if (!associatedTags.isEmpty()) {
            // 处理存在关联的标签
            removeTagIdsAssociation(associatedTags);
        }
        // 5. 删除操作
        boolean removeByIds = removeByIds(ids);
        if (removeByIds) {
            return Result.ok(ResultEnum.SUCCESS);
        } else {
            return Result.error(ResultEnum.FAIL, "删除标签失败");
        }
    }
    // 检测与sortTag关联数据
    private List<String> getAssociatedTagsInSortTag(List<String> ids) {
        List<String> associatedTags = new ArrayList<>();
        for (String id : ids) {
            QueryWrapper<SortTag> wrapper = new QueryWrapper<>();
            wrapper.eq("tag_id", id);
            long count = sortTagMapper.selectCount(wrapper);
            if (count > 0) {
                associatedTags.add(id);
            }
        }
        return associatedTags;
    }
    // 删除sortTag关联数据
    private void removeTagIdsAssociation(List<String> tagIds) {
        QueryWrapper<SortTag> wrapper = new QueryWrapper<>();
        wrapper.in("tag_id", tagIds);
        sortTagMapper.delete(wrapper);
    }

    @Override
    public Result<Tag> updateStatus(String id, Boolean status) {
        // 1.检查参数
        if (id == null && status == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.根据标签id查询
        Tag tag = getById(id);
        if (tag == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        // 用户检测
        if (!CheckUserUtil.checkUser(tag.getUserId())){
            return Result.error(ResultEnum.NO_OPERATOR_AUTH, "账户异常操作，请退出后重试");
        }
        // 3.设置状态值
        tag.setStatus(status);
        tag.setUpdateTime(new Date());
        // 5.修改并判断结果
        boolean b = updateById(tag);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "修改失败");
    }

    // 单图标修改后 更新操作
    @Override
    public Result<Tag> updateTagIcon(String tagId, String tagIcon) {
        // 参数检查
        if (tagId == null || tagIcon == null) {
            return Result.error(ResultEnum.FAIL, "图标更新失败，参数不完整");
        }
        // 查询标签是否存在
        Tag tag = getById(tagId);
        if (tag == null){
            // 首次提交
            return Result.ok("图标添加成功~");
        }
        // 用户检测
        if (!CheckUserUtil.checkUser(tag.getUserId())){
            return Result.error(ResultEnum.NO_OPERATOR_AUTH, "账户异常操作，请退出后重试");
        }
        // 下划线替换回斜杠
        String icon = tagIcon.replace('_', '/');
        // 更新标签图标
        UpdateWrapper<Tag> wrapper = new UpdateWrapper<>();
        wrapper.eq("tag_id", tagId);
        wrapper.set("tag_icon", icon);
        baseMapper.update(tag, wrapper);
        return Result.ok(ResultEnum.SUCCESS);
    }

    // 根据标签Id获取类别数据
    @Override
    public Result<Map<String, Object>> getSortsByTagId(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.获取当前用户的类别
        QueryWrapper<Sort> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", UserThreadLocalUtil.getUser().getId());
        List<Sort> sortList = sortMapper.selectList(wrapper);
        // 3.根据标签id查询已经关联的类别
        List<SortTag> tagSortList = sortTagMapper.selectList(new QueryWrapper<SortTag>()
                .eq("tag_id", id));
        // 4.获取所有类别id
        List<String> sortIds = tagSortList.stream().map(SortTag::getSortId).collect(Collectors.toList());
        // 5.封装到map
        HashMap<String, Object> map = new HashMap<>();
        // 5.1所有标签
        map.put("allSorts", sortList);
        // 5.2标签分配标签id集合
        map.put("tagSortIds", sortIds);
        return Result.ok(map);
    }

    // 新增标签与类别
    @Override
    public Result<SortTag> saveSortTag(SortTagVo sortTagVo) {
        // 1.检查参数
        if (sortTagVo == null || sortTagVo.getTagId() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.获取当前用户
        String userId = UserThreadLocalUtil.getUser().getId();
        // 3.获取当前用户的类别id,添加标签类别关系表
        List<String> userSortIdList = getUserSortIdList(userId);
        // 4.根据标签id删除原来分配的类别
        if (!userSortIdList.isEmpty()) {
            sortTagMapper.delete(new QueryWrapper<SortTag>().eq("user_id", userId).eq("tag_id", sortTagVo.getTagId()));
        }
        // 5.筛选出有效的 sortIdList
        List<String> sortIdList = sortTagVo.getSortIdList().stream()
                .filter(userSortIdList::contains)
                .collect(Collectors.toList());
        // 6.添加标签类别关系
        sortIdList.forEach(sortId -> {
            SortTag sortTag = new SortTag();
            sortTag.setId(String.valueOf(new IdWorker().nextId()));
            sortTag.setUserId(userId);
            sortTag.setTagId(sortTagVo.getTagId());
            sortTag.setSortId(sortId);
            sortTagMapper.insert(sortTag);
        });
        return Result.ok(ResultEnum.SUCCESS);
    }

    // 获取用户的类别
    private List<String> getUserSortIdList(String userId) {
        QueryWrapper<Sort> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        // 执行查询并获取结果列表
        List<Sort> userSortList = sortMapper.selectList(wrapper);
        // 从结果列表中提取类别 id 列表
        return userSortList.stream()
                .map(Sort::getId)
                .collect(Collectors.toList());
    }


    private Map<String, Object> checkTagStatusByIds(List<Tag> tagList) {
        // 定义一个标志位flag，默认为false 没有查到标签状态为false的数据
        boolean flag = false;
        // 存储状态为false的标签名称
        List<String> tagNameList = new ArrayList<>();
        // 遍历传入的标签数组
        for (Tag tag : tagList) {
            // 用户检测,检测不通过，就将flag状态进行更改
            if (!CheckUserUtil.checkUser(tag.getUserId())){
                flag = true;
            }
            // 如果标签状态为启用（status 为 false）
            if (!tag.getStatus()) {
                // 设置标志位为 true，表示有未禁用的标签
                flag = true;
                tagNameList.add(tag.getTagName());
            }
        }
        // 创建一个 Map 用于存储标志位和标签名称
        Map<String, Object> map = new HashMap<>();
        map.put("flag", flag);
        map.put("tagName", String.join("，", tagNameList));
        // 返回包含标志位和标签名称的 Map
        return map;
    }

    // 删除图标地址
    private String delIconUrl(String tagIcon) {
        // 找到 "group" 在 tagIcon 中的位置
        int startIndex = tagIcon.indexOf("group");
        if (startIndex != -1) {
            // 截取 "group" 后面的部分
            return tagIcon.substring(startIndex);
        } else {
            return tagIcon;
        }
    }
}
