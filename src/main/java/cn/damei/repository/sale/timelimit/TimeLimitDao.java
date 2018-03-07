package cn.damei.repository.sale.timelimit;


import cn.damei.common.persistence.CrudDao;
import org.springframework.stereotype.Repository;

import cn.damei.entity.sale.timelimit.SaleTimeLimit;

@Repository
public interface TimeLimitDao extends CrudDao<SaleTimeLimit> {
	public SaleTimeLimit info(Long id);
	public SaleTimeLimit checkResult(SaleTimeLimit timeLimit);

}
