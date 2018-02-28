package com.rocoinfo.entity.sale.customerManagement;


import com.rocoinfo.entity.IdEntity;
import com.rocoinfo.entity.sale.dict.MdniDictionary;
import com.rocoinfo.entity.sale.organization.MdniOrganization;

/**
 * <dl>
 * <dd>Description: 工单POJO</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-03-08 13:23:18</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
public class CustomerManagement extends IdEntity {


	/** 工单编号 **/
	private String workOrderCode;
	/**事项类别**/
	private MdniDictionary questionType1;
	/**问题类型**/
	private MdniDictionary questionType2;
	/** 责任部门 **/
	private MdniOrganization liableDepartment;
	/** 工单状态 **/
	private String orderStatus;
	/** 创建时间 **/
	private String createDate;
	//工单完成时间
	private String operationDate;
    /**工单来源**/
    private MdniDictionary source;
	//投诉原因
	private MdniDictionary complaintType;
	//工单数量
	private Integer workCount;
	//完成数量
	private Integer completCount;
	/** 预计完成时间 **/
	private String treamentTime;
	/** 接待时间 **/
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
