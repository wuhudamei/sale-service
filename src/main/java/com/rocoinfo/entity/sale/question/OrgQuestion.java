package com.rocoinfo.entity.sale.question;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * <dl>
 * <dd>Description: 美得你售后 责任部门对应事项分类 实体</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/28</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */

public class OrgQuestion extends IdEntity {

    /**
     * 对应字典表
     * 事项分类 id
     */
    private Long dicId;
    /**
     * 对应字典表
     * 事项分类 名字
     */
    private String dicName;
    /**
     * 对应组织表
     * 责任部门 id
     */
    private Long orgId;
    /**
     * 对应组织表
     * 责任部门 名称
     */
    private String orgName;
    /**
     * 对应分公司的id
     */
    private String companyId;
    /**
     * 对应分公司的名称
     */
    private String company;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    
    /**
     * 创建人
     */
    private Long createUser;
    
	public Long getDicId() {
		return dicId;
	}
	public void setDicId(Long dicId) {
		this.dicId = dicId;
	}
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}
}