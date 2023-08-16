package cn.coisini.navigation.service;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.SortTag;
import cn.coisini.navigation.model.pojos.Tag;
import cn.coisini.navigation.model.vo.QueryVo;
import cn.coisini.navigation.model.vo.SortTagVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * Author: xiaoxiang
 * Description: 标签 - 接口类
 */
public interface TagService extends IService<Tag> {
    // 根据ID条件查询
    Result<Tag> findIdByTag(String id);
    // 标签条件查询
    Result<Tag> qbcTag(QueryVo queryVo);
    // 标签新增
    Result<Tag> saveTag(Tag tag);
    // 标签修改
    Result<Tag> updateTag(Tag tag);
    // 标签删除
    Result<Tag> removeTag(String id);
    // 类别批量删除
    // json数组格式 ---对应---Java的list集合
    Result<List<Tag>> batchRemove(List<String> ids);
    // 修改类别状态（0正常 1禁用）
    Result<Tag>  updateStatus(String id, Boolean status);

    // === 类别和标签关系===
    // 根据标签id获取类别信息
    Result<Map<String, Object>> getSortsByTagId(String id);
    // 新增标签与类别
    Result<SortTag> saveSortTag(SortTagVo sortTagVo);
}