package cn.coisini.navigation.common.log.service;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.AsyncLoginLog;
import cn.coisini.navigation.model.vo.AsyncLoginLogQueryVo;

import java.util.List;

/**
 * Author: xiaoxiang
 * Description: 异步调用日志服务
 */
public interface AsyncLoginLogService {
    /**
     * 记录登录信息
     *
     * @param username 用户名
     * @param status 状态(0成功 1失败)
     * @param loginIp ip
     * @param loginIpSource ip来源
     * @param message 消息内容
     */
    void recordLoginLog(String userId, String username, Boolean status, String loginIp, String loginIpSource, String loginIpCity, String message);

    // 条件分页查询登录日志
    Result<AsyncLoginLog> pagingQuery(AsyncLoginLogQueryVo asyncLoginLogQueryVo);

    // 根据id查询登录日志
    Result<AsyncLoginLog> get(String id);

    // 根据id删除登录日志
    Result<AsyncLoginLog> removeLoginLogById(String id);
    // 批量删除登录日志
    Result<AsyncLoginLog> batchRemove(List<String> ids);
}