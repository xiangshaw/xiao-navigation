package cn.coisini.navigation.model.common.enums;

/**
 * Author: xiaoxiang
 * Description: 通用的异常枚举
 */
public enum ResultEnum {
    // 登录段1~50
    NEED_LOGIN(1, "需要登录后操作"),
    LOGIN_PASSWORD_ERROR(2, "密码错误"),
    // TOKEN50~100
    TOKEN_INVALID(50, "无效的TOKEN"),
    TOKEN_EXPIRE(51, "TOKEN已过期"),
    TOKEN_REQUIRE(52, "TOKEN是必须的"),
    // SIGN验签 100~120
    SIGN_INVALID(100, "无效的SIGN"),
    SIG_TIMEOUT(101, "SIGN已过期"),
    // 成功段0
    SUCCESS(200, "操作成功"),
    FAIL(201, "失败"),
    SERVICE_ERROR(202, "服务异常"),
    ACCOUNT_STOP( 217, "账号已停用"),
    NODE_ERROR( 218, "该节点下有子节点，不可以删除"),
    // 参数错误 500~1000
    PARAM_REQUIRE(500, "缺少参数"),
    PARAM_INVALID(501, "无效参数"),
    PARAM_IMAGE_FORMAT_ERROR(502, "图片格式有误"),
    //    SERVER_ERROR(503,"服务器内部错误"),
    SECURITY_CODE(504,"验证码不存在或已过期"),
    SECURITY_CODE_ERROR(505,"验证码错误"),
    SERVER_ERROR(777, "当前系统正在维护，请稍后重试"),
    // 数据错误 1000~2000
    DATA_EXIST(1000, "数据已经存在"),
    AP_USER_DATA_NOT_EXIST(1001, "User数据不存在"),
    DATA_NOT_EXIST(1002, "数据不存在"),
    // 数据错误 3000~3500
    NO_OPERATOR_AUTH(3000, "无权限操作"),
    NEED_ADMIND(3001, "需要管理员权限");
    int code;
    String message;

    //触发构造器，赋值
    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    //通过get方法就可以拿值
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

