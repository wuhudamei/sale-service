package cn.damei.repository.sale.dameiorder;

import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sale.dameiorder.DameiOrder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface DameiOrderDao extends CrudDao<DameiOrder> {

	List<DameiOrder> getOrderInfoByCondition(Map<String,Object> parMap);
	
}
