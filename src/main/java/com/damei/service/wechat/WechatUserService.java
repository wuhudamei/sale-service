package com.damei.service.wechat;

import com.damei.common.service.CrudService;
import com.damei.entity.wechat.WechatUser;
import org.springframework.stereotype.Service;
import com.damei.repository.wechat.WechatUserDao;

import java.util.List;

@Service
public class WechatUserService extends CrudService<WechatUserDao, WechatUser> {


    public void insert(WechatUser user) {

        super.insert(user);
    }

    /**
     * 批量插入微信用户信息
     *
     * @param users 微信用户列表
     */
    public void batchInsert(List<WechatUser> users) {
        if (users != null && users.size() > 0) {

            this.entityDao.batchInsert(users);
        }
    }

    /**
     * 根据用户微信昵称查询用户列表
     *
     * @param nickname 用户昵称
     * @return 返回用户列表
     */
    public  List<WechatUser> getByNickname(String nickname) {

        return this.entityDao.getByNickname(nickname);
    }

    /**
     * 删除所有微信用户
     */
    public void clean() {
        this.entityDao.clean();
    }
}