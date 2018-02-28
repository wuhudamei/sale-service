package com.rocoinfo.service.sale.report;

import com.rocoinfo.service.sale.workorder.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * <dl>
 * <dd>Description: 美得你智装 日清报表定时任务</dd>
 * <dd>@date：2017/10/13  9:55</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Service
@EnableScheduling
@Lazy(false)
public class ReportQuartz {
    @Autowired
    WorkOrderService workOrderService;
    /**
     * @author Ryze
     * @date 2017/10/13 9:58
     * @description 日清报表 每天 17点30 跑批
     * @param
     * @return
     */
    @Scheduled(cron = "0 30 17 * * ?")
    public void insertReportQuartz() {
        //集团的
        workOrderService.findAllListForCall(false);
        //分公司的
        workOrderService.findAllListForCall(true);
    }

}
