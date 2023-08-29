package cn.coisini.navigation.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Author: xiaoxiang
 * Description: 登录日志vo
 */
@Data
@ApiModel(description = "登录日志vo")
public class AsyncLoginLogQueryVo {
    @ApiModelProperty("用户名")
    private String username;
    @ApiModelProperty("开始时间")
    private String createTimeBegin;
    @ApiModelProperty("结束时间")
    private String createTimeEnd;
    @ApiModelProperty("当前页")
    private Long current;
    @ApiModelProperty("每页几条数据")
    private Long limit;
}
