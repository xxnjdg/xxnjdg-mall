package io.xxnjdg.mall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.xxnjdg.common.utils.PageUtils;
import io.xxnjdg.mall.member.dto.MemberRegisterDTO;
import io.xxnjdg.mall.member.entity.MemberEntity;

import java.util.Map;

/**
 * 会员
 *
 * @author xxnjdg
 * @email 1422570468@qq.com
 * @date 2020-06-04 11:47:31
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Boolean memberRegister(MemberRegisterDTO memberRegisterDTO);

    MemberEntity getMemberByUid(String uid);
}

