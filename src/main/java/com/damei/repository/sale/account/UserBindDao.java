package com.damei.repository.sale.account;

import com.damei.entity.sale.account.UserBind;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.damei.common.persistence.CrudDao;

@Repository
public interface UserBindDao extends CrudDao<UserBind> {

    UserBind getByOidAndPlatform(@Param("oid") String oid, @Param("platform") UserBind.Platfrom platfrom);

}
