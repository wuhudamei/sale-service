package cn.damei.repository.sale.report;

import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sale.report.ReportNissin;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ReportNissinDao extends CrudDao<ReportNissin> {
  void  insertBatch(List<ReportNissin> list);
  List<ReportNissin>  findReportNissinList(Map<String, Object> params);



}
