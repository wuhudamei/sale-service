package com.rocoinfo.entity.sale.workorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;
import com.rocoinfo.enumeration.PushType;

import java.util.Date;

/**
 * @author Ryze
 * @date 2017-9-11 15:00:30
 * @Description 推送工单失败实体
 */
public class WorkorderPushFail extends IdEntity {

    /**
     * 工单id
     */
    private Long workOrderId;

    /**
     * 工单编号
     */
    private String workOrderCode;

    /**
     * 推送失败原因
     */
    private String remarks;

    /**
     * 最新推送失败时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date pushDate;
    /**
     * 推送结果 0 失败 1 成功
     */
    private String pushResult;

    /**
     * 失败 的同步时间 
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date synDate;

    /**
     * 推送次数
     */
    private Long pushNumber;

    /**
     * 失败类型
     */
    private PushType pushType;

    public PushType getPushType() {
        return pushType;
    }

    public void setPushType(PushType pushType) {
        this.pushType = pushType;
    }

    private static final long serialVersionUID = 1L;

    public Long getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(Long workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getWorkOrderCode() {
        return workOrderCode;
    }

    public void setWorkOrderCode(String workOrderCode) {
        this.workOrderCode = workOrderCode;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getPushDate() {
        return pushDate;
    }

    public void setPushDate(Date pushDate) {
        this.pushDate = pushDate;
    }

    public String getPushResult() {
        return pushResult;
    }

    public void setPushResult(String pushResult) {
        this.pushResult = pushResult;
    }

    public Date getSynDate() {
        return synDate;
    }

    public void setSynDate(Date synDate) {
        this.synDate = synDate;
    }

    public Long getPushNumber() {
        return pushNumber;
    }

    public void setPushNumber(Long pushNumber) {
        this.pushNumber = pushNumber;
    }
}