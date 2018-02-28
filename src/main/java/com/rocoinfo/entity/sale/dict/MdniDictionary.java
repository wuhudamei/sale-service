package com.rocoinfo.entity.sale.dict;


import com.rocoinfo.entity.IdEntity;

/**
/*
/*@author asher
/*@time 2017-03-08 14:23:39
**/
public class MdniDictionary extends IdEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

    private Integer parentCode=0;

    private String parentName;

    // 1:重要类别1
//            2:重要类别2
//            3:责任类别1
//            4:责任类别2 mdni_dictionary.type
    private Integer type;

    private Integer sort;

    private Integer status;//是否删除 0未删除 1已删除

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getParentCode() {
        return parentCode;
    }

    public void setParentCode(Integer parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}