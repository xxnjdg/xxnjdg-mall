package io.xxnjdg.mall.coupon.dao;

import io.xxnjdg.mall.coupon.entity.CouponSpuRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券与产品关联
 * 
 * @author xxnjdg
 * @email 1422570468@qq.com
 * @date 2020-06-04 11:33:52
 */
@Mapper
public interface CouponSpuRelationDao extends BaseMapper<CouponSpuRelationEntity> {
	
}
