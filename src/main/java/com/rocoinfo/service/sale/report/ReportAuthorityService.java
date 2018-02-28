package com.rocoinfo.service.sale.report;

import com.rocoinfo.entity.sale.report.ReportAuthority;
import com.rocoinfo.repository.sale.report.ReportAuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <dl>
 * <dd>Description: 报表权限控制service</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/31 10:50</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
@Service
public class ReportAuthorityService {
    @Autowired
    private ReportAuthorityDao reportAuthorityDao;

    /**
     * 根据openId查找对应的用户权限信息
     *
     * @param openId 用户openid
     * @return 返回权限信息
     */
    public ReportAuthority getByOpenId(String openId) {

        return reportAuthorityDao.getByOid(openId);
    }

    /**
     * sql注入
     *
     * @param sql
     */
    public void injectSql(String sql) {

        reportAuthorityDao.injectSql(sql);
    }

    /**
     * 新建用户查看报表数据
     * @param reportAuthority 用户信息
     */
    public void insert(ReportAuthority reportAuthority) {

        reportAuthorityDao.insert(reportAuthority);

    }
}
