package io.xxnjdg.mall.member.dao;

import io.xxnjdg.mall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author xxnjdg
 * @email 1422570468@qq.com
 * @date 2020-06-04 11:47:31
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
