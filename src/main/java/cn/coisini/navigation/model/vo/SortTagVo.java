package cn.coisini.navigation.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * Author: xiaoxiang
 * Description:
 */
@ApiModel(value ="SortTagVo" ,description = "分配标签")
@Data
public class SortTagVo {
    @ApiModelProperty(value = "标签id")
    private String tagId;

    @ApiModelProperty(value = "类别id列表")
    private List<String> sortIdList;
}
