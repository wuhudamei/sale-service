package cn.damei.entity.sale.workorder;


import cn.damei.entity.IdEntity;
import cn.damei.entity.sale.account.User;
import cn.damei.entity.sale.dict.DameiDictionary;

public class WorkOrderRemark extends IdEntity {

	private static final long serialVersionUID = 1L;
	
	private Long workOrderId;
	private String operationDate;
	private User operationUser;
	private String operationType;
	private String remark;
	private DameiDictionary complaintType;
	
	
	public Long getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(Long workOrderId) {
		this.workOrderId = workOrderId;
	}
	public String getOperationDate() {
		return operationDate;
	}
	public void setOperationDate(String operationDate) {
		this.operationDate = operationDate;
	}
	public User getOperationUser() {
		return operationUser;
	}
	public void setOperationUser(User operationUser) {
		this.operationUser = operationUser;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public DameiDictionary getComplaintType() {
		return complaintType;
	}
	public void setComplaintType(DameiDictionary complaintType) {
		this.complaintType = complaintType;
	}
}
