package io.xxnjdg.mall.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.xxnjdg.mall.member.dto.MemberRegisterDTO;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.xxnjdg.common.utils.PageUtils;
import io.xxnjdg.common.utils.Query;

import io.xxnjdg.mall.member.dao.MemberDao;
import io.xxnjdg.mall.member.entity.MemberEntity;
import io.xxnjdg.mall.member.service.MemberService;


@Service("memberService")
@org.apache.dubbo.config.annotation.Service(protocol = "dubbo", version = "1.0.0",validation = "true")
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public Boolean memberRegister(MemberRegisterDTO memberRegisterDTO) {
        // TODO: 2020/6/30 注册逻辑
        //1 查询memberLevel
        //2 检查两次密码是否相同 手机号 姓名是否唯一
        //3 加密密码
        //4 填充 MemberEntity 并 插入
        return true;
    }

    @Override
    public MemberEntity getMemberByUid(String uid) {
        LambdaQueryWrapper<MemberEntity> wrapper = new QueryWrapper<MemberEntity>()
                .lambda()
                .eq(MemberEntity::getUid, uid);

        return this.getOne(wrapper);
    }


}