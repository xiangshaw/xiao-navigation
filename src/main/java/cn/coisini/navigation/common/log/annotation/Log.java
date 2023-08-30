package cn.coisini.navigation.common.log.annotation;

import cn.coisini.navigation.common.log.enums.BusinessType;
import cn.coisini.navigation.common.log.enums.OperatorType;

import java.lang.annotation.*;

/**
 * Author: xiaoxiang
 * Description: 自定义操作日志记录注解
 */
// 该注解@Target表示能用在什么地方(PARAMETER参数位置,METHOD方法上)
@Target({ElementType.PARAMETER,ElementType.METHOD})
// 注解的作用范围(RUNTIME运行时起作用)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * 模块
     */
    String title() default "";

    /**
     * 功能
     */
    BusinessType businessType() default BusinessType.OTHER;

    /**
     * 操作人类别
     */
    OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * 是否保存请求的参数
     */
    boolean saveRequestData() default true;

    /**
     * 是否保存响应的参数
     */
    boolean saveResponseData() default true;
}