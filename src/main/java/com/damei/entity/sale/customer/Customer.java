package com.damei.entity.sale.customer;

import com.damei.entity.IdEntity;
import com.damei.entity.sale.organization.MdniOrganization;

public class Customer extends IdEntity {
	
    private String customerName;

    private String customerMobile;

    private String customerAddress;

    private MdniOrganization company;
    
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

    public MdniOrganization getCompany() {
        return company;
    }

    public void setCompany(MdniOrganization company) {
        this.company = company;
    }

    public Boolean getBlackFlag() {
        return blackFlag;
    }

    public void setBlackFlag(Boolean blackFlag) {
        this.blackFlag = blackFlag;
    }
}