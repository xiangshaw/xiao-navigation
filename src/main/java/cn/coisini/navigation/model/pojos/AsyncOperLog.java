package cn.coisini.navigation.model.pojos;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: xiaoxiang
 * Description: 操作日志记录
 */
@Data
@TableName("oper_log")
@ApiModel(value = "AsyncOperLog", description = "操作日志记录")
public class AsyncOperLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    @ApiModelProperty(value = "操作日志ID", required = true, example = "7072624308312477713")
    private String id;

    @TableField("title")
    @ApiModelProperty("模块标题")
    private String title;

    @TableField("business_type")
    @ApiModelProperty("业务类型（0其它 1新增 2修改 3删除）")
    private String businessType;

    @TableField("method")
    @ApiModelProperty("方法名称")
    private String method;

    @TableField("request_method")
    @ApiModelProperty("请求方式")
    private String requestMethod;

    @TableField("operator_type")
    @ApiModelProperty("操作类别（0其它 1后台用户 2手机端用户）")
    private String operatorType;

    @TableField("oper_user_id")
    @ApiModelProperty("操作人员ID")
    private String operUserId;

    @TableField("oper_name")
    @ApiModelProperty("操作人员")
    private String operName;

    @TableField("dept_name")
    @ApiModelProperty("部门名称")
    private String deptName;

    @TableField("oper_url")
    @ApiModelProperty("请求URL")
    private String operUrl;

    @TableField("oper_ip")
    @ApiModelProperty("主机IP")
    private String operIp;

    @TableField("oper_ip_source")
    @ApiModelProperty("主机地址")
    private String operIpSource;
    @TableField("oper_ip_city")
    @ApiModelProperty("IP 省市区")
    private String operIpCity;

    @TableField("oper_param")
    @ApiModelProperty("请求参数")
    private String operParam;

    @TableField("json_result")
    @ApiModelProperty("返回参数")
    private String jsonResult;

    @TableField("status")
    @ApiModelProperty("操作状态（0正常 1异常）")
    private Boolean status;

    @TableField("error_msg")
    @ApiModelProperty("错误消息")
    private String errorMsg;

    @TableField("oper_time")
    @ApiModelProperty("操作时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date operTime;

    @TableField("create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date createTime;

    @TableField("update_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    private Date updateTime;

    @TableField("del_flag")
    @ApiModelProperty("删除标记（0可用 1已删除）")
    private Integer delFlag;
}