package com.damei.repository.sale.report;

import com.damei.entity.sale.report.ReportAuthority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportAuthorityDao {

    ReportAuthority getByOid(String oid);

    void injectSql(@Param(value = "sql") String sql);

    void insert(ReportAuthority reportAuthority);
}
