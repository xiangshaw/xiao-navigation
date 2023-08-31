package cn.coisini.navigation.utils;

import cn.coisini.navigation.model.pojos.User;

/**
 * Author: xiaoxiang
 * Description: 用户线程工具类
 */
public class UserThreadLocalUtil {
    private final  static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
    /**
     * 设置当前线程中的用户
     * @param user
     */
    public static void setUser(User user){
        userThreadLocal.set(user);
    }
    /**
     * 获取线程中的用户
     * @return
     */
    public static User getUser( ){
        return userThreadLocal.get();
    }
}
