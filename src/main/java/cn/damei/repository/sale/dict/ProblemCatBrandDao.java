package cn.damei.repository.sale.dict;

import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sale.dict.ProblemCatBrand;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProblemCatBrandDao extends CrudDao<ProblemCatBrand> {

	List<ProblemCatBrand> getByProIdAndBrandId(ProblemCatBrand problemCatBrand);

	List<ProblemCatBrand> findBrandsByQuestionId(Long questionType1Id);
	
	
}
