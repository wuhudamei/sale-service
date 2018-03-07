package cn.damei.dto;

import cn.damei.entity.IdEntity;

public class WorkOrderReturnDto extends IdEntity {

    private String orderStatus;
    private  String liableCompany;

    private  String liableCompanyName;

    private String liableDepartment;
    private String copyFlag;
    private String orgName;
    private String treamentTime;
    private String feedbackTime;
    private String customerFeedbackTime;
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
