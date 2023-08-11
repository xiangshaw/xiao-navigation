package cn.coisini.navigation.service;

import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.Menu;
import cn.coisini.navigation.model.vo.AssginMenuVo;
import cn.coisini.navigation.model.vo.RouterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Author: xiaoxiang
 * Description: 菜单 服务类
 */
public interface MenuService extends IService<Menu> {
    // 菜单列表 菜单树形数据
    List<Menu> findNodes();
    // 新增菜单
    Result<Menu> saveMenu(Menu menu);
    // 修改菜单
    Result<Menu> updateMenuById(Menu menu);
    // 根据id删除菜单，(如果有子菜单不能删除)
    Result<Menu> removeMenuById(Long id);
    // 根据角色id获取菜单
    List<Menu> findMenuByRoleId(String id);
    // 给角色分配菜单权限
    Result<Menu> doAssign(AssginMenuVo menuVo);
    // 根据用户id查询菜单权限值
    List<RouterVo> findUserMenuList(String userId);
    // 根据用户id查询按钮权限值
    List<String> findUserButtonList(String userId);
    // 更新菜单状态(0正常 1禁用)
    Result<Menu> updateStatus(Long id, Boolean status);
}
