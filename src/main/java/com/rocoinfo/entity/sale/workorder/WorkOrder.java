package com.rocoinfo.entity.sale.workorder;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;
import com.rocoinfo.entity.sale.account.User;
import com.rocoinfo.entity.sale.dict.MdniDictionary;
import com.rocoinfo.entity.sale.mdniorder.MdniOrder;
import com.rocoinfo.entity.sale.organization.MdniOrganization;
import com.rocoinfo.enumeration.PushType;
import org.springframework.data.annotation.Transient;

import java.util.Date;

/**
 * <dl>
 * <dd>Description: 工单POJO</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-03-08 13:23:18</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
public class WorkOrder extends IdEntity {

	/**
	 * 
	 */
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
	/** 合同预计开始时间**/
	private String contractStartTime;
	/** 合同预计结束时间**/
	private String contractCompleteTime;
	/** 实际开始时间 -- 来自合同**/
	private String actualStartTime;
	/** 实际结束时间-- 来自合同**/
	private String actualCompletionTime;
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
	private MdniDictionary importantDegree1;
	/**事项分类**/
	private MdniDictionary questionType1;
	/**问题类型**/
	private MdniDictionary questionType2;
	/** 接待人 **/
	private User receptionPerson;
	/** 接待时间 **/
	private String receptionTime;
	/** 来源部门 **/
	private MdniOrganization srcDepartment;
	/** 来源公司 **/
	private MdniOrganization srcCompany;
	/**分公司**/
	private MdniOrganization contractCompany;
	/**品牌**/
	private Long brand;
	/**品牌名称**/
	private String brandName;
	/**工单分类**/
	private String workType;
	/** 问题 **/
	private String problem;
	/** 责任人 **/
	private User liablePerson;
	/**责任门店**/
	private MdniOrganization liableCompany;
	/** 责任部门 **/
	private MdniOrganization liableDepartment;
	/** 责任类别1 **/
	private MdniDictionary liableType1;
	/** 责任类别1 **/
	private MdniDictionary liableType2;
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
	/** 反馈人*/
	private String rejectName;
	/**反馈时间**/
	private String operationDate;
	/**意见**/
	private String remark;
    /**工单来源**/
    private MdniDictionary source;
	/**投诉原因**/
	private MdniDictionary complaintType;
	/**被拒绝时间**/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date refusedagainTime;
	/**拒绝时间**/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date refuseTime;
	/**催单次数**/
	private Integer urgeTimes;
	/**催单是否已读**/
	private Boolean isRead;
	/**分派时间**/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date fenpaiDate;
	/**工单操作时间--从轨迹表中 按操作类型获取**/
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date operationDateFromRmk;
	/**处理人 -- 来自轨迹表中 操作人**/
	private User operationUserFromRmk;
	/**回访次数 -- 统计轨迹表**/
	private Integer visitedTimes;
	/**预计完成时间修改  0 提交申请  1 申请通过 2 申请驳回**/
	private String treamentTimeUpdate;
	/**责任供应商**/
	private MdniOrganization liableSupplier;
	/**黑名单标记 -- 来自客户库*/
	private Boolean blackFlag;
	/** 工单同步失败的次数 */
	private Long pushNumber;
	/**  工单同步失败的类型 */
	private PushType pushType;
	/** 待分配中 分配意见 */
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

	/**
	 * 获取	工单编号
	 * @return
	 */
	public String getWorkOrderCode() {
		return workOrderCode;
	}
	/**
	 * 设置	工单编号
	 * @param workOrderCode
	 */
	public void setWorkOrderCode(String workOrderCode) {
		this.workOrderCode = workOrderCode;
	}
	/**
	 * 获取	合同信息
	 * @return
	 */
	public MdniOrder getMdniOrder() {
		return mdniOrder;
	}
	/**
	 * 设置	合同信息
	 * @param mdniOrder
	 */
	public void setMdniOrder(MdniOrder mdniOrder) {
		this.mdniOrder = mdniOrder;
	}
	/**
	 * 获取	重要程度1
	 * @return
	 */
	public MdniDictionary getImportantDegree1() {
		return importantDegree1;
	}
	/**
	 * 设置	重要程度1
	 * @param importantDegree1
	 */
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

	/**
	 * 获取	接待人
	 * @return
	 */
	public User getReceptionPerson() {
		return receptionPerson;
	}
	/**
	 * 设置	接待人
	 * @param receptionPerson
	 */
	public void setReceptionPerson(User receptionPerson) {
		this.receptionPerson = receptionPerson;
	}
	/**
	 * 获取	接待时间
	 * @return
	 */
	public String getReceptionTime() {
		return receptionTime;
	}
	/**
	 * 设置	接待时间
	 * @param receptionTime
	 */
	public void setReceptionTime(String receptionTime) {
		this.receptionTime = receptionTime;
	}
	/**
	 * 获取	来源部门
	 * @return
	 */
	public MdniOrganization getSrcDepartment() {
		return srcDepartment;
	}
	/**
	 * 设置	来源部门
	 * @param srcDepartment
	 */
	public void setSrcDepartment(MdniOrganization srcDepartment) {
		this.srcDepartment = srcDepartment;
	}
	/**
	 * 获取	来源公司
	 * @return
	 */
	public MdniOrganization getSrcCompany() {
		return srcCompany;
	}
	/**
	 * 设置	来源公司
	 * @param srcCompany
	 */
	public void setSrcCompany(MdniOrganization srcCompany) {
		this.srcCompany = srcCompany;
	}
	/**
	 * 获取	问题
	 * @return
	 */
	public String getProblem() {
		return problem;
	}
	/**
	 * 设置	问题
	 * @param problem
	 */
	public void setProblem(String problem) {
		this.problem = problem;
	}
	/**
	 * 获取	责任部门
	 * @return
	 */
	public MdniOrganization getLiableDepartment() {
		return liableDepartment;
	}
	/**
	 * 设置	责任部门
	 * @param liableDepartment
	 */
	public void setLiableDepartment(MdniOrganization liableDepartment) {
		this.liableDepartment = liableDepartment;
	}
	/**
	 * 获取	责任人
	 * @return
	 */
	public User getLiablePerson() {
		return liablePerson;
	}
	/**
	 * 设置	责任人
	 */
	public void setLiablePerson(User liablePerson) {
		this.liablePerson = liablePerson;
	}
	/**
	 * 获取	责任类别1
	 * @return
	 */
	public MdniDictionary getLiableType1() {
		return liableType1;
	}
	/**
	 * 设置	责任类别1
	 * @param liableType1
	 */
	public void setLiableType1(MdniDictionary liableType1) {
		this.liableType1 = liableType1;
	}
	/**
	 * 获取	责任类别2
	 * @return
	 */
	public MdniDictionary getLiableType2() {
		return liableType2;
	}

	public Long getBrand() {
		return brand;
	}
	public void setBrand(Long brand) {
		this.brand = brand;
	}
	/**
	 * 设置	责任类别2
	 * @param liableType2
	 */

	public void setLiableType2(MdniDictionary liableType2) {
		this.liableType2 = liableType2;
	}
	/**
	 * 获取	处理方案
	 * @return
	 */
	public String getTreamentPlan() {
		return treamentPlan;
	}
	/**
	 * 设置	处理方案
	 * @param treamentPlan
	 */
	public void setTreamentPlan(String treamentPlan) {
		this.treamentPlan = treamentPlan;
	}
	/**
	 * 获取	处理完成时间
	 * @return
	 */
	public String getTreamentTime() {
		return treamentTime;
	}
	/**
	 * 设置	处理完成时间
	 * @param treamentTime
	 */
	public void setTreamentTime(String treamentTime) {
		this.treamentTime = treamentTime;
	}
	/**
	 * 获取	反馈时间
	 * @return
	 */
	public String getFeedbackTime() {
		return feedbackTime;
	}
	/**
	 * 设置	反馈时间
	 * @param feedbackTime
	 */
	public void setFeedbackTime(String feedbackTime) {
		this.feedbackTime = feedbackTime;
	}
	/**
	 * 获取	回访结果
	 * @return
	 */
	public String getVisitResult() {
		return visitResult;
	}
	/**
	 * 设置	反馈结果
	 * @param visitResult
	 */
	public void setVisitResult(String visitResult) {
		this.visitResult = visitResult;
	}
	/**
	 * 获取	状态
	 * @return
	 */
	public Integer getStatuFlag() {
		return statuFlag;
	}
	/**
	 * 设置	状态
	 * @param status
	 */
	public void setStatuFlag(Integer statuFlag) {
		this.statuFlag = statuFlag;
	}
	/**
	 * 获取	工单状态
	 * @return
	 */
	public String getOrderStatus() {
		return orderStatus;
	}
	/**
	 * 设置	工单状态
	 * @param orderStatus
	 */
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	/**
	 * 获取	创建人
	 * @return
	 */
	public Long getCreateUser() {
		return createUser;
	}
	/**
	 * 设置	创建人
	 * @param createUser
	 */
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	/**
	 * 获取	创建时间
	 * @return
	 */
	public String getCreateDate() {
		return createDate;
	}
	/**
	 * 设置	创建时间
	 * @param createDate
	 */
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	/**
	 * 获取 客户ID
	 * @return
	 */
	public String getCustomerId() {
		return customerId;
	}
	/**
	 * 设置	客户ID
	 * @param customerId
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	/**
	 * 获取	客户姓名
	 * @return
	 */
	public String getCustomerName() {
		return customerName;
	}
	/**
	 * 设置	客户姓名
	 * @param customerName
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	/**
	 * 获取	客户电话
	 * @return
	 */
	public String getCustomerMobile() {
		return customerMobile;
	}
	/**
	 * 设置	客户电话
	 * @param customerMobile
	 */
	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}
	/**
	 * 获取	客户地址
	 * @return
	 */
	public String getCustomerAddress() {
		return customerAddress;
	}
	/**
	 * 设置	客户地址
	 * @param customerAddress
	 */
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	/**
	 * 获取	合同开始时间
	 * @return
	 */
	public String getContractStartTime() {
		return contractStartTime;
	}
	/**
	 * 设置	合同开始时间
	 * @param contractStartTime
	 */
	public void setContractStartTime(String contractStartTime) {
		this.contractStartTime = contractStartTime;
	}
	/**
	 * 获取	合同结束时间
	 * @return
	 */
	public String getContractCompleteTime() {
		return contractCompleteTime;
	}
	/**
	 * 设置	合同结束时间
	 * @param contractEndTime
	 */
	public void setContractCompleteTime(String contractCompleteTime) {
		this.contractCompleteTime = contractCompleteTime;
	}
	/**
	 * 获取	设计师名称
	 * @return
	 */
	public String getStyListName() {
		return styListName==null?"":styListName;
	}
	/**
	 * 设置	设计师名称
	 * @param stylistName
	 */
	public void setStyListName(String styListName) {
		this.styListName = styListName;
	}
	/**
	 * 获取	设计师电话
	 * @return
	 */
	public String getStyListMobile() {
		return styListMobile;
	}
	/**
	 * 设置	设计师电话
	 * @param stylistMobile
	 */
	public void setStyListMobile(String styListMobile) {
		this.styListMobile = styListMobile;
	}
	/**
	 * 获取	项目经理名称
	 * @return
	 */
	public String getContractorName() {
		return contractorName==null?"":contractorName;
	}
	/**
	 * 设置	项目经理名称
	 * @param contractorName
	 */
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	/**
	 * 获取	项目经理电话
	 * @return
	 */
	public String getContractorMobile() {
		return contractorMobile;
	}
	/**
	 * 设置	项目经理电话
	 * @param contractorMobile
	 */
	public void setContractorMobile(String contractorMobile) {
		this.contractorMobile = contractorMobile;
	}
	/**
	 * 获取	监理人
	 * @return
	 */
	public String getSupervisorName() {
		return supervisorName==null?"":supervisorName;
	}
	/**
	 * 设置	监理人
	 * @param supervisorName
	 */
	public void setSupervisorName(String supervisorName) {
		this.supervisorName = supervisorName;
	}
	/**
	 * 获取	监理人电话
	 * @return
	 */
	public String getSupervisorMobile() {
		return supervisorMobile;
	}
	/**
	 * 设置	监理人电话
	 * @param supervisorMobile
	 */
	public void setSupervisorMobile(String supervisorMobile) {
		this.supervisorMobile = supervisorMobile;
	}
	/**
	 * 获取	是否复制标志
	 * @description 此字段仅当 order_status状态为 UNEXECUTED 时才起作用,
	 * 当order_status 字段为 UNEXECUTED ,若此copyFlag为1则表示已处理,否则为处理
	 * @return
	 */
	public String getCopyFlag() {
		return copyFlag;
	}
	/**
	 * 设置	是否复制标志
	 * @description 此字段仅当 order_status状态为 UNEXECUTED 时才起作用,
	 * 当order_status 字段为 UNEXECUTED ,若此copyFlag为1则表示已处理,否则为处理
	 * @param copyFlag
	 */
	public void setCopyFlag(String copyFlag) {
		this.copyFlag = copyFlag;
	}
	/**
	 * 获取	拷贝的工单ID
	 * @return
	 */
	public Long getCopyWorkId() {
		return copyWorkId;
	}
	/**
	 * 设置	拷贝的工单ID
	 * @param copyWorkId
	 */
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

	public MdniOrganization getLiableCompany() {
		return liableCompany;
	}

	public void setLiableCompany(MdniOrganization liableCompany) {
		this.liableCompany = liableCompany;
	}

	public MdniDictionary getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(MdniDictionary complaintType) {

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
	public MdniDictionary getSource() {
		return source;
	}
	public void setSource(MdniDictionary source) {
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

	public MdniOrganization getLiableSupplier() {
		return liableSupplier;
	}

	public void setLiableSupplier(MdniOrganization liableSupplier) {
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
