package cn.coisini.navigation.model.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: xiaoxiang
 * Description: 角色菜单
 */
@Data
@TableName("role_menu")
@ApiModel(value = "RoleMenu", description = "角色菜单")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("role_id")
    @ApiModelProperty("角色ID")
    private String roleId;

    @TableField("menu_id")
    @ApiModelProperty("菜单ID")
    private Long menuId;
}
