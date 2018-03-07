package cn.damei.service.sale.account;

import org.springframework.stereotype.Service;

import cn.damei.common.service.CrudService;
import cn.damei.entity.sale.account.UserBind;
import cn.damei.repository.sale.account.UserBindDao;
import com.rocoinfo.weixin.util.StringUtils;

@Service
public class UserBindService extends CrudService<UserBindDao, UserBind> {

    /**
     * 根据oid和平台查询用户绑定关系
     *
     * @param oid      oid {@link UserBind}
     * @return
     */
    public UserBind getByOidAndPlatform(String oid, UserBind.Platfrom platfrom) {
        if (StringUtils.isNotBlank(oid)) {
            return this.entityDao.getByOidAndPlatform(oid,platfrom);
        }
        return null;
    }
}
