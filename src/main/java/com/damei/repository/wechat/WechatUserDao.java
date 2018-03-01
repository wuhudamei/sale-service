package com.damei.repository.wechat;

import com.damei.entity.wechat.WechatUser;
import com.damei.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WechatUserDao extends CrudDao<WechatUser> {
    void batchInsert(@Param(value = "users") List<WechatUser> users);

    List<WechatUser> getByNickname(@Param("nickname") String nickname);

    void clean();
}