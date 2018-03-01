package com.damei.repository.sale.timelimit;


import org.springframework.stereotype.Repository;

import com.damei.common.persistence.CrudDao;
import com.damei.entity.sale.timelimit.SaleTimeLimit;

@Repository
public interface TimeLimitDao extends CrudDao<SaleTimeLimit> {
	public SaleTimeLimit info(Long id);
	public SaleTimeLimit checkResult(SaleTimeLimit timeLimit);

}
