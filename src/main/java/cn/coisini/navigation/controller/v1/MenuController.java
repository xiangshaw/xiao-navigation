package cn.coisini.navigation.controller.v1;

import cn.coisini.navigation.common.log.annotation.Log;
import cn.coisini.navigation.common.log.enums.BusinessType;
import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.pojos.Menu;
import cn.coisini.navigation.model.vo.AssginMenuVo;
import cn.coisini.navigation.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Author: xiaoxiang
 * Description: 菜单 前端控制器
 */
@Api(tags = "菜单管理接口")
@RestController
@RequestMapping("/api/v1/menu")
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PreAuthorize("hasAuthority('menu:list')")
    @ApiOperation("菜单列表")
    @GetMapping("/findNodes")
    public Result<List<Menu>> findNodes() {
        return Result.ok(menuService.findNodes());
    }

    @ApiOperation("根据id查询菜单")
    @GetMapping("/info/{id}")
    public Result<Menu> info(@PathVariable("id") Long id) {
        Menu menu = menuService.getById(id);
        return Result.ok(menu);
    }

    @PreAuthorize("hasAuthority('menu:add')")
    @Log(title = "菜单管理",businessType = BusinessType.INSERT)
    @ApiOperation("添加菜单")
    @PostMapping("/save")
    public Result<Menu> saveMenu(@RequestBody Menu menu) {
        return menuService.saveMenu(menu);
    }

    @PreAuthorize("hasAuthority('menu:update')")
    @Log(title = "菜单管理",businessType = BusinessType.UPDATE)
    @ApiOperation("修改菜单")
    @PutMapping("/update")
    public Result<Menu> updateMenu(@RequestBody Menu menu) {
        return menuService.updateMenuById(menu);
    }

    @PreAuthorize("hasAuthority('menu:remove')")
    @Log(title = "菜单管理",businessType = BusinessType.DELETE)
    @ApiOperation("根据id删除菜单")
    @DeleteMapping("/remove/{id}")
    public Result<Menu> remove(@PathVariable("id") Long id) {
        return menuService.removeMenuById(id);

    }

    @ApiOperation("根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result<List<Menu>> toAssign(@PathVariable("roleId") String id) {
        return Result.ok(menuService.findMenuByRoleId(id));
    }

    @PreAuthorize("hasAuthority('role:assignAuth')")
    @Log(title = "菜单管理",businessType = BusinessType.ASSGIN)
    @ApiOperation("给角色分配权限")
    @PostMapping("/doAssign")
    public Result<Menu> doAssign(@RequestBody AssginMenuVo menuVo) {
        return menuService.doAssign(menuVo);
    }

    @ApiOperation("更改菜单状态(0启用 1禁用)")
    @Log(title = "菜单管理",businessType = BusinessType.STATUS)
    @GetMapping("/updateStatus/{id}/{status}")
    public Result<Menu> status(@PathVariable("id") Long id,
                         @PathVariable("status") Boolean status) {
        return menuService.updateStatus(id, status);
    }
}
