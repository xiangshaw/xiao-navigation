package cn.coisini.navigation.model.pojos;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Author: xiaoxiang
 * Description: 菜单
 */
@Data
@TableName("menu")
@ApiModel(value = "Menu", description = "菜单")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("编号")
    private Long id;

    @TableField("parent_id")
    @ApiModelProperty("所属上级")
    private Long parentId;

    @TableField("name")
    @ApiModelProperty("名称")
    private String name;

    @TableField("type")
    @ApiModelProperty("类型(0目录 1菜单 2按钮)")
    private Integer type;

    @TableField("path")
    @ApiModelProperty("路由地址")
    private String path;

    @TableField("component")
    @ApiModelProperty("组件路径")
    private String component;

    @TableField("perms")
    @ApiModelProperty("权限标识")
    private String perms;

    @TableField("icon")
    @ApiModelProperty("图标")
    private String icon;

    @TableField("sort_value")
    @ApiModelProperty("排序")
    private Integer sortValue;

    @TableField("status")
    @ApiModelProperty("状态(0:禁止,1:正常)")
    private Boolean status;

    @TableField("create_time")
    @ApiModelProperty("创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @TableField("update_time")
    @ApiModelProperty("更新时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @TableField("del_flag")
    @ApiModelProperty("删除标记（0可用 1已删除）")
    private Integer delFlag;

    @TableField(exist = false)
    private List<Menu> children;

    @TableField(exist = false)
    private boolean isSelect;
}
