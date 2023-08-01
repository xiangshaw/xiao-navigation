package cn.coisini.navigation.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Author: xiaoxiang
 * Description: 标签Vo
 */
@Data
@ApiModel(value = "TagVo", description = "标签Vo")
public class TagVo {
    @ApiModelProperty(value = "标签ID", required = true, example = "7072624308312477713")
    private String tagId;
    @ApiModelProperty(value = "列别ID", required = true, example = "7072624308312477713")
    private String sortId;
    @ApiModelProperty("标签名")
    private String tagName;
    @ApiModelProperty("标签图标")
    private String tagIcon;
    @ApiModelProperty("标签地址")
    private String tagUrl;
    @ApiModelProperty("标签描述")
    private String description;
    @ApiModelProperty("排序")
    private Integer ord;
    @ApiModelProperty("类别状态 0正常 1禁用")
    private Boolean status;
    @ApiModelProperty("标签创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;
}
