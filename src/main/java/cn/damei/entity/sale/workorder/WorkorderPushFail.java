package cn.damei.entity.sale.workorder;

import cn.damei.entity.IdEntity;
import cn.damei.enumeration.PushType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class WorkorderPushFail extends IdEntity {

    private Long workOrderId;

    private String workOrderCode;

    private String remarks;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date pushDate;
    private String pushResult;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date synDate;

    private Long pushNumber;

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