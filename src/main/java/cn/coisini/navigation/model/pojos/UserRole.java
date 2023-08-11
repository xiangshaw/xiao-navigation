package cn.coisini.navigation.model.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: xiaoxiang
 * Description: 用户角色
 */
@Data
@TableName("user_role")
@ApiModel(value = "UserRole", description = "用户角色")
public class UserRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户角色ID", required = true, example = "7072624308312477713")
    @TableId("id")
    private String id;

    @TableField("role_id")
    @ApiModelProperty("角色id")
    private String roleId;

    @TableField("user_id")
    @ApiModelProperty("用户id")
    private String userId;
}
