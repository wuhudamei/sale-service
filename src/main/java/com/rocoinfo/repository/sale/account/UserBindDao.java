package com.rocoinfo.repository.sale.account;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.account.UserBind;

/**
 * <dl>
 * <dd>Description: 本地用户与其他平台绑定Dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/20 下午1:04</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
@Repository
public interface UserBindDao extends CrudDao<UserBind> {

    /**
     * 根据oid和平台查询用户绑定关系
     *
     * @param oid      oid {@link UserBind}
     * @param platfrom 平台 {@link com.rocoinfo.entity.account.UserBind.Platfrom}
     * @return
     */
    UserBind getByOidAndPlatform(@Param("oid") String oid, @Param("platform") UserBind.Platfrom platfrom);

}
