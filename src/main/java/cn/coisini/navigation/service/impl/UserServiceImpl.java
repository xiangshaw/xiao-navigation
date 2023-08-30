package cn.coisini.navigation.service.impl;

import cn.coisini.navigation.common.exception.CoisiniException;
import cn.coisini.navigation.mapper.RoleMapper;
import cn.coisini.navigation.mapper.UserMapper;
import cn.coisini.navigation.mapper.UserRoleMapper;
import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.common.enums.ResultEnum;
import cn.coisini.navigation.model.pojos.Role;
import cn.coisini.navigation.model.pojos.User;
import cn.coisini.navigation.model.pojos.UserRole;
import cn.coisini.navigation.model.vo.AssginRoleVo;
import cn.coisini.navigation.model.vo.RegisterUserVo;
import cn.coisini.navigation.model.vo.RouterVo;
import cn.coisini.navigation.model.vo.UserQueryVo;
import cn.coisini.navigation.service.MenuService;
import cn.coisini.navigation.service.UserService;
import cn.coisini.navigation.utils.FastDfsClient;
import cn.coisini.navigation.utils.IdWorker;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Author: xiaoxiang
 * Description: 用户表 服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private final RoleMapper roleMapper;
    private final UserRoleMapper userRoleMapper;
    private final MenuService menuService;
    private final FastDfsClient fastDfsClient;

    private final StringRedisTemplate stringRedisTemplate;

    @Value("${fdfs.url}")
    private String fileServerUrl;

    public UserServiceImpl(RoleMapper roleMapper, UserRoleMapper userRoleMapper, MenuService menuService, FastDfsClient fastDfsClient, StringRedisTemplate stringRedisTemplate) {
        this.roleMapper = roleMapper;
        this.userRoleMapper = userRoleMapper;
        this.menuService = menuService;
        this.fastDfsClient = fastDfsClient;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    // 分页条件查询用户
    @Override
    public Result<Object> pagingQuery(UserQueryVo userQueryVo) {
        // 获取条件
        String keyword = userQueryVo.getKeyword();
        String createTimeBegin = userQueryVo.getCreateTimeBegin();
        String createTimeEnd = userQueryVo.getCreateTimeEnd();
        // 封装条件
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // 根据用户名称、真实名称、手机号模糊查询，，，大于等于开始时间、小于等于创建时间
        if (CharSequenceUtil.isNotBlank(userQueryVo.getKeyword())) {
            wrapper.and(x -> x.like("username", keyword)
                    .or()
                    .like("nickname", keyword)
                    .or()
                    .like("phone", keyword));
        }
        if (CharSequenceUtil.isNotBlank(createTimeBegin)) {
            wrapper.ge("create_time", createTimeBegin);
        }
        if (CharSequenceUtil.isNotBlank(createTimeEnd)) {
            wrapper.le("create_time", createTimeEnd);
        }
        // 排序
        wrapper.eq("del_flag", 0).orderByDesc("id");
        // 选择性排除password字段
        wrapper.select(User.class, info -> !"password".equals(info.getColumn()));
        // 分页条件 当前页-每页条数
        Page<User> page = new Page<>(userQueryVo.getCurrent(), userQueryVo.getLimit());
        Page<User> userPage = page(page, wrapper);
        // 返回结果
        Result<Object> result = new Result<>();
        result.setData(userPage);
        result.setHost(fileServerUrl);
        return result;
    }

    // 根据Id获取用户
    @Override
    public Result<User> getUserId(String id) {
        if (id == null) {
            return Result.error(ResultEnum.PARAM_REQUIRE);
        }
        User user = getById(id);
        if (user == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        return Result.ok(user);
    }

    // 保存用户
    @Override
    public Result<User> saveUser(User user) {
        // 1.检查参数
        if (user.getUsername() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.查询数据库
        List<User> list = list(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        if (list != null && list.size() == 1) {
            return Result.error(ResultEnum.DATA_EXIST, "该用户名已经注册");
        }
        // 3.输入的密码进行BCryptPasswordEncoder加密
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setId(String.valueOf(new IdWorker().nextId()));
        user.setNickname("用户" + user.getId());
        boolean b = save(user);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "用户保存失败");
    }

    // 修改用户
    @Override
    public Result<User> updateUser(User user) {
        // 1.检查参数
        if (user == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        user.setPassword(null);
        // 2.修改并判断结果
        boolean b = updateById(user);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "用户修改失败");
    }

    // 删除用户
    @Override
    public Result<User> removeUser(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.判断当前用户是否存在
        User user = getById(id);
        if (user == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        // 先删除头像
        if (!ObjectUtils.isEmpty(user.getAvatar())) {
            fastDfsClient.delFile(delAvatar(user.getAvatar()));
        }
        // 3.删除并判断结果
        boolean b = removeById(id);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "用户删除失败");
    }

    // 修改用户状态（0正常 1禁用）
    @Override
    public Result<User> updateStatus(String id, Boolean status) {
        // 1.检查参数
        if (id == null && status == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.根据用户id查询
        User user = getById(id);
        // 3.设置状态值
        user.setStatus(status);
        user.setUpdateTime(new Date());
        //  4.修改并判断结果
        boolean b = updateById(user);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "用户状态修改失败");
    }

    // 单头像修改后 更新操作
    @Override
    public Result<User> updateUserAvatar(String id, String userAvatar) {
        // 参数检查
        if (id == null || userAvatar == null) {
            return Result.error(ResultEnum.FAIL, "头像更新失败，参数不完整");
        }
        // 查询用户是否存在
        User user = getById(id);
        if (user == null){
            // 首次提交
            return Result.ok("头像添加成功~");
        }
        // 下划线替换回斜杠
        String avatar = userAvatar.replace('_', '/');
        // 更新头像
        UpdateWrapper<User> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", id);
        wrapper.set("avatar", avatar);
        baseMapper.update(user, wrapper);
        return Result.ok(ResultEnum.SUCCESS);
    }

    // 获取用户id获取角色数据
    @Override
    public Result<Map<String, Object>> getRolesByUserId(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.获取所有角色
        List<Role> roleList = roleMapper.selectList(null);
        // 3.根据用户id查询已经分配的角色
        List<UserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<UserRole>().eq("user_id", id));
        // 4.获取所有角色id
        List<String> roleIds = userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        // 5.封装到map
        HashMap<String, Object> map = new HashMap<>();
        // 5.1 所有角色
        map.put("allRoles", roleList);
        // 5.2 用户分配角色id集合
        map.put("userRoleIds", roleIds);
        return Result.ok(map);
    }

    // 给用户分配角色
    @Override
    public Result<UserRole> doAssign(AssginRoleVo assginRoleVo) {
        // 1.检查参数
        if (assginRoleVo.getUserId() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.根据用户id删除原来分配的角色
        userRoleMapper.delete(new QueryWrapper<UserRole>().eq("user_id", assginRoleVo.getUserId()));
        // 3.获取所有角色id，添加角色用户关系表
        List<String> roleIdList = assginRoleVo.getRoleIdList();
        roleIdList.forEach(roleId -> {
            UserRole userRoleEntity = new UserRole();
            userRoleEntity.setUserId(assginRoleVo.getUserId());
            userRoleEntity.setRoleId(roleId);
            userRoleMapper.insert(userRoleEntity);
        });
        return Result.ok(ResultEnum.SUCCESS);
    }

    // 用户登录（交给Spring Security了）
    @Override
    public User getUserInfoByName(String username) {
        if ("null".equals(username)) {
            throw new CoisiniException(ResultEnum.PARAM_INVALID);
        }
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }

    // 获取用户信息以及权限数据
    @Override
    public Map<String, Object> getUserInfo(String userId, String username) {
        // 1.检查参数
        if (userId == null && StringUtils.isEmpty(username)) {
            throw new CoisiniException(ResultEnum.PARAM_INVALID);
        }
        // 2.根据用户id查询菜单权限值
        List<RouterVo> routerVoList = menuService.findUserMenuList(userId);
        HashMap<String, Object> map = new HashMap<>();
        // 3.根据用户id查询按钮权限值
        List<String> permsList = menuService.findUserButtonList(userId);
        // 根据用户id查询信息
        String avatar = baseMapper.selectById(userId).getAvatar();
        map.put("name", username);
        map.put("avatar", fileServerUrl + avatar);
        // 菜单权限数据
        map.put("routers", routerVoList);
        // 按钮权限数据
        map.put("buttons", permsList);
        return map;
    }

    // 用户注册
    @Override
    public Result<User> registerUser(RegisterUserVo registerUserVo) {
        if (StringUtils.isEmpty(registerUserVo.getUsername()) || StringUtils.isEmpty(registerUserVo.getPhone())) {
            return Result.error(ResultEnum.PARAM_REQUIRE, "用户名和手机号不能为空");
        }
        String checkCode = stringRedisTemplate.opsForValue().get("checkCode:" + registerUserVo.getUuid());
        // 验证码校验
        if (CharSequenceUtil.isBlank(checkCode)) {
            return Result.error(ResultEnum.SECURITY_CODE);
        }
        if (!checkCode.equals(registerUserVo.getCode())) {
            return Result.error(ResultEnum.SECURITY_CODE_ERROR);
        }
        User user = new User();
        user.setUsername(registerUserVo.getUsername());
        user.setPassword(registerUserVo.getPassword());
        user.setPhone(registerUserVo.getPhone());
        Result<User> userResult = saveUser(user);
        if (userResult.getCode() == 200){
            return Result.ok(userResult.getmessage());
        }
        return Result.error(ResultEnum.FAIL,userResult.getmessage());
    }

    // 删除头像
    private String delAvatar(String avatar) {
        // 找到 "group" 在 avatar 中的位置
        int startIndex = avatar.indexOf("group");
        if (startIndex != -1) {
            // 截取 "group" 后面的部分
            return avatar.substring(startIndex);
        } else {
            return avatar;
        }
    }
}
