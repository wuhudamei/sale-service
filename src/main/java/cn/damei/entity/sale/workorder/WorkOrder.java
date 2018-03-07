package cn.damei.entity.sale.workorder;


import cn.damei.entity.IdEntity;
import cn.damei.entity.sale.dameiorder.DameiOrder;
import cn.damei.entity.sale.dict.DameiDictionary;
import cn.damei.enumeration.PushType;
import cn.damei.entity.sale.account.User;
import cn.damei.entity.sale.dameiorganization.DameiOrganization;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Transient;

import java.util.Date;

public class WorkOrder extends IdEntity {

	private static final long serialVersionUID = 1L;
	private String workOrderCode;
	private DameiOrder dameiOrder;
	private String customerId;
	private String customerName;
	private String customerMobile;
	private String customerAddress;
	private String contractStartTime;
	private String contractCompleteTime;
	private String actualStartTime;
	private String actualCompletionTime;
	private String styListName;
	private String styListMobile;
	private String contractorName;
	private String contractorMobile;
	private String supervisorName;
	private String supervisorMobile;
	private DameiDictionary importantDegree1;
	private DameiDictionary questionType1;
	private DameiDictionary questionType2;
	private User receptionPerson;
	private String receptionTime;
	private DameiOrganization srcDepartment;
	private DameiOrganization srcCompany;
	private DameiOrganization contractCompany;
	private Long brand;
	private String brandName;
	private String workType;
	private String problem;
	private User liablePerson;
	private DameiOrganization liableCompany;
	private DameiOrganization liableDepartment;
	private DameiDictionary liableType1;
	private DameiDictionary liableType2;
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
    private DameiDictionary source;
	private DameiDictionary complaintType;
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
	private User operationUserFromRmk;
	private Integer visitedTimes;
	private String treamentTimeUpdate;
	private DameiOrganization liableSupplier;
	private Boolean blackFlag;
	private Long pushNumber;
	private PushType pushType;
	private String suggestion;



	public String getTreamentTimeUpdate() {
		return treamentTimeUpdate;
	}

	public void setTreamentTimeUpdate(String treamentTimeUpdate) {
		this.treamentTimeUpdate = treamentTimeUpdate;
	}

	public Long getPushNumber() {
		return pushNumber;
	}

	public void setPushNumber(Long pushNumber) {
		this.pushNumber = pushNumber;
	}

	public PushType getPushType() {
		return pushType;
	}

	public void setPushType(PushType pushType) {
		this.pushType = pushType;
	}

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

	public User getReceptionPerson() {
		return receptionPerson;
	}
	public void setReceptionPerson(User receptionPerson) {
		this.receptionPerson = receptionPerson;
	}
	public String getReceptionTime() {
		return receptionTime;
	}
	public void setReceptionTime(String receptionTime) {
		this.receptionTime = receptionTime;
	}
	public DameiOrganization getSrcDepartment() {
		return srcDepartment;
	}
	public void setSrcDepartment(DameiOrganization srcDepartment) {
		this.srcDepartment = srcDepartment;
	}
	public DameiOrganization getSrcCompany() {
		return srcCompany;
	}
	public void setSrcCompany(DameiOrganization srcCompany) {
		this.srcCompany = srcCompany;
	}
	public String getProblem() {
		return problem;
	}
	public void setProblem(String problem) {
		this.problem = problem;
	}
	public DameiOrganization getLiableDepartment() {
		return liableDepartment;
	}
	public void setLiableDepartment(DameiOrganization liableDepartment) {
		this.liableDepartment = liableDepartment;
	}
	public User getLiablePerson() {
		return liablePerson;
	}
	public void setLiablePerson(User liablePerson) {
		this.liablePerson = liablePerson;
	}
	public DameiDictionary getLiableType1() {
		return liableType1;
	}
	public void setLiableType1(DameiDictionary liableType1) {
		this.liableType1 = liableType1;
	}
	public DameiDictionary getLiableType2() {
		return liableType2;
	}

	public Long getBrand() {
		return brand;
	}
	public void setBrand(Long brand) {
		this.brand = brand;
	}
	public void setLiableType2(DameiDictionary liableType2) {
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
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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
		return styListName==null?"":styListName;
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
		return contractorName==null?"":contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getContractorMobile() {
		return contractorMobile;
	}
	public void setContractorMobile(String contractorMobile) {
		this.contractorMobile = contractorMobile;
	}
	public String getSupervisorName() {
		return supervisorName==null?"":supervisorName;
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

	public String getStatusFlagName() {
		return statusFlagName;
	}

	public void setStatusFlagName(String statusFlagName) {
		this.statusFlagName = statusFlagName;
	}

	public String getCustomerFeedbackTime() {
		return customerFeedbackTime;
	}

	public void setCustomerFeedbackTime(String customerFeedbackTime) {
		this.customerFeedbackTime = customerFeedbackTime;
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

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public DameiOrganization getLiableCompany() {
		return liableCompany;
	}

	public void setLiableCompany(DameiOrganization liableCompany) {
		this.liableCompany = liableCompany;
	}

	public DameiDictionary getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(DameiDictionary complaintType) {

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

	public Boolean getRead() {
		return isRead;
	}

	public void setRead(Boolean read) {
		isRead = read;
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
	public DameiDictionary getSource() {
		return source;
	}
	public void setSource(DameiDictionary source) {
		this.source = source;
	}
	public Integer getVisitedTimes() {
		return visitedTimes;
	}
	public void setVisitedTimes(Integer visitedTimes) {
		this.visitedTimes = visitedTimes;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public User getOperationUserFromRmk() {
		return operationUserFromRmk;
	}
	public void setOperationUserFromRmk(User operationUserFromRmk) {
		this.operationUserFromRmk = operationUserFromRmk;
	}

	public Boolean getBlackFlag() {
		return blackFlag;
	}

	public void setBlackFlag(Boolean blackFlag) {
		this.blackFlag = blackFlag;
	}

	public DameiOrganization getLiableSupplier() {
		return liableSupplier;
	}

	public void setLiableSupplier(DameiOrganization liableSupplier) {
		this.liableSupplier = liableSupplier;
	}

	public String getActualStartTime() {
		return actualStartTime;
	}

	public void setActualStartTime(String actualStartTime) {
		this.actualStartTime = actualStartTime;
	}

	public String getActualCompletionTime() {
		return actualCompletionTime;
	}

	public void setActualCompletionTime(String actualCompletionTime) {
		this.actualCompletionTime = actualCompletionTime;
	}

	public String getSuggestion() {
		return suggestion;
	}

	public void setSuggestion(String suggestion) {
		this.suggestion = suggestion;
	}
}
