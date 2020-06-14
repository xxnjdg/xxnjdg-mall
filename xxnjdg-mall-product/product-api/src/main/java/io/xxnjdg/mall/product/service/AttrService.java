package io.xxnjdg.mall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xxnjdg.common.utils.PageUtils;
import io.xxnjdg.mall.product.entity.AttrEntity;
import io.xxnjdg.mall.product.vo.AttrGroupRelationVo;
import io.xxnjdg.mall.product.vo.AttrRespVo;
import io.xxnjdg.mall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author xxnjdg
 * @email 1422570468@qq.com
 * @date 2020-06-04 08:07:12
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type);

    AttrRespVo getAttrInfo(Long attrId);

    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(Long attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNoRelationAttr(Map<String, Object> params, Long attrgroupId);
}

