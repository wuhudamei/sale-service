package com.rocoinfo.dto;

import com.rocoinfo.entity.IdEntity;

import java.util.List;

/**
 * <dl>
 * <dd>Description: 统计：工单dto </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/30</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */

public class WorkOrderReturnDto extends IdEntity{

    /**
     * 工单状态
     */
    private String orderStatus;
    /**
     * 所属分公司 id
     */
    private  String liableCompany;

    /**
     * 所属分公司 名字
     */
    private  String liableCompanyName;

    /**
     * 所属部门
     */
    private String liableDepartment;
    /**
     * 不满意是否需要派单
     */
    private String copyFlag;
    /**
     * 部门名字
     */
    private String orgName;
    /**
     *预计完成时间
     */
    private String treamentTime;
    /**
     *  反馈时间
     */
    private String feedbackTime;
    /**
     * 客户要求反馈时间
     */
    private String customerFeedbackTime;
    /**
     * 创建时间
     */
    private String createTime;



    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getLiableDepartment() {
        return liableDepartment;
    }

    public void setLiableDepartment(String liableDepartment) {
        this.liableDepartment = liableDepartment;
    }

    public String getCopyFlag() {
        return copyFlag;
    }

    public void setCopyFlag(String copyFlag) {
        this.copyFlag = copyFlag;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getTreamentTime() {
        return treamentTime;
    }

    public void setTreamentTime(String treamentTime) {
        this.treamentTime = treamentTime;
    }

    public String getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public String getCustomerFeedbackTime() {
        return customerFeedbackTime;
    }

    public void setCustomerFeedbackTime(String customerFeedbackTime) {
        this.customerFeedbackTime = customerFeedbackTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLiableCompany() {
        return liableCompany;
    }

    public void setLiableCompany(String liableCompany) {
        this.liableCompany = liableCompany;
    }

    public String getLiableCompanyName() {
        return liableCompanyName;
    }

    public void setLiableCompanyName(String liableCompanyName) {
        this.liableCompanyName = liableCompanyName;
    }
}
