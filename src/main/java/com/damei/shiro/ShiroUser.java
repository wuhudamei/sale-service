package com.damei.shiro;

import com.damei.enumeration.oa.DEPTYPE;

import java.io.Serializable;
import java.util.List;

public class ShiroUser implements Serializable {

    private static final long serialVersionUID = -1473281454547002154L;
    /**
     * 用户id
     */
    private Long id;
    
    /**
     * 员工唯一编号
     */
    private String account;

    /**
     * 部门码
     */
    private String depCode;
    
    /**
     * 集团码(登录账号)
     */
    private String orgCode;
    
    /**
     * 姓名
     */
    private String name;

    /**
     * 电话
     */
    private String mobile;

    /**
     * 公司id
     */
    private String company;
    
    /**
     * 公司编码
     */
    private String companyCode;
    
    /**
     * 部门id
     */
    private String department;

    /**
     * 部门属性
     */
    private DEPTYPE depType;
    /**
     * 部门编码
     */
    private String departmentCode;
    /**
     * 部门名称
     */
    private String departmentName;
    
    /**
     * 角色信息
     */
    private List<String> roles;

    /**
     * 权限信息
     */
    private List<String> permissions;

    /**
     * 是不是部门负责人  0 不是  1是
     */
    private String departmentHead;

    public ShiroUser(Long id,String account, String depCode, String orgCode, String name, String mobile,
    		String company,
    		String companyCode,
    		String department,
    		String departmentCode,
    		String departmentName,
            DEPTYPE depType,
            String departmentHead,
            List<String> roles,
            List<String> permissions) {
        this.id = id;
        this.account = account;
        this.departmentHead = departmentHead;
        this.depCode = depCode;
        this.orgCode = orgCode;
        this.name = name;
        this.mobile = mobile;
        this.company = company;
        this.companyCode = companyCode;
        this.depType=depType;
        this.department = department;
        this.departmentCode = departmentCode;
        this.departmentName = departmentName;
        this.roles = roles;
        this.permissions = permissions;
    }

    /**
     * 本函数输出将作为默认的<shiro:principal/>输出.
     */
    @Override
    public String toString() {
        return orgCode;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShiroUser other = (ShiroUser) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    public String getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }

    public Long getId() {
        return id;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

    public DEPTYPE getDepType() {
        return depType;
    }

    public void setDepType(DEPTYPE depType) {
        this.depType = depType;
    }

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
	
}