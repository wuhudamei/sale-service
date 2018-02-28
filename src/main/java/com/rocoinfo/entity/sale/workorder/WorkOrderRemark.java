package com.rocoinfo.entity.sale.workorder;


import com.rocoinfo.entity.IdEntity;
import com.rocoinfo.entity.sale.account.User;
import com.rocoinfo.entity.sale.dict.MdniDictionary;

/**
 * <dl>
 * <dd>Description: 工单备注POJO</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-3-15 16:33:46</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
public class WorkOrderRemark extends IdEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/** 工单ID **/
	private Long workOrderId;
	/** 操作日期 **/
	private String operationDate;
	/** 操作人 **/
	private User operationUser;
	/** 操作类型 **/
	private String operationType;	
	/** 备注 **/
	private String remark;
	//投诉原因
	private MdniDictionary complaintType;
	
	
	/**
	 * 获取	工单ID
	 * @return
	 */
	public Long getWorkOrderId() {
		return workOrderId;
	}
	/**
	 * 设置	工单ID
	 * @param workOrderId
	 */
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}
	/**
	 * 获取	操作日期
	 * @return
	 */
	public String getOperationDate() {
		return operationDate;
	}
	/**
	 * 设置	操作日期
	 * @param operationDate
	 */
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}
	/**
	 * 获取	操作人
	 * @return
	 */
	public User getOperationUser() {
		return operationUser;
	}
	/**
	 * 设置	操作人
	 * @param operationUser
	 */
	public void setOperationUser(User operationUser) {
		this.operationUser = operationUser;
	}
	/**
	 * 获取	操作类型
	 * @return
	 */
	public String getOperationType() {
		return operationType;
	}
	/**
	 * 设置	操作类型
	 * @param operationType
	 */
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	/**
	 * 获取	备注
	 * @return
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 设置	备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public MdniDictionary getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(MdniDictionary complaintType) {
		this.complaintType = complaintType;
	}
}
