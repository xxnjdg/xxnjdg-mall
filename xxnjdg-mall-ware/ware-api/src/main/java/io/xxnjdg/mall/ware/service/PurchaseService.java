package io.xxnjdg.mall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xxnjdg.common.utils.PageUtils;
import io.xxnjdg.mall.ware.entity.PurchaseEntity;

import java.util.Map;

/**
 * 采购信息
 *
 * @author xxnjdg
 * @email 1422570468@qq.com
 * @date 2020-06-04 11:56:48
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

