package cn.coisini.navigation.common.exception;

import cn.coisini.navigation.common.log.annotation.Log;
import cn.coisini.navigation.common.log.enums.BusinessType;
import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.common.enums.ResultEnum;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: xiaoxiang
 * Description: 抓捕全局异常
 */
@ControllerAdvice//控制器增强
@Log4j2//记录日志信息
public class ExceptionCatch {
    /*指定要拦截的类型注解,这里指定拦截所有错误，都需经过此处*/
    @ExceptionHandler(Exception.class)
    /*返回JSON数据*/
    @ResponseBody
    @Log(title = "记录异常",businessType = BusinessType.RECORD_EXCEPTIONS)
    /*Exception接收错误信息*/
    public Result<Exception> exception(Exception e){
        /*打印异常信息*/
        log.error(e);
        /*拿到错误信息，记录日志*/
        log.error("catch exception:{}",e.getMessage());
        /*返回通用的异常信息*/
        return Result.error(ResultEnum.SERVER_ERROR);
    }

    // 处理 AccessDeniedException 异常
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    @Log(title = "记录异常",businessType = BusinessType.RECORD_EXCEPTIONS)
    public Result<Exception> accessDeniedException(AccessDeniedException e) {
        // 处理访问权限异常
        log.error("catch AccessDeniedException:{}", e.getMessage());
        return Result.error(ResultEnum.NO_OPERATOR_AUTH);
    }
}
