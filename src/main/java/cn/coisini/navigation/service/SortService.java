package cn.coisini.navigation.service;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.Sort;
import cn.coisini.navigation.model.pojos.Tag;
import cn.coisini.navigation.model.vo.QueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * Author: xiaoxiang
 * Description: 类别 - 接口类
 */
public interface SortService extends IService<Sort> {
    // 根据ID条件查询
    Result<Sort> findIdBySort(String id);
    // 类别条件查询
    Result<Sort> qbcSort(QueryVo queryVo);
    // 类别新增
    Result<Sort> saveSort(Sort sort);
    // 类别修改
    Result<Sort> updateSort(Sort sort);
    // 类别删除
    Result<Sort> removeSort(String id);
    // 类别批量删除
    // json数组格式 ---对应---Java的list集合
    Result<List<Sort>> batchRemove(List<String> ids);
    // 修改类别状态（0正常 1禁用）
    Result<Sort> updateStatus(String id, Boolean status);
    // 根据类别id查询标签数量
    Long countTagBySort(String id);
    // 根据多个类别id查询标签数量
    Map<String, Long> countTagsBySortIds(List<String> ids);
    // 查询类别并查询类别下的标签
    Result<Object> qbcSortTag();

}
