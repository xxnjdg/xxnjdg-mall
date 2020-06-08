package io.xxnjdg.mall.product.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xxnjdg.common.utils.PageUtils;
import io.xxnjdg.common.utils.Query;

import io.xxnjdg.mall.product.dao.AttrAttrgroupRelationDao;
import io.xxnjdg.mall.product.entity.AttrAttrgroupRelationEntity;
import io.xxnjdg.mall.product.service.AttrAttrgroupRelationService;


@Service("attrAttrgroupRelationService")
@org.apache.dubbo.config.annotation.Service(protocol = "dubbo", version = "1.0.0")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

}