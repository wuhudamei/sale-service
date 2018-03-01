package com.damei.service.sale.dict;

import com.damei.common.service.CrudService;
import com.damei.repository.sale.dict.ProblemCatBrandDao;
import com.damei.entity.sale.dict.ProblemCatBrand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public List<ProblemCatBrand> findBrandsByQuestionId(Long questionType1Id) {
		return problemCatBrandDao.findBrandsByQuestionId(questionType1Id);
	}
	
	
}
