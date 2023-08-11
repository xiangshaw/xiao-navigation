package cn.coisini.navigation.service;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.Role;
import cn.coisini.navigation.model.vo.RoleQueryVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Author: xiaoxiang
 * Description: 角色 服务类
 */
public interface RoleService extends IService<Role> {
    // 根据id查询角色
    Result<Role> findRoleById(String id);
    // 条件分页查询
    Result<Role> pagingQuery(RoleQueryVo roleQueryVo);
    // 新增角色
    Result<Role> saveRole(Role role);
    // 修改角色
    Result<Role> updateRole(Role role);
    // 删除角色
    Result<Role> removeRole(String id);
    // 批量删除
    // json数组格式 ---对应---Java的list集合
    Result<List<Role>> batchRemove(List<String> ids);
}
