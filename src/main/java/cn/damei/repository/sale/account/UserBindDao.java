package cn.damei.repository.sale.account;

import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sale.account.UserBind;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBindDao extends CrudDao<UserBind> {

    UserBind getByOidAndPlatform(@Param("oid") String oid, @Param("platform") UserBind.Platfrom platfrom);

}
