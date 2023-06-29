package cn.coisini.navigation.model.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * Author: xiaoxiang
 * Description: 类别 查询条件
 */
public class SortQueryVo {
    @ApiModelProperty(value = "类别名称", required = false)
    private String sortName;
    // 当前页
    private Long current;
    // 每页条数
    private Long limit;

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }
}
