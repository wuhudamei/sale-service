package com.damei.service.sale.report;

import com.damei.service.sale.workorder.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
@Lazy(false)
public class ReportQuartz {
    @Autowired
    WorkOrderService workOrderService;
    @Scheduled(cron = "0 30 17 * * ?")
    public void insertReportQuartz() {
        //集团的
        workOrderService.findAllListForCall(false);
        //分公司的
        workOrderService.findAllListForCall(true);
    }

}
