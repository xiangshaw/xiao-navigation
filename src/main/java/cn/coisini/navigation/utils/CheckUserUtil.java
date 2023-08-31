package cn.coisini.navigation.utils;

import java.util.Objects;

/**
 * Author: xiaoxiang
 * Description: 检测是否为当前用户操作
 */
public class CheckUserUtil {
    // 检测是否为当前用户操作
    public static boolean checkUser(String userId) {
        // 默认false
        boolean flag = false;
        if (Objects.equals(UserThreadLocalUtil.getUser().getId(), userId)){
            // true为当前用户操作
            flag = true;
        }
        return flag;
    }
}
