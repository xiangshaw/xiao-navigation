package cn.coisini.navigation.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * Author: xiaoxiang
 * Description: 查询条件定义
 */
@Data
@ApiModel(value = "QueryVo", description = "查询条件")
public class QueryVo implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "关键词", required = false)
    private String keyword;
    @ApiModelProperty(value = "当前页", required = false)
    private long current;
    @ApiModelProperty(value = "每页条数", required = false)
    private long limit;
}
