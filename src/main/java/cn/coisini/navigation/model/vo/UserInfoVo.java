package cn.coisini.navigation.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: xiaoxiang
 * Description: 用户信息
 */
@Data
@ApiModel(value = "UserInfoVo", description = "用户信息")
public class UserInfoVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID", required = true, example = "7072624308312477713")
    private String id;
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("呢称")
    private String nickname;
    @ApiModelProperty("手机")
    private String phone;
    @ApiModelProperty("头像地址")
    private String avatar;
    @ApiModelProperty("描述")
    private String description;
    @ApiModelProperty("状态（0正常 1停用）")
    private Boolean status;
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("删除标记（0可用 1已删除）")
    private Integer delFlag;
}
