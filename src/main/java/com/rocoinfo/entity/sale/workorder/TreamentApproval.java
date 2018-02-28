package com.rocoinfo.entity.sale.workorder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;
/**
 * <dl>
 * <dd>Description: 审批预计完成时间 申请记录 POJO</dd>
 * <dd>Company: mdni</dd>
 * <dd>@date：2017-8-24 15:38:44</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
public class TreamentApproval extends IdEntity {

    /**
     * 原先预计完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date oldTime;

    /**
     * 申请预计完成时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date newTime;

    /**
     * 工单id
     */

    private Long workorderId;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;

    /**
     * 修改原因
     */
    private String remarks;
    /**
     * 申请人
     */

    private String name;
    /**
     * 工单状态
     */
    private String workorderStatus;

    public String getWorkorderStatus() {
        return workorderStatus;
    }

    public void setWorkorderStatus(String workorderStatus) {
        this.workorderStatus = workorderStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getOldTime() {
        return oldTime;
    }

    public void setOldTime(Date oldTime) {
        this.oldTime = oldTime;
    }

    public Date getNewTime() {
        return newTime;
    }

    public void setNewTime(Date newTime) {
        this.newTime = newTime;
    }

    public Long getWorkorderId() {
        return workorderId;
    }

    public void setWorkorderId(Long workorderId) {
        this.workorderId = workorderId;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}