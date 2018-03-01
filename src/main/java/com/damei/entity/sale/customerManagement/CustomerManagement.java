package com.damei.entity.sale.customerManagement;


import com.damei.entity.IdEntity;
import com.damei.entity.sale.dict.MdniDictionary;
import com.damei.entity.sale.organization.MdniOrganization;

public class CustomerManagement extends IdEntity {


	private String workOrderCode;
	private MdniDictionary questionType1;
	private MdniDictionary questionType2;
	private MdniOrganization liableDepartment;
	private String orderStatus;
	private String createDate;
	private String operationDate;
    private MdniDictionary source;
	private MdniDictionary complaintType;
	private Integer workCount;
	private Integer completCount;
	private String treamentTime;
	private String receptionTime;

	public Integer getWorkCount() {
		return workCount;
	}

	public void setWorkCount(Integer workCount) {
		this.workCount = workCount;
	}

	public Integer getCompletCount() {
		return completCount;
	}

	public void setCompletCount(Integer completCount) {
		this.completCount = completCount;
	}

	public String getWorkOrderCode() {
		return workOrderCode;
	}

	public void setWorkOrderCode(String workOrderCode) {
		this.workOrderCode = workOrderCode;
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

	public MdniOrganization getLiableDepartment() {
		return liableDepartment;
	}

	public void setLiableDepartment(MdniOrganization liableDepartment) {
		this.liableDepartment = liableDepartment;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getOperationDate() {
		return operationDate;
	}

	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}

	public MdniDictionary getSource() {
		return source;
	}

	public void setSource(MdniDictionary source) {
		this.source = source;
	}

	public MdniDictionary getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(MdniDictionary complaintType) {
		this.complaintType = complaintType;
	}

	public String getTreamentTime() {
		return treamentTime;
	}

	public void setTreamentTime(String treamentTime) {
		this.treamentTime = treamentTime;
	}

	public String getReceptionTime() {
		return receptionTime;
	}

	public void setReceptionTime(String receptionTime) {
		this.receptionTime = receptionTime;
	}
}
