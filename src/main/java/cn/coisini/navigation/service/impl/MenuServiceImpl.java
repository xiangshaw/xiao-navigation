package cn.coisini.navigation.service.impl;


import cn.coisini.navigation.mapper.MenuMapper;
import cn.coisini.navigation.mapper.RoleMenuMapper;
import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.common.enums.ResultEnum;
import cn.coisini.navigation.model.pojos.Menu;
import cn.coisini.navigation.model.pojos.RoleMenu;
import cn.coisini.navigation.model.pojos.UserRole;
import cn.coisini.navigation.model.vo.AssginMenuVo;
import cn.coisini.navigation.model.vo.RouterVo;
import cn.coisini.navigation.service.MenuService;
import cn.coisini.navigation.utils.RouterHelperUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.coisini.navigation.model.common.constant.Constants.FALSE;
import static cn.coisini.navigation.model.common.constant.Constants.STATUS;


/**
 * Author: xiaoxiang
 * Description: 菜单 服务实现类
 */
@Service
public class MenuServiceImpl extends MPJBaseServiceImpl<MenuMapper, Menu> implements MenuService {
    private final MenuMapper menuMapper;
    private final RoleMenuMapper roleMenuMapper;

    public MenuServiceImpl(MenuMapper menuMapper, RoleMenuMapper roleMenuMapper) {
        this.menuMapper = menuMapper;
        this.roleMenuMapper = roleMenuMapper;
    }

    @Override
    public List<Menu> findNodes() {
        // 获取所有菜单
        List<Menu> menuList = baseMapper.selectList(null);
        // 构建树形数据
        return buildTree(menuList);
    }

    // 新增菜单
    @Override
    public Result<Menu> saveMenu(Menu menu) {
        // 1.检查参数
        if (menu == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 查询数据库菜单信息
        List<Menu> list = list(Wrappers.<Menu>lambdaQuery().eq(Menu::getName, menu.getName()));
        if (list != null && list.size() == 1) {
            return Result.error(ResultEnum.DATA_EXIST, "菜单名称重复");
        }
        boolean b = save(menu);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "添加菜单失败");
    }

    // 修改菜单
    @Override
    public Result<Menu> updateMenuById(Menu menu) {
        // 1.检查参数
        if (menu == null && menu.getId() == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        boolean b = updateById(menu);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "修改失败");
    }

    // 删除菜单
    @Override
    public Result<Menu> removeMenuById(Long id) {
        // 查询当前菜单下面是否有子菜单
        Long count = baseMapper.selectCount(new QueryWrapper<Menu>().eq("parent_id", id));
        if (count > 0) {
            return Result.error(ResultEnum.FAIL, "删除失败：该菜单有子菜单");
        }
        baseMapper.deleteById(id);
        return Result.ok(ResultEnum.SUCCESS);
    }

    // 根据角色获取菜单
    @Override
    public List<Menu> findMenuByRoleId(String id) {
        // 获取所有菜单 status = 0 列表
        List<Menu> allMenu = baseMapper.selectList(new QueryWrapper<Menu>().eq(STATUS, FALSE));
        // 根据角色id获取菜单
        List<RoleMenu> roleMenu = roleMenuMapper.selectList(new QueryWrapper<RoleMenu>().eq("role_id", id));
        // 获取该角色已分配的所有权限id
        List<Long> roleMenuId = roleMenu.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        // 遍历权限数据处理：isSelect 如果菜单选中为true，否则false
        allMenu.forEach(menuEntity ->
                // 拿着分配菜单id 和 所有菜单比对，有相同的，让isSelect值为true（设置该权限已被分配）
                menuEntity.setSelect(roleMenuId.contains(menuEntity.getId()))
        );
        // 将权限列表转换为权限树
        return buildTree(allMenu);
    }

    // 给角色分配权限
    @Override
    public Result<Menu> doAssign(AssginMenuVo menuVo) {
        // 根据角色id删除已分配的菜单权限
        roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id", menuVo.getRoleId()));
        // 遍历菜单id
        List<Long> menuIdList = menuVo.getMenuIdList();
        menuIdList.forEach(menuId -> {
            // 创建RoleMenu对象
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(menuVo.getRoleId());
            roleMenu.setMenuId(menuId);
            // 添加菜单
            roleMenuMapper.insert(roleMenu);
        });
        return Result.ok(ResultEnum.SUCCESS);
    }

    // 根据用户id获取菜单权限值
    @Override
    public List<RouterVo> findUserMenuList(String userId) {
        // 超级管理员（可操作所有内容）admin账号id为：1
        List<Menu> menuList;
        if (userId.equals("1")) {
            // 超级管理员查询所有权限，且状态为0，sort_value排序
            menuList = baseMapper.selectList(new QueryWrapper<Menu>().eq(STATUS, FALSE).orderByAsc("sort_value"));
        } else {
            // 如果不是1，其他类型用户，查询这个用户权限
            menuList = findMenuListUserId(userId);
        }
        // 构建树形数据
        List<Menu> menuTreeList = buildTree(menuList);
        // 数据转换成前端路由格式
        return RouterHelperUtil.buildRouters(menuTreeList);
    }

    /**
     * 如果不是1，其他类型用户，查询这个用户权限
     * 使用mybatis-plus-join，使用方法详情见https://gitee.com/best_handsome/mybatis-plus-join/wikis/%E4%BD%BF%E7%94%A8
     * 完成连表查询实现语句：
     * SELECT DISTINCT
     * m.id,m.parent_id,m.name,m.type,m.path,m.component,m.perms,m.icon,m.sort_value,m.status,m.create_time,m.update_time,m.del_flag
     * FROM menu m
     * INNER JOIN role_menu rm ON m.id=rm.menu_id
     * INNER JOIN user_role ur ON rm.role_id=ur.role_id
     * WHERE ur.user_id=#{userId}
     * AND m.status = 0
     * AND m.del_flag = 0
     */
    private List<Menu> findMenuListUserId(String userId) {
        return menuMapper.selectJoinList(Menu.class, new MPJLambdaWrapper<Menu>()
                // 查询菜单全部字段
                .selectAll(Menu.class)
                // 参数1：参与连表的实体类class  参数2：连表的ON字段，这个属性必需是第一个参数实体类的属性 参数3：参与连表的ON的另一个实体类属性
                // 角色菜单（INNER JOIN role_menu rm ON m.id=rm.menu_id）
                .leftJoin(RoleMenu.class, RoleMenu::getMenuId, Menu::getId)
                //用户角色（INNER JOIN user_role ur ON rm.role_id=ur.role_id）
                .leftJoin(UserRole.class, UserRole::getRoleId, RoleMenu::getRoleId)
                // 根据用户id查询
                .eq(UserRole::getUserId, userId)
                // 菜单状态0
                .eq(Menu::getStatus, FALSE)
                // 菜单是否删除
                .eq(Menu::getDelFlag, FALSE));
    }

    // 根据用户id获取用户按钮权限
    @Override
    public List<String> findUserButtonList(String userId) {
        // 超级管理员(可操作所有内容)admin账号id为：1
        List<Menu> menuList;
        if (userId.equals("1")) {
            // 超级管理员查询所有权限，且状态为0
            menuList = baseMapper.selectList(new QueryWrapper<Menu>().eq(STATUS, FALSE).orderByAsc("sort_value"));
        } else {
            // 如果不是1，其他类型用户，查询这个用户权限
            menuList = findMenuListUserId(userId);
        }
        // 筛选出按钮
        return menuList.stream()
                .filter(item -> item.getType() == 2)
                .map(Menu::getPerms)
                .collect(Collectors.toList());
    }

    // 更新菜单状态(0正常 1禁用)
    @Override
    public Result<Menu> updateStatus(Long id, Boolean status) {
        Menu menu = new Menu();
        menu.setId(id);
        menu.setStatus(status);
        menu.setUpdateTime(new Date());
        int i = baseMapper.updateById(menu);
        if (i == 1) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "修改状态失败");
    }

    /**
     * 构建树型结构
     */
    private List<Menu> buildTree(List<Menu> menuList) {
        return menuList.stream().filter(menu -> menu.getParentId() == 0)
                .map(menu -> {
                    // 设置子菜单
                    menu.setChildren(getChildrens(menu, menuList));
                    return menu;
                })
                .collect(Collectors.toList());
    }

    /**
     * 递归查找子节点
     */
    // 从根节点进行查询子节点
    // 判断id=parentid是否相同，如果相同则是子节点，进行数据封装
    private List<Menu> getChildrens(Menu menu, List<Menu> menuList) {
        return menuList.stream()
                // 获取当前菜单id -- 获取所有菜单parentid 进行 比对
                .filter(submenu -> Objects.equals(submenu.getParentId(), menu.getId()))
                .map(submenu -> {
                    submenu.setChildren(getChildrens(submenu, menuList));
                    return submenu;
                }).collect(Collectors.toList());
    }
}
