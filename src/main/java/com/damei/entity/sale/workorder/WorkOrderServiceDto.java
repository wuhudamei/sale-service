package com.damei.entity.sale.workorder;

import com.damei.entity.sale.dict.MdniDictionary;
import com.damei.entity.sale.organization.MdniOrganization;

public class WorkOrderServiceDto {
    private static final long serialVersionUID = 1L;
    private String liableCompanyName;
    private String workOrderCode;
    private String customerName;
    private String customerMobile;
    private String customerAddress;

    private String contractorName;
    private String contractorMobile;
    private String supervisorName;
    private String supervisorMobile;

    private String problem;
    private String createDate;
    private String photo;
    private String orderNo;



    private MdniOrganization liableDepartment;
    private MdniDictionary liableType1;
    private MdniDictionary questionType1;
    private MdniDictionary questionType2;
    private MdniDictionary importantDegree1;
    private MdniDictionary source;
    private MdniDictionary complaintType;


    public String getLiableCompanyName() {
        return liableCompanyName;
    }

    public void setLiableCompanyName(String liableCompanyName) {
        this.liableCompanyName = liableCompanyName;
    }

    public String getWorkOrderCode() {
        return workOrderCode;
    }

    public void setWorkOrderCode(String workOrderCode) {
        this.workOrderCode = workOrderCode;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getContractorName() {
        return contractorName;
    }

    public void setContractorName(String contractorName) {
        this.contractorName = contractorName;
    }

    public String getContractorMobile() {
        return contractorMobile;
    }

    public void setContractorMobile(String contractorMobile) {
        this.contractorMobile = contractorMobile;
    }

    public String getSupervisorName() {
        return supervisorName;
    }

    public void setSupervisorName(String supervisorName) {
        this.supervisorName = supervisorName;
    }

    public String getSupervisorMobile() {
        return supervisorMobile;
    }

    public void setSupervisorMobile(String supervisorMobile) {
        this.supervisorMobile = supervisorMobile;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public MdniOrganization getLiableDepartment() {
        return liableDepartment;
    }

    public void setLiableDepartment(MdniOrganization liableDepartment) {
        this.liableDepartment = liableDepartment;
    }

    public MdniDictionary getLiableType1() {
        return liableType1;
    }

    public void setLiableType1(MdniDictionary liableType1) {
        this.liableType1 = liableType1;
    }


    public MdniDictionary getImportantDegree1() {
        return importantDegree1;
    }

    public void setImportantDegree1(MdniDictionary importantDegree1) {
        this.importantDegree1 = importantDegree1;
    }

    public MdniDictionary getQuestionType1() {
        return questionType1;
    }

    public void setQuestionType1(MdniDictionary questionType1) {
        this.questionType1 = questionType1;
    }

    public MdniDictionary getQuestionType2() {
        return questionType2;
    }

    public void setQuestionType2(MdniDictionary questionType2) {
        this.questionType2 = questionType2;
    }

    public MdniDictionary getSource() {
        return source;
    }

    public void setSource(MdniDictionary source) {
        this.source = source;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

	public MdniDictionary getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(MdniDictionary complaintType) {
		this.complaintType = complaintType;
	}
    
}
