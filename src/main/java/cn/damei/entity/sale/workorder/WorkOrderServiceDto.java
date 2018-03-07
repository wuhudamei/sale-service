package cn.damei.entity.sale.workorder;

import cn.damei.entity.sale.dameiorganization.DameiOrganization;
import cn.damei.entity.sale.dict.DameiDictionary;

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



    private DameiOrganization liableDepartment;
    private DameiDictionary liableType1;
    private DameiDictionary questionType1;
    private DameiDictionary questionType2;
    private DameiDictionary importantDegree1;
    private DameiDictionary source;
    private DameiDictionary complaintType;


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

    public DameiOrganization getLiableDepartment() {
        return liableDepartment;
    }

    public void setLiableDepartment(DameiOrganization liableDepartment) {
        this.liableDepartment = liableDepartment;
    }

    public DameiDictionary getLiableType1() {
        return liableType1;
    }

    public void setLiableType1(DameiDictionary liableType1) {
        this.liableType1 = liableType1;
    }


    public DameiDictionary getImportantDegree1() {
        return importantDegree1;
    }

    public void setImportantDegree1(DameiDictionary importantDegree1) {
        this.importantDegree1 = importantDegree1;
    }

    public DameiDictionary getQuestionType1() {
        return questionType1;
    }

    public void setQuestionType1(DameiDictionary questionType1) {
        this.questionType1 = questionType1;
    }

    public DameiDictionary getQuestionType2() {
        return questionType2;
    }

    public void setQuestionType2(DameiDictionary questionType2) {
        this.questionType2 = questionType2;
    }

    public DameiDictionary getSource() {
        return source;
    }

    public void setSource(DameiDictionary source) {
        this.source = source;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

	public DameiDictionary getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(DameiDictionary complaintType) {
		this.complaintType = complaintType;
	}
    
}
