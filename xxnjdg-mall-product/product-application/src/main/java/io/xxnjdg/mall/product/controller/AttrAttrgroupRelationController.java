package io.xxnjdg.mall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//import org.apache.shiro.authz.annotation.RequiresPermissions;
import io.xxnjdg.common.exception.ServiceException;
import io.xxnjdg.common.utils.ServiceExceptionUtil;
import io.xxnjdg.mall.order.dto.TestDTO;
import io.xxnjdg.mall.order.entity.OrderItemEntity;
import io.xxnjdg.mall.order.entity.OrderReturnReasonEntity;
import io.xxnjdg.mall.order.service.OrderItemService;
import io.xxnjdg.mall.order.service.OrderReturnReasonService;
import io.xxnjdg.mall.product.constant.ProductErrorCodeEnum;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.xxnjdg.mall.product.entity.AttrAttrgroupRelationEntity;
import io.xxnjdg.mall.product.service.AttrAttrgroupRelationService;
import io.xxnjdg.common.utils.PageUtils;
import io.xxnjdg.common.utils.R;



/**
 * 属性&属性分组关联
 *
 * @author xxnjdg
 * @email 1422570468@qq.com
 * @date 2020-06-04 10:01:58
 */
@RestController
@RequestMapping("product/attrattrgrouprelation")
public class AttrAttrgroupRelationController {
    @Autowired
    private AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Reference(version = "1.0.0",check = false)
    private OrderReturnReasonService orderReturnReasonService;

    @RequestMapping("/test")
    public R test(Integer param){

        String test = orderReturnReasonService.test(new TestDTO());
//        List<OrderReturnReasonEntity> list = orderReturnReasonService.list();
        if (Objects.equals(param,1)){
            throw ServiceExceptionUtil.exception(ProductErrorCodeEnum.TEST_EXISTS.getCode());
        }

        return R.ok().put("list",test);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("product:attrattrgrouprelation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = attrAttrgroupRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    //@RequiresPermissions("product:attrattrgrouprelation:info")
    public R info(@PathVariable("id") Long id){
		AttrAttrgroupRelationEntity attrAttrgroupRelation = attrAttrgroupRelationService.getById(id);

        return R.ok().put("attrAttrgroupRelation", attrAttrgroupRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:attrattrgrouprelation:save")
    public R save(@RequestBody AttrAttrgroupRelationEntity attrAttrgroupRelation){
		attrAttrgroupRelationService.save(attrAttrgroupRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:attrattrgrouprelation:update")
    public R update(@RequestBody AttrAttrgroupRelationEntity attrAttrgroupRelation){
		attrAttrgroupRelationService.updateById(attrAttrgroupRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    //@RequiresPermissions("product:attrattrgrouprelation:delete")
    public R delete(@RequestBody Long[] ids){
		attrAttrgroupRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
