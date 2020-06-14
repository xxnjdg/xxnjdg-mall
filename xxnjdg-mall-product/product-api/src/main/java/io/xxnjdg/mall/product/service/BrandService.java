package io.xxnjdg.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xxnjdg.common.utils.PageUtils;
import io.xxnjdg.mall.product.entity.BrandEntity;

import java.util.Map;

/**
 * 品牌
 *
 * @author xxnjdg
 * @email 1422570468@qq.com
 * @date 2020-06-04 08:07:12
 */
public interface BrandService extends IService<BrandEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateDetail(BrandEntity brand);
}

