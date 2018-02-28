package com.rocoinfo.entity.sale.workorder;


import java.util.Date;

import org.springframework.data.annotation.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;
import com.rocoinfo.entity.sale.mdniorder.MdniOrder;

/**
 * <dl> 用于工单回访中导出
 * <dd>Description: 导出工单POJO</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-08-04 </dd>
 * <dd>@author：Paul</dd>
 * </dl>
 */
public class WorkOrderForExport extends IdEntity {
	
	private static final long serialVersionUID = 1L;
	/** 工单编号 **/
	private String workOrderCode;
	/** 合同信息 **/
	private MdniOrder mdniOrder;
	/** 客户ID **/
	private String customerId;
	/** 客户姓名 **/
	private String customerName;
	/** 客户电话 **/
	private String customerMobile;
	/** 客户地址 **/
	private String customerAddress;
	/** 合同开始时间**/
	private String contractStartTime;
	/** 合同结束时间**/
	private String contractCompleteTime;
	/** 设计师名称**/
	private String styListName;
	/** 设计师电话**/
	private String styListMobile;
	/** 项目经理名称**/
	private String contractorName;
	/** 项目经理电话**/
	private String contractorMobile;
	/** 监理名称**/
	private String supervisorName;
	/** 监理电话**/
	private String supervisorMobile;
	/** 重要程度1 **/
	private String importantDegree1;
	/**事项分类**/
	private String questionType1;
	/**问题类型**/
	private String questionType2;
	/** 接待人 **/
	private String receptionPerson;
	/** 接待时间 **/
	private String receptionTime;
	/** 来源部门 **/
	private String srcDepartment;
	/** 来源公司 **/
	private String srcCompany;
	/**分公司**/
	private String contractCompany;
	/**品牌**/
	private Long brand;
	//品牌名称
	private String brandName;
	/**工单分类**/
	private String workType;
	/** 问题 **/
	private String problem;
	/** 责任人 **/
	private String liablePerson;
	/**责任门店**/
	private String liableCompany;
	/** 责任部门 **/
	private String liableDepartment;
	/** 责任类别1 **/
	private String liableType1;
	/** 责任类别1 **/
	private String liableType2;
	/** 处理方案 **/
	private String treamentPlan;
	/** 预计完成时间 **/
	private String treamentTime;
	/** 反馈时间 **/
	private String feedbackTime;
	/** 客户要求反馈时间 **/
	private String customerFeedbackTime;
	/** 回访结果 **/
	private String visitResult;
	/** 状态 0-无效,1-正常 **/
	private Integer statuFlag;
	@Transient
	private String statusFlagName;
	/** 工单状态 **/
	private String orderStatus;
	/** 是否复制 1-复制**/
	private String copyFlag;
	/** 要复制的工单ID **/
	private Long copyWorkId;
	/** 创建人 **/
	private Long createUser;
	/**创建人姓名**/
	private String createUserName;
	/** 创建时间 **/
	private String createDate;
	/**工单图片**/
	private String photo;
	/**
	 * 反馈人
	 */
	private String rejectName;
	/**反馈时间**/
	private String operationDate;
	/**意见**/
	private String remark;
    /**工单来源**/
    private String source;
	//投诉原因
	private String complaintType;
	//被拒绝时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date refusedagainTime;
	//拒绝时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date refuseTime;
	//催单次数
	private Integer urgeTimes;
	//催单是否已读
	private Boolean isRead;
	//分派时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date fenpaiDate;
	//工单操作时间--从轨迹表中 按操作类型获取
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date operationDateFromRmk;
	//处理人 -- 来自轨迹表中 操作人
	private String operationUserFromRmk;
	//回访次数 -- 统计轨迹表
	private Integer visitedTimes;
	//回访时,不满意原因--来自轨迹表且是操作类型是visit
	private String unSatisfiedFromRmk;
	//申请预计完成时间延期成功 次数
	private String treamentResultCount;

	
	
	
	
	
	public String getWorkOrderCode() {
		return workOrderCode;
	}
	public void setWorkOrderCode(String workOrderCode) {
		this.workOrderCode = workOrderCode;
	}
	public MdniOrder getMdniOrder() {
		return mdniOrder;
	}
	public void setMdniOrder(MdniOrder mdniOrder) {
		this.mdniOrder = mdniOrder;
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
