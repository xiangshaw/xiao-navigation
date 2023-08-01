package cn.coisini.navigation.model.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author: xiaoxiang
 * Description: 类别 与 标签
 */
@Data
@TableName("sort_tag")
@ApiModel(value = "Sort_Tag", description = "一个类别对应多个标签")
public class SortTag {

    @ApiModelProperty(value = "类别标签关系ID", required = true, example = "7072624308312477713")
    @TableId("id")
    private String id;
    @ApiModelProperty(value = "类别ID", required = true, example = "7072624308312477713")
    @TableField("sort_id")
    private String sortId;

    @ApiModelProperty(value = "标签ID", required = true, example = "7072624308312477713")
    @TableField("tag_id")
    private String tagId;
}
