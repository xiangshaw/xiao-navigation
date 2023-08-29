package cn.coisini.navigation.service.impl;

import cn.coisini.navigation.common.log.service.AsyncOperLogService;
import cn.coisini.navigation.mapper.AsyncOperLogMapper;
import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.common.enums.ResultEnum;
import cn.coisini.navigation.model.pojos.AsyncOperLog;
import cn.coisini.navigation.model.vo.AsyncOperLogQueryVo;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.coisini.navigation.model.common.constant.Constants.FALSE;

/**
 * Author: xiaoxiang
 * Description: 操作日志
 */
@Service
public class AsyncOperLogServiceImpl extends ServiceImpl<AsyncOperLogMapper, AsyncOperLog> implements AsyncOperLogService {

    private final AsyncOperLogMapper sysOperLogMapper;

    public AsyncOperLogServiceImpl(AsyncOperLogMapper sysOperLogMapper) {
        this.sysOperLogMapper = sysOperLogMapper;
    }

    @Override
    public void saveSysLog(AsyncOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
        Result.ok(ResultEnum.SUCCESS);
    }

    // 条件分页查询操作日志
    @Override
    public Result<AsyncOperLog> pagingQuery(AsyncOperLogQueryVo asyncOperLogQueryVo) {
        // 获取条件值
        String title = asyncOperLogQueryVo.getTitle();
        String operName = asyncOperLogQueryVo.getOperName();
        String createTimeBegin = asyncOperLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = asyncOperLogQueryVo.getCreateTimeEnd();
        // 封装条件
        QueryWrapper<AsyncOperLog> wrapper = new QueryWrapper<>();
        // 根据 标题、操作人、大于等于开始时间、小于等于创建时间
        if (!CharSequenceUtil.isEmpty(title)) {
            wrapper.like("title", title);
        }
        if (!CharSequenceUtil.isEmpty(operName)) {
            wrapper.like("oper_name", operName);
        }
        // 大于等于开始时间
        if (!CharSequenceUtil.isEmpty(createTimeBegin)) {
            wrapper.ge("create_time", createTimeBegin);
        } // 小于等于结束时间
        if (!CharSequenceUtil.isEmpty(createTimeBegin)) {
            wrapper.le("create_time", createTimeEnd);
        }// 排序
        wrapper.eq("del_flag",FALSE).orderByDesc("id");
        // 分页条件（当前页、每页条数）
        Page<AsyncOperLog> page = new Page<>(asyncOperLogQueryVo.getCurrent(), asyncOperLogQueryVo.getLimit());
        Page<AsyncOperLog> operLogPage = page(page, wrapper);
        return Result.ok(operLogPage);
    }

    // 根据id查询操作日志
    @Override
    public Result<AsyncOperLog> get(String id) {
        // 1.检验id
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        AsyncOperLog operLog = getById(id);
        return Result.ok(operLog);
    }

    // 根据id删除操作日志
    @Override
    public Result<AsyncOperLog> removeOperLogById(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.判断是否存在
        AsyncOperLog operLog = getById(id);
        if (operLog == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        removeById(id);
        return Result.ok(ResultEnum.SUCCESS);
    }

    // 批量删除操作日志
    @Override
    @Transactional
    public Result<AsyncOperLog> batchRemove(List<String> ids) {
        // 1.检查参数
        if (ids == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.判断当前日志是否存在 和 是否有效
        List<AsyncOperLog> operLogs = listByIds(ids);
        if (operLogs == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        removeByIds(ids);
        return Result.error(ResultEnum.SUCCESS);
    }
}
