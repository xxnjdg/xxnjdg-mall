package io.xxnjdg.mall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xxnjdg.common.utils.PageUtils;
import io.xxnjdg.mall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author xxnjdg
 * @email 1422570468@qq.com
 * @date 2020-06-04 11:52:46
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

