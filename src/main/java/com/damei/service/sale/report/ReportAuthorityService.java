package com.damei.service.sale.report;

import com.damei.entity.sale.report.ReportAuthority;
import com.damei.repository.sale.report.ReportAuthorityDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
