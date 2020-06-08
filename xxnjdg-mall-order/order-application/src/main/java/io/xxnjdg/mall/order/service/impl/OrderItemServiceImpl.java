package io.xxnjdg.mall.order.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xxnjdg.common.utils.PageUtils;
import io.xxnjdg.common.utils.Query;

import io.xxnjdg.mall.order.dao.OrderItemDao;
import io.xxnjdg.mall.order.entity.OrderItemEntity;
import io.xxnjdg.mall.order.service.OrderItemService;


@Service("orderItemService")
@org.apache.dubbo.config.annotation.Service(protocol = "dubbo", version = "1.0.0")
public class OrderItemServiceImpl extends ServiceImpl<OrderItemDao, OrderItemEntity> implements OrderItemService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderItemEntity> page = this.page(
                new Query<OrderItemEntity>().getPage(params),
                new QueryWrapper<OrderItemEntity>()
        );

        return new PageUtils(page);
    }

}