package cn.coisini.navigation.service.impl;

import cn.coisini.navigation.mapper.RoleMapper;
import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.common.enums.ResultEnum;
import cn.coisini.navigation.model.pojos.Role;
import cn.coisini.navigation.model.vo.RoleQueryVo;
import cn.coisini.navigation.service.RoleService;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static cn.coisini.navigation.model.common.constant.Constants.FALSE;


/**
 * Author: xiaoxiang
 * Description: 角色 服务实现类
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    // 根据id查询角色
    @Override
    public Result<Role> findRoleById(String id) {
        // 检查id
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        Role role = getById(id);
        // 检查返回结果
        if (role == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        return Result.ok(role);
    }

    // 条件分页查询
    @Override
    public Result<Role> pagingQuery(RoleQueryVo roleQueryVo) {
        // 获取条件值
        String roleName = roleQueryVo.getRoleName();
        // 封装条件
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        // 根据 角色名 模糊查询
        if (!CharSequenceUtil.isEmpty(roleName)) {
            wrapper.like("role_name", roleName);
        } // 排序
        wrapper.eq("del_flag", FALSE).orderByDesc("id");
        // 分页条件
        Page<Role> page = new Page<>(roleQueryVo.getCurrent(), roleQueryVo.getLimit());
        Page<Role> rolePage = page(page, wrapper);
        // 总条数
        rolePage.setTotal(rolePage.getRecords().size());
        return Result.ok(rolePage);

    }

    // 新增角色
    @Override
    public Result<Role> saveRole(Role role) {
        // 1.检查参数
        if (role.getRoleName() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.查询数据库
        List<Role> list = list(Wrappers.<Role>lambdaQuery().eq(Role::getRoleName, role.getRoleName()));
        if (list != null && list.size() == 1) {
            return Result.error(ResultEnum.DATA_EXIST, "该角色已经添加过了");
        }
        // 3.检查结果
        boolean b = save(role);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "角色添加失败");
    }

    // 修改角色
    @Override
    public Result<Role> updateRole(Role role) {
        // 1.检查参数
        if (role == null && role.getId() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.检查结果
        boolean b = updateById(role);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "修改角色失败");
    }

    // 删除角色
    @Override
    public Result<Role> removeRole(String id) {
        // 1.检查参数
        if (id == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.判断当前角色是否存在
        Role role = getById(id);
        if (role == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        // 3.删除并判断结果
        boolean b = removeById(id);
        if (b) {
            return Result.error(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "删除角色失败");
    }

    // 批量删除
    @Override
    @Transactional
    public Result<List<Role>> batchRemove(List<String> ids) {
        // 1.检查参数
        if (ids == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.判断当前角色是否存在 和 是否有效
        List<Role> roleList = listByIds(ids);
        if (roleList == null) {
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        // 3.删除并检查结果
        boolean b = removeByIds(ids);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "删除角色失败");
    }
}
