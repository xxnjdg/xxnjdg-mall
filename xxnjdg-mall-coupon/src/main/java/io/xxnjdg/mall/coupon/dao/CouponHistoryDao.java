package io.xxnjdg.mall.coupon.dao;

import io.xxnjdg.mall.coupon.entity.CouponHistoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券领取历史记录
 * 
 * @author xxnjdg
 * @email 1422570468@qq.com
 * @date 2020-06-04 11:33:52
 */
@Mapper
public interface CouponHistoryDao extends BaseMapper<CouponHistoryEntity> {
	
}
