package cn.damei.service.sale.report;

import cn.damei.common.service.CrudService;
import cn.damei.entity.sale.report.ReportNissin;
import cn.damei.repository.sale.report.ReportNissinDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ReportNissinService extends CrudService<ReportNissinDao,ReportNissin> {

    public List<ReportNissin> findReportNissinList(Map<String, Object> params){
        return entityDao.findReportNissinList(params);
    }

}
