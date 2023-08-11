package cn.coisini.navigation.mapper;

import cn.coisini.navigation.model.pojos.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * Author: xiaoxiang
 * Description: 角色
 */
// @Mapper 让mapper的实现类能被启动类扫描同时创建bean，启动类上有MapperScan就只需要注册bean
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
}
