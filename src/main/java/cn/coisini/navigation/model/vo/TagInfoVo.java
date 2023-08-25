package cn.coisini.navigation.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Author: xiaoxiang
 * Description:
 */
@ApiModel(value ="TagInfoVo" ,description = "首页标签展示必要字段")
@Data
public class TagInfoVo {
    private String tagName;
    private String tagIcon;
    private String tagUrl;
    private String description;
    private int ord;
}
