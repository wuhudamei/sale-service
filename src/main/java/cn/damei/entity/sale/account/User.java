package cn.damei.entity.sale.account;


import cn.damei.entity.IdEntity;
import cn.damei.entity.sale.dameiorganization.DameiOrganization;
import cn.damei.enumeration.Gender;
import cn.damei.enumeration.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Transient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
public class User extends IdEntity {

    private static final long serialVersionUID = -6124169517696837801L;

    private String account;
    
    private String depCode;
    
    private String orgCode;

    private String name;

    @JsonIgnore
    private String plainPassword;

    @JsonIgnore
    private String passWord;

    @JsonIgnore
    private String salt;

    private String phone;

    private String email;

    private Gender sex;

    private DameiOrganization department;

    private DameiOrganization company;

    @Transient
    @JsonIgnore
    private List<Role> roles; // 有序的关联对象集合
    private Status status;

    private String createDate;

    private Long createUser;
    private String departmentHead;
    private Integer remindFlag;

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

    public DameiOrganization getDepartment() {
        return department;
    }

    public void setDepartment(DameiOrganization department) {
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

    public DameiOrganization getCompany() {
        return company;
    }

    public void setCompany(DameiOrganization company) {
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
