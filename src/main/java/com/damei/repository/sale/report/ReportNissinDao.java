package com.damei.repository.sale.report;

import com.damei.entity.sale.report.ReportNissin;
import com.damei.common.persistence.CrudDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReportNissinDao extends CrudDao<ReportNissin> {
  void  insertBatch(List<ReportNissin> list);
  List<ReportNissin>  findReportNissinList(Map<String, Object> params);



}
