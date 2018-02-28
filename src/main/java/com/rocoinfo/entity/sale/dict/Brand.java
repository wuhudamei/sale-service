package com.rocoinfo.entity.sale.dict;


import com.rocoinfo.entity.IdEntity;

/**
/*
/*@author asher
/*@time 2017-03-08 14:23:39
**/
public class Brand extends IdEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2L;

	private String name;

    private Integer sort;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}