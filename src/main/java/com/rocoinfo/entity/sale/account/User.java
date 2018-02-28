package com.rocoinfo.entity.sale.account;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rocoinfo.entity.IdEntity;
import com.rocoinfo.entity.sale.organization.MdniOrganization;
import com.rocoinfo.enumeration.Gender;
import com.rocoinfo.enumeration.Status;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * <dl>
 * <dd>Description: 用户POJO</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-03-08 13:23:18</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
public class User extends IdEntity {

    private static final long serialVersionUID = -6124169517696837801L;

    /**
     * 登录账号--就是 jobnum
     */
    private String account;
    
    /**
     * 部门码
     */
    private String depCode;
    
    /**
     * 集团码
     */
    private String orgCode;

    /**
     * 名称
     */
    private String name;

    /**
     * 原始密码(未加密)
     */
    @JsonIgnore
    private String plainPassword;

    /**
     * 加密后的密码
     */
    @JsonIgnore
    private String passWord;

    /**
     * 盐值
     */
    @JsonIgnore
    private String salt;

    /**
     * 电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别
     */
    private Gender sex;

    /**
     * 部门
     */
    private MdniOrganization department;

    /**
     * 公司
     */
    private MdniOrganization company;

    @Transient
    @JsonIgnore
    private List<Role> roles; // 有序的关联对象集合

    /**
     * 状态
     */
    private Status status;

    /**
     * 创建日期
     */
    private String createDate;

    /**
     * 创建人
     */
    private Long createUser;
    /**
     * 是不是部门负责人  0 不是  1是
     */
    private String departmentHead;

    /**
     * 是否不进行微信消息通知 0不提醒, 1提醒,默认1
     */
    private Integer remindFlag;

    /**
     * 兼职部门id, 多个使用&拼接
     */
    private String partTimeJob;



    public String getDepartmentHead() {
        return departmentHead;
    }

    public void setDepartmentHead(String departmentHead) {
        this.departmentHead = departmentHead;
    }

    public User() {
        super();
    }

    public User(long id) {
        super();
        this.id = id;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public List<String> getRoleNameList() {
        if (roles == null || roles.size() == 0) {
            return Collections.emptyList();
        }

        ArrayList<String> roleNameList = new ArrayList<>();
        for (Role role : roles) {
            roleNameList.add(role.getName());
        }

        return roleNameList;
    }

    public LinkedHashSet<String> getPermissions() {
        LinkedHashSet<String> permissions = new LinkedHashSet<>();
        if (roles != null) {
            for (Role role : roles) {
                List<Permission> permission = role.getPermission();
                if (permission != null) {
                    for (Permission perm : permission) {
                        permissions.add(perm.getPermission());
                    }
                }
            }
        }
        return permissions;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MdniOrganization getDepartment() {
        return department;
    }

    public void setDepartment(MdniOrganization department) {
        this.department = department;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Gender getSex() {
        return sex;
    }

    public void setSex(Gender sex) {
        this.sex = sex;
    }

    public MdniOrganization getCompany() {
        return company;
    }

    public void setCompany(MdniOrganization company) {
        this.company = company;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

	public String getDepCode() {
		return depCode;
	}

	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

    public Integer getRemindFlag() {
        return remindFlag;
    }

    public void setRemindFlag(Integer remindFlag) {
        this.remindFlag = remindFlag;
    }

    public String getPartTimeJob() {
        return partTimeJob;
    }

    public void setPartTimeJob(String partTimeJob) {
        this.partTimeJob = partTimeJob;
    }
}
