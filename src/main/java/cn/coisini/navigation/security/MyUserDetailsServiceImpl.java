package cn.coisini.navigation.security;


import cn.coisini.navigation.common.exception.CoisiniException;
import cn.coisini.navigation.model.pojos.User;
import cn.coisini.navigation.service.MenuService;
import cn.coisini.navigation.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Author: xiaoxiang
 * Description: 三、添加UserDetailsServiceImpl类，实现UserDetailsService接口
 */
// 交给spring进行管理
@Component
public class MyUserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    private final MenuService menuService;
    public MyUserDetailsServiceImpl(UserService userService, MenuService menuService) {
        this.userService = userService;
        this.menuService = menuService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if (user == null){
            throw new CoisiniException(500,"用户不存在");
        }
        if (Boolean.TRUE.equals(user.getStatus())){
            throw new CoisiniException(500,"用户被禁用");
        }
        // 根据userid查询操作权限数据
        List<String> userPermsList = menuService.findUserButtonList(user.getId());
        List<SimpleGrantedAuthority> grantedAuthorityList = userPermsList.stream().map(perms -> new SimpleGrantedAuthority(perms.trim())).collect(Collectors.toList());
        return new CustomUser(user, grantedAuthorityList);
    }
}
