package cn.coisini.navigation.model.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: xiaoxiang
 * Description: 标签 - 实体类
 */
@Data
@TableName("tag")
@ApiModel(value = "Tag", description = "标签")
public class Tag implements Serializable {
    @ApiModelProperty(value = "标签ID", required = true, example = "7072624308312477713")
    @TableId("tag_id")
    private String tagId;
    @ApiModelProperty(value = "标签名", required = true)
    @TableField("tag_name")
    @NotEmpty(message = "标签名不能为空")
    private String tagName;
    @ApiModelProperty("标签图标")
    @TableField("tag_icon")
    private String tagIcon;
    @ApiModelProperty("标签地址")
    @TableField("tag_url")
    private String tagUrl;
    @ApiModelProperty("标签描述")
    @TableField("description")
    private String description;
    @ApiModelProperty("排序")
    @TableField("ord")
    private int ord;
    @ApiModelProperty("类别状态 0正常 1禁用")
    @TableField("status")
    private Boolean status;
    @ApiModelProperty("标签创建时间")
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    @ApiModelProperty("标签修改时间")
    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;
}

