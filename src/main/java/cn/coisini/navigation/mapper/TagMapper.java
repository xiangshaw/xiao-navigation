package cn.coisini.navigation.mapper;

import cn.coisini.navigation.model.pojos.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: xiaoxiang
 * Description: 标签 - 数据访问层
 */
@Mapper
public interface TagMapper extends BaseMapper<Tag> {
}
