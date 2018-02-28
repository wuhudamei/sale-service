package com.rocoinfo.repository.sale.dict;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.dict.ProblemCatBrand;

/**
 * <dl>
 * <dd>Description: 美得你售后 事项分类与品牌中间关系  dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/7/31</dd>
 * <dd>@author：Paul</dd>
 * </dl>
 */
@Repository
public interface ProblemCatBrandDao extends CrudDao<ProblemCatBrand> {

	/**
	 * 通过  责任部门id和问题类型id 查询对象集合
	 * @param problemCatBrand 实体
	 * @return
	 */
	List<ProblemCatBrand> getByProIdAndBrandId(ProblemCatBrand problemCatBrand);

	/**
     * 通过事项分类id 查询品牌事项分类集合
     * 		:用于下拉框
     * @author Paul
     * @date 2017年7月31日
     * @param questionType1Id 事项分类id
     * @return
     */
	List<ProblemCatBrand> findBrandsByQuestionId(Long questionType1Id);
	
	
}
