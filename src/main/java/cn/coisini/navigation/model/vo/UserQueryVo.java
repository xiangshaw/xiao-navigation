package cn.coisini.navigation.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * Author: xiaoxiang
 * Description: 用户查询实体
 */
@Data
public class UserQueryVo implements Serializable {

    private static final long serialVersionUID = 1L;
    // 当前页
    private Long current;
    // 每页几条数据
    private Long limit;
    // 关键词
    private String keyword;
    // 开始时间
    private String createTimeBegin;
    // 结束时间
    private String createTimeEnd;
    // 角色id
    private String roleId;
}