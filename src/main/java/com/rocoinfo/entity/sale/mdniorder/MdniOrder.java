package com.rocoinfo.entity.sale.mdniorder;

import com.rocoinfo.entity.IdEntity;

/**
 * 美得你合同相关信息
 * @author Andy
 */
public class MdniOrder extends IdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 合同ID **/
	private String orderId;
	/** 合同编号 **/
	private String orderNo;
	/** 客户ID **/
	private String customerId;
	/** 客户姓名 **/
	private String customerName;
	/** 手机号 **/
	private String mobile;
	/** 地址 **/
	private String address;
	/** 合同开始时间 **/
	private String contractStartTime;
	/** 合同竣工时间 **/
	private String contractCompleteTime;
	/** 实际开始时间 **/
	private String actualStartTime;
	/** 实际竣工时间 **/
	private String actualCompletionTime;
	/** 设计师名称 **/
	private String styListName;
	/** 设计师电话 **/
	private String styListMobile;
	/** 监理人名称 **/
	private String superVisorName;
	/** 监理人电话 **/
	private String superVisoMobile;
	/** 项目经理名称 **/
	private String contractor;
	/** 项目经理电话 **/
	private String contact;
	/**定金和改拆费阶段 定：0 改：1**/
	private Integer IsImprestAmount;
	/**首期款阶段**/
	private Integer IsModifyCost;
	/**中期款阶段**/
	private Integer IsFirstAmount;
	/**尾款收款中阶段**/
	private Integer IsMediumAmount;
	/**尾款完成**/
	private Integer IsFinalAmount;
	/**付款阶段**/
	private String paymentStage;



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
	 * 获取	电话
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * 设置	电话
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * 获取	地址
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 设置	地址
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * 获取	设计师名称
	 * @return
	 */
	public String getStyListName() {
		return styListName;
	}

	/**
	 * 设置	设计师名称
	 * @param styListName
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
	 * @param styListMobile
	 */
	public void setStyListMobile(String styListMobile) {
		this.styListMobile = styListMobile;
	}

	/**
	 * 获取	监理人名称
	 * @return
	 */
	public String getSuperVisorName() {
		return superVisorName;
	}

	/**
	 * 设置	监理人名称
	 * @param superVisorName
	 */
	public void setSuperVisorName(String superVisorName) {
		this.superVisorName = superVisorName;
	}

	/**
	 * 获取	监理人电话
	 * @return
	 */
	public String getSuperVisoMobile() {
		return superVisoMobile;
	}

	/**
	 * 设置	监理人电话
	 * @param superVisoMobile
	 */
	public void setSuperVisoMobile(String superVisoMobile) {
		this.superVisoMobile = superVisoMobile;
	}
	
	/**
	 * 获取	项目经理
	 * @return
	 */
	public String getContractor() {
		return contractor;
	}
	
	/**
	 * 设置	项目经理
	 * @param contractor
	 */
	public void setContractor(String contractor) {
		this.contractor = contractor;
	}
	
	/**
	 * 获取	联系人
	 * @return
	 */
	public String getContact() {
		return contact;
	}
	
	/**
	 * 设置	联系人
	 * @param contact
	 */
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	/**
	 * 获取	合同ID
	 * @return
	 */
	public String getOrderId() {
		return orderId;
	}
	
	/**
	 * 设置	合同ID
	 * @param orderId
	 */
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	/**
	 * 获取	合同编号
	 * @return
	 */
	public String getOrderNo() {
		return orderNo;
	}
	
	/**
	 * 设置	合同编号
	 * @param orderNo
	 */
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
	/**
	 * 获取	客户ID
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
	 * 获取	合同竣工时间
	 * @return
	 */
	public String getContractCompleteTime() {
		return contractCompleteTime;
	}
	
	/**
	 * 设置	合同竣工时间
	 * @param contractCompleteTime
	 */
	public void setContractCompleteTime(String contractCompleteTime) {
		this.contractCompleteTime = contractCompleteTime;
	}
	
	/**
	 * 获取	实际开始时间
	 * @return
	 */
	public String getActualStartTime() {
		return actualStartTime;
	}
	
	/***
	 * 设置	实际开始时间
	 * @param actualStartTime
	 */
	public void setActualStartTime(String actualStartTime) {
		this.actualStartTime = actualStartTime;
	}
	
	/**
	 * 获取	实际竣工时间
	 * @return
	 */
	public String getActualCompletionTime() {
		return actualCompletionTime;
	}
	
	/**
	 * 设置	实际竣工时间
	 * @param actualCompletionTime
	 */
	public void setActualCompletionTime(String actualCompletionTime) {
		this.actualCompletionTime = actualCompletionTime;
	}


	public Integer getIsImprestAmount() {
		return IsImprestAmount;
	}

	public void setIsImprestAmount(Integer isImprestAmount) {
		IsImprestAmount = isImprestAmount;
	}

	public Integer getIsModifyCost() {
		return IsModifyCost;
	}

	public void setIsModifyCost(Integer isModifyCost) {
		IsModifyCost = isModifyCost;
	}

	public Integer getIsFirstAmount() {
		return IsFirstAmount;
	}

	public void setIsFirstAmount(Integer isFirstAmount) {
		IsFirstAmount = isFirstAmount;
	}

	public Integer getIsMediumAmount() {
		return IsMediumAmount;
	}

	public void setIsMediumAmount(Integer isMediumAmount) {
		IsMediumAmount = isMediumAmount;
	}

	public Integer getIsFinalAmount() {
		return IsFinalAmount;
	}

	public void setIsFinalAmount(Integer isFinalAmount) {
		IsFinalAmount = isFinalAmount;
	}

	public String getPaymentStage() {
		return paymentStage;
	}

	public void setPaymentStage(String paymentStage) {
		this.paymentStage = paymentStage;
	}

}
