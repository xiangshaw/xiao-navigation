package cn.coisini.navigation.model.common.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Author: xiaoxiang
 * Description:
 */
public class PageRequest {
    @ApiModelProperty(value="每页显示条数",required = true)/*required 参数必须要传*/
    protected Integer size;
    @ApiModelProperty(value="当前页",required = true)
    protected Integer page;

    public void checkParam() {
        /*等于null或者小于0,赋予默认值第1页*/
        if (this.page == null || this.page < 0) {
            setPage(1);
        }
        /*等于null或者小于0或大于100,赋予默认值10条数据*/
        if (this.size == null || this.size < 0 || this.size > 100) {
            setSize(10);
        }
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }
}
