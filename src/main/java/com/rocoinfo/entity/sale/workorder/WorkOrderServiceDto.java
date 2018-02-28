package com.rocoinfo.entity.sale.workorder;

import com.rocoinfo.entity.sale.account.User;
import com.rocoinfo.entity.sale.dict.MdniDictionary;
import com.rocoinfo.entity.sale.mdniorder.MdniOrder;
import com.rocoinfo.entity.sale.organization.MdniOrganization;
import org.springframework.data.annotation.Transient;

/**
 * Created by Asher on 17/6/22.
 */
public class WorkOrderServiceDto {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /** 门店名称 **/
    private String liableCompanyName;
    /** 工单编号 **/
    private String workOrderCode;
    /** 客户姓名 **/
    private String customerName;
    /** 客户电话 **/
    private String customerMobile;
    /** 客户地址 **/
    private String customerAddress;

    /** 项目经理名称**/
    private String contractorName;
    /** 项目经理电话**/
    private String contractorMobile;

    /** 监理名称**/
    private String supervisorName;
    /** 监理电话**/
    private String supervisorMobile;

    /** 问题 **/
    private String problem;
    /** 创建时间 **/
    private String createDate;
    /**工单图片**/
    private String photo;
    /**项目编号**/
    private String orderNo;



    /** 责任部门 **/
    private MdniOrganization liableDepartment;
    /** 投诉原因 **/
    private MdniDictionary liableType1;
    /** 问题类型 **/
    private MdniDictionary questionType1;
    /** 事项分类 **/
    private MdniDictionary questionType2;
    /** 重要程度 **/
    private MdniDictionary importantDegree1;
    /** 来源部门 **/
    private MdniDictionary source;
    /** 重要程度 **/
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
