package com.rocoinfo.repository.sale.report;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.report.ReportNissin;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dd>Description: 美得你智装 日清报表接口 dao</dd>
 * <dd>@date：2017/10/12  11:38</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Repository
public interface ReportNissinDao extends CrudDao<ReportNissin> {
    /**
    * @author Ryze
    * @date 2017/10/12 14:28
    * @description 批量添加
    * @param   list
    * @return
    */
  void  insertBatch(List<ReportNissin> list);
  /**
   * @author Ryze
   * @date 2017/10/13 10:40
   * @description 提供接口查询 报表
   * @param
   * @return
   */ 
  List<ReportNissin>  findReportNissinList(Map<String, Object> params);



}
