package com.damei.repository.sale.mdniorder;

import com.damei.common.persistence.CrudDao;
import com.damei.entity.sale.mdniorder.MdniOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MdniOrderDao extends CrudDao<MdniOrder> {

	List<MdniOrder> getOrderInfoByCondition(Map<String,Object> parMap);
	
}
