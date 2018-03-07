package cn.damei.entity.sale.customerManagement;


import cn.damei.entity.sale.dict.DameiDictionary;
import cn.damei.entity.IdEntity;
import cn.damei.entity.sale.dameiorganization.DameiOrganization;

public class CustomerManagement extends IdEntity {


	private String workOrderCode;
	private DameiDictionary questionType1;
	private DameiDictionary questionType2;
	private DameiOrganization liableDepartment;
	private String orderStatus;
	private String createDate;
	private String operationDate;
    private DameiDictionary source;
	private DameiDictionary complaintType;
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

	public DameiOrganization getLiableDepartment() {
		return liableDepartment;
	}

	public void setLiableDepartment(DameiOrganization liableDepartment) {
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

	public DameiDictionary getSource() {
		return source;
	}

	public void setSource(DameiDictionary source) {
		this.source = source;
	}

	public DameiDictionary getComplaintType() {
		return complaintType;
	}

	public void setComplaintType(DameiDictionary complaintType) {
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
