package com.rocoinfo.repository.sale.timelimit;


import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.timelimit.SaleTimeLimit;

@Repository
public interface TimeLimitDao extends CrudDao<SaleTimeLimit> {
	/**
	 * 修改获取详情
	 * @param id
	 * @return
	 */
	public SaleTimeLimit info(Long id);
	/**
	 * 插入校验
	 * @param timeLimit
	 * @return
	 */
	public SaleTimeLimit checkResult(SaleTimeLimit timeLimit);

}
