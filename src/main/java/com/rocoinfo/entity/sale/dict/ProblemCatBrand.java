package com.rocoinfo.entity.sale.dict;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rocoinfo.entity.IdEntity;

import java.util.Date;

/**
 * <dl>
 * <dd>Description: 美得你售后 事项分类与品牌中间关系  实体类</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/7/31</dd>
 * <dd>@author：Paul</dd>
 * </dl>
 */
public class ProblemCatBrand extends IdEntity{
	
	private static final long serialVersionUID = 1L;

	/**事项分类*/
	private MdniDictionary questionType1;
	
	/**品牌*/
	private Brand brand;
	
	/**创建时间*/
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    
    /**创建人*/
    private Long createUser;
    
    

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public MdniDictionary getQuestionType1() {
		return questionType1;
	}

	public void setQuestionType1(MdniDictionary questionType1) {
		this.questionType1 = questionType1;
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
