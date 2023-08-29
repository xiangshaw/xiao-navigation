package cn.coisini.navigation.common.log.service;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.AsyncOperLog;
import cn.coisini.navigation.model.vo.AsyncOperLogQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Author: xiaoxiang
 * Description: 操作日志记录 服务类
 */
public interface AsyncOperLogService extends IService<AsyncOperLog> {

    // 保存操作日志
    void saveSysLog(AsyncOperLog sysOperLog);

    // 条件分页查询操作日志
    Result<AsyncOperLog> pagingQuery(AsyncOperLogQueryVo asyncOperLogQueryVo);

    // 根据id查询操作日志
    Result<AsyncOperLog> get(String id);

    // 根据id删除操作日志
    Result<AsyncOperLog> removeOperLogById(String id);

    // 批量删除操作日志
    // json数组格式 ---对应---Java的list集合
    Result<AsyncOperLog> batchRemove(List<String> ids);
}
