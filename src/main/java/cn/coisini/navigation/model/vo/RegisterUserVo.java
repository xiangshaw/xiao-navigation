package cn.coisini.navigation.model.vo;

import lombok.Data;

/**
 * Author: xiaoxiang
 * Description: 注册对象
 */
@Data
public class RegisterUserVo {
    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 验证码
     */
    private String code;

    private String uuid = "";
}

