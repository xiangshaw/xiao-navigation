package cn.coisini.navigation.common.exception;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.common.enums.ResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: xiaoxiang
 * Description: 自定义全局异常处理
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CoisiniException extends RuntimeException {
    // 异常状态码
    private final Integer code;
    // 异常信息
    private final String message;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param code    标识码
     * @param message 消息
     */
    public CoisiniException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum 枚举
     */
    public CoisiniException(ResultEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }

    public CoisiniException(Result result , ResultEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
        this.message = resultCodeEnum.getMessage();
    }
    /**
     * spring security异常
     *
     * @param e 异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public Result<ResultEnum> error(AccessDeniedException e) throws AccessDeniedException {
        return Result.error(ResultEnum.NEED_ADMIND);
    }

    @Override
    public String toString() {
        return "CoisiniException{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
