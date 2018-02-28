package com.rocoinfo.repository.wechat;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.wechat.WechatUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WechatUserDao extends CrudDao<WechatUser> {
    /**
     * 将微信用户信息批量插入到数据库中
     *
     * @param users 用户信息
     */
    void batchInsert(@Param(value = "users") List<WechatUser> users);

    /**
     * 根据用户昵称查询用户列表
     *
     * @param nickname 用户昵称
     * @return 返回用户列表
     */
    List<WechatUser> getByNickname(@Param("nickname") String nickname);

    /**
     * 删除所有微信用户
     */
    void clean();
}