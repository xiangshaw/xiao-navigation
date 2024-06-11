package cn.coisini.navigation.service.impl;

import cn.coisini.navigation.common.log.service.AsyncLoginLogService;
import cn.coisini.navigation.mapper.AsyncLoginLogMapper;
import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.common.enums.ResultEnum;
import cn.coisini.navigation.model.pojos.AsyncLoginLog;
import cn.coisini.navigation.model.vo.AsyncLoginLogQueryVo;
import cn.coisini.navigation.utils.IdWorker;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author: xiaoxiang
 * Description: 系统访问记录 服务实现类
 */
@Service
public class AsyncLoginLogServiceImpl extends ServiceImpl<AsyncLoginLogMapper, AsyncLoginLog> implements AsyncLoginLogService {

    private final AsyncLoginLogMapper asyncLoginLogMapper;

    public AsyncLoginLogServiceImpl(AsyncLoginLogMapper asyncLoginLogMapper) {
        this.asyncLoginLogMapper = asyncLoginLogMapper;
    }

    /**
     * 记录登录信息
     *
     * @param username      用户名
     * @param status        状态 默认0
     * @param loginIp       ip
     * @param loginIpSource ip归属地
     * @param loginIpCity ip省市区
     * @param message       消息内容
     */
    public void recordLoginLog(String userId, String username, Boolean status, String loginIp, String loginIpSource, String loginIpCity,String message) {
        AsyncLoginLog asyncLoginLog = new AsyncLoginLog();
        asyncLoginLog.setId(String.valueOf(new IdWorker().nextId()));
        asyncLoginLog.setUserId(userId);
        asyncLoginLog.setUsername(username);
        asyncLoginLog.setLoginIp(loginIp);
        asyncLoginLog.setLoginIpSource(loginIpSource);
        asyncLoginLog.setLoginIpCity(loginIpCity);
        asyncLoginLog.setMsg(message);
        asyncLoginLog.setStatus(status);
        asyncLoginLogMapper.insert(asyncLoginLog);
    }

    // 条件分页查询登录日志
    @Override
    public Result<AsyncLoginLog> pagingQuery(AsyncLoginLogQueryVo asyncLoginLogQueryVo) {
        // 获取条件值
        String username = asyncLoginLogQueryVo.getUsername();
        String createTimeBegin = asyncLoginLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = asyncLoginLogQueryVo.getCreateTimeEnd();
        // 封装条件
        QueryWrapper<AsyncLoginLog> wrapper = new QueryWrapper<>();
        // 根据 登录用户名称、大于等于开始时间、小于等于创建时间
        if (!CharSequenceUtil.isEmpty(username)) {
            wrapper.like("username", username);
        } // 大于等于开始时间
        if (!CharSequenceUtil.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time", createTimeBegin);
        } // 小于等于结束时间
        if (!CharSequenceUtil.isEmpty(createTimeBegin)) {
            wrapper.le("create_time", createTimeEnd);
        } //排序
        wrapper.eq("del_flag", 0).orderByDesc("id");
        // 分页条件（当前页、每页条数）
        Page<AsyncLoginLog> page = new Page<>(asyncLoginLogQueryVo.getCurrent(), asyncLoginLogQueryVo.getLimit());
        Page<AsyncLoginLog> loginLogPage = page(page, wrapper);
        return Result.ok(loginLogPage);
    }

    // 根据id查询登录日志
    @Override
    public Result<AsyncLoginLog> get(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        AsyncLoginLog sysLoginLog = getById(id);

        return Result.ok(sysLoginLog);
    }

    @Override
    public Result<AsyncLoginLog> removeLoginLogById(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.判断是否存在
        AsyncLoginLog loginLog = getById(id);
        if (loginLog == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        removeById(id);
        return Result.ok(ResultEnum.SUCCESS);
    }

    // 批量删除登录日志
    @Override
    public Result<AsyncLoginLog> batchRemove(List<String> ids) {
        // 1.检查参数
        if (ids == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.判断当前日志是否存在 和 是否有效
        List<AsyncLoginLog> loginLogs = listByIds(ids);
        if (loginLogs == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        removeByIds(ids);
        return Result.ok(ResultEnum.SUCCESS);
    }
}
