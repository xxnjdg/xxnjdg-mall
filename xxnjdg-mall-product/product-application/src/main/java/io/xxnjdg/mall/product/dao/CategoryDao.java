package io.xxnjdg.mall.product.dao;

import io.xxnjdg.mall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author xxnjdg
 * @email 1422570468@qq.com
 * @date 2020-06-04 08:07:12
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
