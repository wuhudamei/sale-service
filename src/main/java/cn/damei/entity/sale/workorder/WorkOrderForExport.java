package cn.damei.entity.sale.workorder;


import java.util.Date;

import cn.damei.entity.IdEntity;
import cn.damei.entity.sale.dameiorder.DameiOrder;
import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;

public class WorkOrderForExport extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	private String workOrderCode;
	private DameiOrder dameiOrder;
	private String customerId;
	private String customerName;
	private String customerMobile;
	private String customerAddress;
	private String contractStartTime;
	private String contractCompleteTime;
	private String styListName;
	private String styListMobile;
	private String contractorName;
	private String contractorMobile;
	private String supervisorName;
	private String supervisorMobile;
	private String importantDegree1;
	private String questionType1;
	private String questionType2;
	private String receptionPerson;
	private String receptionTime;
	private String srcDepartment;
	private String srcCompany;
	private String contractCompany;
	private Long brand;
	private String brandName;
	private String workType;
	private String problem;
	private String liablePerson;
	private String liableCompany;
	private String liableDepartment;
	private String liableType1;
	private String liableType2;
	private String treamentPlan;
	private String treamentTime;
	private String feedbackTime;
	private String customerFeedbackTime;
	private String visitResult;
	private Integer statuFlag;
	@Transient
	private String statusFlagName;
	private String orderStatus;
	private String copyFlag;
	private Long copyWorkId;
	private Long createUser;
	private String createUserName;
	private String createDate;
	private String photo;
	private String rejectName;
	private String operationDate;
	private String remark;
    private String source;
	private String complaintType;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date refusedagainTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date refuseTime;
	private Integer urgeTimes;
	private Boolean isRead;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date fenpaiDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date operationDateFromRmk;
	private String operationUserFromRmk;
	private Integer visitedTimes;
	private String unSatisfiedFromRmk;
	private String treamentResultCount;

	
	
	
	
	
	public String getWorkOrderCode() {
		return workOrderCode;
	}
	public void setWorkOrderCode(String workOrderCode) {
		this.workOrderCode = workOrderCode;
	}
	public DameiOrder getDameiOrder() {
		return dameiOrder;
	}
	public void setDameiOrder(DameiOrder dameiOrder) {
		this.dameiOrder = dameiOrder;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
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
	public String getContractStartTime() {
		return contractStartTime;
	}
	public void setContractStartTime(String contractStartTime) {
		this.contractStartTime = contractStartTime;
	}
	public String getContractCompleteTime() {
		return contractCompleteTime;
	}
	public void setContractCompleteTime(String contractCompleteTime) {
		this.contractCompleteTime = contractCompleteTime;
	}
	public String getStyListName() {
		return styListName;
	}
	public void setStyListName(String styListName) {
		this.styListName = styListName;
	}
	public String getStyListMobile() {
		return styListMobile;
	}
	public void setStyListMobile(String styListMobile) {
		this.styListMobile = styListMobile;
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
	public String getImportantDegree1() {
		return importantDegree1;
	}
	public void setImportantDegree1(String importantDegree1) {
		this.importantDegree1 = importantDegree1;
	}
	public String getQuestionType1() {
		return questionType1;
	}
	public void setQuestionType1(String questionType1) {
		this.questionType1 = questionType1;
	}
	public String getQuestionType2() {
		return questionType2;
	}
	public void setQuestionType2(String questionType2) {
		this.questionType2 = questionType2;
	}
	public String getReceptionPerson() {
		return receptionPerson;
	}
	public void setReceptionPerson(String receptionPerson) {
		this.receptionPerson = receptionPerson;
	}
	public String getReceptionTime() {
		return receptionTime;
	}
	public void setReceptionTime(String receptionTime) {
		this.receptionTime = receptionTime;
	}
	public String getSrcDepartment() {
		return srcDepartment;
	}
	public void setSrcDepartment(String srcDepartment) {
		this.srcDepartment = srcDepartment;
	}
	public String getSrcCompany() {
		return srcCompany;
	}
	public void setSrcCompany(String srcCompany) {
		this.srcCompany = srcCompany;
	}
	public String getContractCompany() {
		return contractCompany;
	}
	public void setContractCompany(String contractCompany) {
		this.contractCompany = contractCompany;
	}
	public Long getBrand() {
		return brand;
	}
	public void setBrand(Long brand) {
		this.brand = brand;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public String getLiablePerson() {
		return liablePerson;
	}
	public void setLiablePerson(String liablePerson) {
		this.liablePerson = liablePerson;
	}
	public String getLiableCompany() {
		return liableCompany;
	}
	public void setLiableCompany(String liableCompany) {
		this.liableCompany = liableCompany;
	}
	public String getLiableDepartment() {
		return liableDepartment;
	}
	public void setLiableDepartment(String liableDepartment) {
		this.liableDepartment = liableDepartment;
	}
	public String getLiableType1() {
		return liableType1;
	}
	public void setLiableType1(String liableType1) {
		this.liableType1 = liableType1;
	}
	public String getLiableType2() {
		return liableType2;
	}
	public void setLiableType2(String liableType2) {
		this.liableType2 = liableType2;
	}
	public String getTreamentPlan() {
		return treamentPlan;
	}
	public void setTreamentPlan(String treamentPlan) {
		this.treamentPlan = treamentPlan;
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
	public String getVisitResult() {
		return visitResult;
	}
	public void setVisitResult(String visitResult) {
		this.visitResult = visitResult;
	}
	public Integer getStatuFlag() {
		return statuFlag;
	}
	public void setStatuFlag(Integer statuFlag) {
		this.statuFlag = statuFlag;
	}
	public String getStatusFlagName() {
		return statusFlagName;
	}
	public void setStatusFlagName(String statusFlagName) {
		this.statusFlagName = statusFlagName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCopyFlag() {
		return copyFlag;
	}
	public void setCopyFlag(String copyFlag) {
		this.copyFlag = copyFlag;
	}
	public Long getCopyWorkId() {
		return copyWorkId;
	}
	public void setCopyWorkId(Long copyWorkId) {
		this.copyWorkId = copyWorkId;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
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
	public String getRejectName() {
		return rejectName;
	}
	public void setRejectName(String rejectName) {
		this.rejectName = rejectName;
	}
	public String getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(String complaintType) {
		this.complaintType = complaintType;
	}
	public Date getRefusedagainTime() {
		return refusedagainTime;
	}
	public void setRefusedagainTime(Date refusedagainTime) {
		this.refusedagainTime = refusedagainTime;
	}
	public Date getRefuseTime() {
		return refuseTime;
	}
	public void setRefuseTime(Date refuseTime) {
		this.refuseTime = refuseTime;
	}
	public Integer getUrgeTimes() {
		return urgeTimes;
	}
	public void setUrgeTimes(Integer urgeTimes) {
		this.urgeTimes = urgeTimes;
	}
	public Boolean getIsRead() {
		return isRead;
	}
	public void setIsRead(Boolean isRead) {
		this.isRead = isRead;
	}
	public Date getFenpaiDate() {
		return fenpaiDate;
	}
	public void setFenpaiDate(Date fenpaiDate) {
		this.fenpaiDate = fenpaiDate;
	}
	public Date getOperationDateFromRmk() {
		return operationDateFromRmk;
	}
	public void setOperationDateFromRmk(Date operationDateFromRmk) {
		this.operationDateFromRmk = operationDateFromRmk;
	}
	public String getOperationUserFromRmk() {
		return operationUserFromRmk;
	}
	public void setOperationUserFromRmk(String operationUserFromRmk) {
		this.operationUserFromRmk = operationUserFromRmk;
	}
	public Integer getVisitedTimes() {
		return visitedTimes;
	}
	public void setVisitedTimes(Integer visitedTimes) {
		this.visitedTimes = visitedTimes;
	}
	public String getUnSatisfiedFromRmk() {
		return unSatisfiedFromRmk;
	}
	public void setUnSatisfiedFromRmk(String unSatisfiedFromRmk) {
		this.unSatisfiedFromRmk = unSatisfiedFromRmk;
	}
	public String getTreamentResultCount() {
		return treamentResultCount;
	}
	public void setTreamentResultCount(String treamentResultCount) {
		this.treamentResultCount = treamentResultCount;
	}
}
