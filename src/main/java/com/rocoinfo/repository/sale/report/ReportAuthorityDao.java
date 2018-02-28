package com.rocoinfo.repository.sale.report;

import com.rocoinfo.entity.sale.report.ReportAuthority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <dl>
 * <dd>Description: 功能描述</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/31 10:56</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
@Repository
public interface ReportAuthorityDao {

    /**
     * 根据用户oid查找用户
     *
     * @param oid 用户oid
     * @return 返回用户权限实体
     */
    ReportAuthority getByOid(String oid);

    /**
     * sql注入
     *
     * @param sql 需要注入的sql
     */
    void injectSql(@Param(value = "sql") String sql);

    /**
     * 插入用户数据
     * @param reportAuthority 用户权限信息
     */
    void insert(ReportAuthority reportAuthority);
}
