package cn.damei.entity.sale.customer;

import cn.damei.entity.IdEntity;
import cn.damei.entity.sale.dameiorganization.DameiOrganization;

public class Customer extends IdEntity {
	
    private String customerName;

    private String customerMobile;

    private String customerAddress;

    private DameiOrganization company;
    
    private Integer amount;

    private Boolean blackFlag;


    
    public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile == null ? null : customerMobile.trim();
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress == null ? null : customerAddress.trim();
    }

    public DameiOrganization getCompany() {
        return company;
    }

    public void setCompany(DameiOrganization company) {
        this.company = company;
    }

    public Boolean getBlackFlag() {
        return blackFlag;
    }

    public void setBlackFlag(Boolean blackFlag) {
        this.blackFlag = blackFlag;
    }
}