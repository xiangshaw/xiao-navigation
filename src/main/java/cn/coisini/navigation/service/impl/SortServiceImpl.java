package cn.coisini.navigation.service.impl;

import cn.coisini.navigation.mapper.SortMapper;
import cn.coisini.navigation.model.common.dto.Result;
import cn.coisini.navigation.model.common.enums.ResultEnum;
import cn.coisini.navigation.model.pojos.Sort;
import cn.coisini.navigation.model.vo.SortQueryVo;
import cn.coisini.navigation.service.SortService;
import cn.coisini.navigation.utils.IdWorker;
import cn.coisini.navigation.utils.PageUtils;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author: xiaoxiang
 * Description: 类别 - 接口实现类
 */
@Service
public class SortServiceImpl extends ServiceImpl<SortMapper, Sort> implements SortService {
    // 根据ID查询类别
    @Override
    public Result<Sort> findIdBySort(String id) {
        // 1.检查参数
        if (id == null){
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        Sort sort = getById(id);
        // 检查结果
        if (sort == null){
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        return Result.ok(sort);
    }

    // 查询所有类别
    @Override
    public Result<Sort> qbcSort(SortQueryVo sortQueryVo) {
        // 获取条件
        String sortName = sortQueryVo.getSortName();
        // 封装条件
        QueryWrapper<Sort> wrapper = new QueryWrapper<>();
        // 根据 类别名称 模糊查询
        if (!CharSequenceUtil.isEmpty(sortName)) {
            wrapper.like("sort_name", sortName);
        }
        // 排序
        wrapper.orderByDesc("sort_id");
        // 分页条件
        Page<Sort> page = new Page<>(sortQueryVo.getCurrent(), sortQueryVo.getLimit());
        Page<Sort> sortPage = page(page, wrapper);
        // 返回结果
        return Result.ok(new PageUtils(sortPage));
    }

    @Override
    public Result<Sort> saveSort(Sort sort) {
        // 1.检查参数
        if (sort == null) {
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.查询数据库
        List<Sort> list = list(Wrappers.lambdaQuery(Sort.class).eq(Sort::getSortName, sort.getSortName()));
        if (list != null && list.size() == 1) {
            return Result.error(ResultEnum.DATA_EXIST, "该类别已经添加");
        }
        // 3.检查结果
        IdWorker worker = new IdWorker();
        // 生成分布式ID
        sort.setSortId(String.valueOf(worker.nextId()));
        sort.setCreateTime(new Date());
        boolean b = save(sort);
        if (b) {
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL, "类别添加失败");
    }

    // 类别修改
    @Override
    public Result<Sort> updateSort(Sort sort) {
        // 1.检查参数
        if (sort == null && sort.getSortId() == null){
            return Result.error(ResultEnum.PARAM_INVALID);
        }
        // 2.修改并检查结果
        boolean b = updateById(sort);
        if (b){
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL,"修改失败");
    }

    // 类别删除
    @Override
    public Result<Sort> removeSort(String id) {
        // 1.检查参数
        if (id == null){
            return Result.error(ResultEnum.PARAM_REQUIRE);
        }
        Sort sort = getById(id);
        if (sort == null ){
            return Result.error(ResultEnum.DATA_NOT_EXIST);
        }
        if (sort.getStatus()){
            return Result.error(ResultEnum.FAIL,"类别有效，不能删除");
        }
        // 2.执行删除并检查结果
        boolean b = removeById(id);
        if (b){
            return Result.ok(ResultEnum.SUCCESS);
        }
        return Result.error(ResultEnum.FAIL,"删除失败");
    }
}
