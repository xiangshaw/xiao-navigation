package cn.coisini.navigation.service;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.Sort;
import cn.coisini.navigation.model.vo.SortQueryVo;

/**
 * Author: xiaoxiang
 * Description: 类别 - 接口类
 */
public interface SortService {
    // 根据ID条件查询
    Result<Sort> findIdBySort(String id);
    // 类别条件查询
    Result<Sort> qbcSort(SortQueryVo sortQueryVo);
    // 类别新增
    Result<Sort> saveSort(Sort sort);
    // 类别修改
    Result<Sort> updateSort(Sort sort);
    // 类别删除
    Result<Sort> removeSort(String id);
}
