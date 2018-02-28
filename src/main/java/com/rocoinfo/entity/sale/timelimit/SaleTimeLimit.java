package com.rocoinfo.entity.sale.timelimit;

import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * @author
 */
public class SaleTimeLimit extends IdEntity {
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 分店
	 */
	private Long companyId;

	/**
	 * 组织
	 */
	private Long departmentId;

	/**
	 * 类别
	 */
	private Long questionCategoryId;

	/**
	 * 类型
	 */
	private Long questionTypeId;

	/**
	 * 最大时长
	 */
	private Long duration;

	/**
	 *  最终完成时间--不存储于数据库
	 */
	private String finalDate;

	private Long createUser;
	private Date crateDate;


	private static final long serialVersionUID = 1L;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long getQuestionCategoryId() {
		return questionCategoryId;
	}
	public void setQuestionCategoryId(Long questionCategoryId) {
		this.questionCategoryId = questionCategoryId;
	}
	public Long getQuestionTypeId() {
		return questionTypeId;
	}
	public void setQuestionTypeId(Long questionTypeId) {
		this.questionTypeId = questionTypeId;
	}
	public Long getDuration() {
		return duration;
	}
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
	public Date getCrateDate() {
		return crateDate;
	}
	public void setCrateDate(Date crateDate) {
		this.crateDate = crateDate;
	}

	public String getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}
}