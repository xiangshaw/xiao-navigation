package cn.coisini.navigation.service;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.User;
import cn.coisini.navigation.model.pojos.UserRole;
import cn.coisini.navigation.model.vo.AssginRoleVo;
import cn.coisini.navigation.model.vo.RegisterUserVo;
import cn.coisini.navigation.model.vo.UserInfoVo;
import cn.coisini.navigation.model.vo.UserQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * Author: xiaoxiang
 * Description: 用户服务类
 */
public interface UserService extends IService<User> {
    // 分页条件查询用户
    Result<Object> pagingQuery(UserQueryVo userQueryVo);

    // 根据id获取用户
    Result<UserInfoVo> getUserId(String id);

    // 保存用户
    Result<User> saveUser(User user);

    // 修改用户
    Result<User> updateUser(User user);

    // 删除用户
    Result<User> removeUser(String id);
    // 批量删除？考虑考虑

    // 修改用户状态（0正常 1禁用）
    Result<User> updateStatus(String id, Boolean status);

    // 单头像修改后 更新操作
    Result<User> updateUserAvatar(String id, String userAvatar);

    // 用户修改个人信息
    Result<UserInfoVo> updateUserInfo(UserInfoVo userInfoVo);

    // ---用户 角色 关系---
    // 根据用户id获取角色信息
    Result<Map<String, Object>> getRolesByUserId(String id);
    // 给用户分配角色
    Result<UserRole> doAssign(AssginRoleVo assginRoleVo);

    // 权限认证
    // 用户登录（根据用户名称进行查询）
    User getUserInfoByName(String username);

    // 获取用户信息以及权限数据
    Map<String, Object> getUserInfo(String userId, String username);

    // 用户注册
    Result<User> registerUser(RegisterUserVo registerUserVo);
}
