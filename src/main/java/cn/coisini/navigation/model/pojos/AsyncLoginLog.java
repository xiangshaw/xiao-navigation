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
 * Description: 系统访问记录
 */
@Data
@TableName("login_log")
@ApiModel(value = "AsyncLoginLog", description = "系统访问记录")
public class AsyncLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    @ApiModelProperty(value = "系统访问记录Id", required = true, example = "7072624308312477713")
    private String id;

    @TableField("username")
    @ApiModelProperty("用户名称")
    private String username;

    @TableField("user_id")
    @ApiModelProperty("用户ID")
    private String userId;

    @TableField("login_ip")
    @ApiModelProperty("登录IP")
    private String loginIp;

    @TableField("login_ip_source")
    @ApiModelProperty("登录IP地址")
    private String loginIpSource;

    @TableField("login_ip_city")
    @ApiModelProperty("登录IP省市区")
    private String loginIpCity;

    @TableField("status")
    @ApiModelProperty("登录状态（0成功 1失败）")
    private Boolean status;

    @TableField("msg")
    @ApiModelProperty("提示信息")
    private String msg;

    @TableField("access_time")
    @ApiModelProperty("访问时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date accessTime;

    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @TableField("del_flag")
    @ApiModelProperty("删除标记（0可用 1已删除）")
    private Boolean delFlag;
}