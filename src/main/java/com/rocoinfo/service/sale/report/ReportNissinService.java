package com.rocoinfo.service.sale.report;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.sale.report.ReportNissin;
import com.rocoinfo.repository.sale.report.ReportNissinDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dd>Description: 美得你智装 日清报表Service</dd>
 * <dd>@date：2017/10/12  11:39</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Service
public class ReportNissinService extends CrudService<ReportNissinDao,ReportNissin> {

    /**
     * @author Ryze
     * @date 2017/10/13 10:40
     * @description 提供接口查询 报表
     * @param
     * @return
     */
    public List<ReportNissin> findReportNissinList(Map<String, Object> params){
        return entityDao.findReportNissinList(params);
    }

}
