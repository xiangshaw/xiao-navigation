package cn.coisini.navigation.model.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: xiaoxiang
 * Description: 类别 - 实体类
 */
@Data
@TableName("sort")
@ApiModel(value = "Sort", description = "类别")
public class Sort implements Serializable {
    @ApiModelProperty(value = "类别ID", required = true, example = "7072624308312477713")
    @TableId("sort_id")
    private String id;
    @ApiModelProperty(value = "类别名",required = true)
    @TableField("sort_name")
    private String sortName;
    @ApiModelProperty("类别描述")
    @TableField("description")
    private String description;
    @ApiModelProperty("排序")
    @TableField("ord")
    private Integer ord;
    @ApiModelProperty("类别状态 0正常 1禁用")
    @TableField("status")
    private Boolean status;
    @ApiModelProperty("类别创建时间")
    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
    @ApiModelProperty("类别修改时间")
    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;
    @ApiModelProperty("类别下的标签")
    @TableField(exist = false)
    private List<Tag> tags = new ArrayList<>();
}

