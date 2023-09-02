package cn.coisini.navigation.model.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: xiaoxiang
 * Description: 用户
 */
@Data
@TableName("user")
@ApiModel(value = "User", description = "用户")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID", required = true, example = "7072624308312477713")
    @TableId("id")
    private String id;

    @TableField("username")
    @ApiModelProperty("用户名")
    private String username;

    @TableField("password")
    @ApiModelProperty("密码")
    private String password;

    @TableField("nickname")
    @ApiModelProperty("姓名")
    private String nickname;

    @TableField("phone")
    @ApiModelProperty("手机")
    private String phone;

    @TableField("avatar")
    @ApiModelProperty("头像地址")
    private String avatar;

    @TableField("description")
    @ApiModelProperty("描述")
    private String description;

    @TableField("status")
    @ApiModelProperty("状态（0正常 1停用）")
    private Boolean status;

    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @ApiModelProperty("创建时间")
    private Date createTime;

    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @TableField("login_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @ApiModelProperty("登录时间")
    private Date loginTime;

    @TableField("del_flag")
    @ApiModelProperty("删除标记（0可用 1已删除）")
    private Integer delFlag;
}
