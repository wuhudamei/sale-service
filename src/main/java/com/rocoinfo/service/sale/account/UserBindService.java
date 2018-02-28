package com.rocoinfo.service.sale.account;

import org.springframework.stereotype.Service;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.sale.account.UserBind;
import com.rocoinfo.repository.sale.account.UserBindDao;
import com.rocoinfo.weixin.util.StringUtils;

/**
 * <dl>
 * <dd>Description: 本地用户与其他平台绑定Service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/20 下午1:03</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
@Service
public class UserBindService extends CrudService<UserBindDao, UserBind> {

    /**
     * 根据oid和平台查询用户绑定关系
     *
     * @param oid      oid {@link UserBind}
     * @param platfrom 平台 {@link com.rocoinfo.entity.account.UserBind.Platfrom}
     * @return
     */
    public UserBind getByOidAndPlatform(String oid, UserBind.Platfrom platfrom) {
        if (StringUtils.isNotBlank(oid)) {
            return this.entityDao.getByOidAndPlatform(oid,platfrom);
        }
        return null;
    }
}
