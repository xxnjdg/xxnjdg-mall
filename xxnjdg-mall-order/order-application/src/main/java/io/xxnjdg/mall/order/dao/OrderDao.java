package io.xxnjdg.mall.order.dao;

import io.xxnjdg.mall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author xxnjdg
 * @email 1422570468@qq.com
 * @date 2020-06-04 11:52:46
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
