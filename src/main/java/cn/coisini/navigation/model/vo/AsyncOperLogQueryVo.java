package cn.coisini.navigation.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author: xiaoxiang
 * Description: 操作日志vo
 */
@Data
@ApiModel(description = "操作日志vo")
public class AsyncOperLogQueryVo {
    @ApiModelProperty("标题")
    private String title;
    @ApiModelProperty("操作人名称")
    private String operName;
    @ApiModelProperty("开始时间")
    private String createTimeBegin;
    @ApiModelProperty("结束时间")
    private String createTimeEnd;
    @ApiModelProperty("当前页")
    private Long current;
    @ApiModelProperty("每页几条数据")
    private Long limit;
}
