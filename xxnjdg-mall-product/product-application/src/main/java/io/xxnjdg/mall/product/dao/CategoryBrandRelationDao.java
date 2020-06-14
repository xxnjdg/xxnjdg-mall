package io.xxnjdg.mall.product.dao;

import io.xxnjdg.mall.product.entity.CategoryBrandRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 品牌分类关联
 * 
 * @author xxnjdg
 * @email 1422570468@qq.com
 * @date 2020-06-04 08:07:12
 */
@Mapper
public interface CategoryBrandRelationDao extends BaseMapper<CategoryBrandRelationEntity> {

    /**
     * 更新品牌
     * @param catId
     * @param name
     */
    void updateCategory(@Param("catId") Long catId, @Param("name") String name);
}
