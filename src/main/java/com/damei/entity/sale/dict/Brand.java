package com.damei.entity.sale.dict;


import com.damei.entity.IdEntity;

public class Brand extends IdEntity {
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