package cn.coisini.navigation.controller.v1;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.User;
import cn.coisini.navigation.model.pojos.UserRole;
import cn.coisini.navigation.model.vo.AssginRoleVo;
import cn.coisini.navigation.model.vo.UserQueryVo;
import cn.coisini.navigation.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Author: xiaoxiang
 * Description: 用户 前端控制器
 */
@Api(tags = "用户管理接口")
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    // 使用构造器注入，代替@Resource、@Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasAuthority('user:list')")
    @ApiOperation("用户列表")
    @GetMapping("/list")
    public Result<User> pagingQuery(UserQueryVo userQueryVo) {
        return userService.pagingQuery(userQueryVo);
    }

    @ApiOperation("根据id获取用户")
    @GetMapping("/findUserById/{id}")
    public Result<User> getUserId(@PathVariable("id") String id) {
        return userService.getUserId(id);
    }

    @PreAuthorize("hasAuthority('user:add')")
    @ApiOperation("添加用户")
    @PostMapping("/save")
    public Result<User> saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PreAuthorize("hasAuthority('user:update')")
    @ApiOperation("修改用户")
    @PutMapping("/update")
    public Result<User> update(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @PreAuthorize("hasAuthority('user:remove')")
    @ApiOperation("删除用户")
    @DeleteMapping("/remove/{id}")
    public Result<User> remove(@PathVariable("id") String id) {
        return userService.removeUser(id);
    }

    @ApiOperation("更改用户状态(0启用 1禁用)")
    @GetMapping("/updateStatus/{id}/{status}")
    public Result<User> status(@PathVariable("id") String id,
                         @PathVariable("status") Boolean status) {
        return userService.updateStatus(id, status);
    }

    @ApiOperation("根据用户id获取角色信息数据")
    @GetMapping("/toAssign/{id}")
    public Result<Map<String, Object>> getRolesByUserId(@PathVariable("id") String id) {
        return userService.getRolesByUserId(id);
    }

    @PreAuthorize("hasAuthority('user:assignRole')")
    @ApiOperation("给用户分配角色")
    @PostMapping("/doAssign")
    public Result<UserRole> doAssign(@RequestBody AssginRoleVo assginRoleVo) {
        return userService.doAssign(assginRoleVo);
    }
}
