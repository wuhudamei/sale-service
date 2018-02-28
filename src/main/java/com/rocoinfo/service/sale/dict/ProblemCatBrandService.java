package com.rocoinfo.service.sale.dict;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.sale.dict.ProblemCatBrand;
import com.rocoinfo.repository.sale.dict.ProblemCatBrandDao;

/**
 * <dl>
 * <dd>Description: 美得你售后 事项分类与品牌中间关系  serivce</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/7/31</dd>
 * <dd>@author：Paul</dd>
 * </dl>
 */
@Service
public class ProblemCatBrandService extends CrudService<ProblemCatBrandDao,ProblemCatBrand> {

	@Autowired
	private ProblemCatBrandDao problemCatBrandDao;
	
	/**
     * 校验是否已经重复添加了:
     * 	检验标准: 通过 责任部门id和问题类型id 是否能查询到对象
	 * @param problemCatBrand
	 * @return true:重复, false:未重复
	 */
	public boolean checkRepeat(ProblemCatBrand problemCatBrand) {
		boolean result = false;
		List<ProblemCatBrand> problemCatBrands = problemCatBrandDao.getByProIdAndBrandId(problemCatBrand);
		if(problemCatBrands != null && problemCatBrands.size() > 0){
			//已经查询到了,故重复
			result = true;
		}
		return result;
	}

	/**
     * 通过事项分类id 查询品牌事项分类集合
     * 		:用于下拉框
     * @author Paul
     * @date 2017年7月31日
     * @param questionType1Id 事项分类id
     * @return
     */
	public List<ProblemCatBrand> findBrandsByQuestionId(Long questionType1Id) {
		return problemCatBrandDao.findBrandsByQuestionId(questionType1Id);
	}
	
	
}
