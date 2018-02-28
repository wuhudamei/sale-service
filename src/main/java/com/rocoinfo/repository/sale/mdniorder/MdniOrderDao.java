package com.rocoinfo.repository.sale.mdniorder;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.mdniorder.MdniOrder;

/**
 * <dl>
 * <dd>Description: 美的你合同信息Dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-03-09 10:09:45</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@Repository
public interface MdniOrderDao extends CrudDao<MdniOrder> {

    /**
     * 根据手机号查询合同信息
     *
     * @param mobile 电话号
     * @return
     */
	List<MdniOrder> getOrderInfoByCondition(Map<String,Object> parMap);
	
}
