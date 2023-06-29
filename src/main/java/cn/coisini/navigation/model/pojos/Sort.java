package cn.coisini.navigation.model.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: xiaoxiang
 * Description: 类别 - 实体类
 */
@TableName("sort")
@ApiModel(value = "Sort", description = "类别")
public class Sort implements Serializable {
    @ApiModelProperty(value = "类别ID", required = true, example = "7072624308312477713")
    @TableId("sort_id")
    private String sortId;
    @ApiModelProperty("类别名")
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

    public Sort(String sortId, String sortName, String description, Integer ord, Boolean status, Date createTime, Date updateTime) {
        this.sortId = sortId;
        this.sortName = sortName;
        this.description = description;
        this.ord = ord;
        this.status = status;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getSortId() {
        return sortId;
    }

    public void setSortId(String sortId) {
        this.sortId = sortId;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getOrd() {
        return ord;
    }

    public void setOrd(Integer ord) {
        this.ord = ord;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Sort{" +
                "sortId='" + sortId + '\'' +
                ", sortName='" + sortName + '\'' +
                ", description='" + description + '\'' +
                ", ord=" + ord +
                ", status=" + status +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

