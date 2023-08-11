package cn.coisini.navigation.model.vo;

import lombok.Data;

/**
 * Author: xiaoxiang
 * Description: 登录对象
 */
@Data
public class LoginVo {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String code;

    private String uuid = "";
}
