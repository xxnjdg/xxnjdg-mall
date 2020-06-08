package io.xxnjdg.mall.order.service.impl;

import io.xxnjdg.common.utils.ServiceExceptionUtil;
import io.xxnjdg.mall.order.constant.OrderErrorCodeEnum;
import io.xxnjdg.mall.order.dto.TestDTO;
import io.xxnjdg.mall.product.constant.ProductErrorCodeEnum;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xxnjdg.common.utils.PageUtils;
import io.xxnjdg.common.utils.Query;

import io.xxnjdg.mall.order.dao.OrderReturnReasonDao;
import io.xxnjdg.mall.order.entity.OrderReturnReasonEntity;
import io.xxnjdg.mall.order.service.OrderReturnReasonService;


@Service("orderReturnReasonService")
@org.apache.dubbo.config.annotation.Service(protocol = "dubbo", version = "1.0.0",validation = "true")
public class OrderReturnReasonServiceImpl extends ServiceImpl<OrderReturnReasonDao, OrderReturnReasonEntity> implements OrderReturnReasonService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OrderReturnReasonEntity> page = this.page(
                new Query<OrderReturnReasonEntity>().getPage(params),
                new QueryWrapper<OrderReturnReasonEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public String test(TestDTO testDTO) {
        throw ServiceExceptionUtil.exception(OrderErrorCodeEnum.TEST_EXISTS.getCode());
//        return "test * 2";
    }

}