package com.rocoinfo.repository.sale.customerManagement;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.customerManagement.CustomerManagement;
import com.rocoinfo.entity.sale.workorder.WorkOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <dl>
 * <dd>Description: 工单信息Dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-03-10 16:47:26</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@Repository
public interface CustomerManagementDao extends CrudDao<CustomerManagement> {

    List<CustomerManagement> findCustomerManagementByOrderNo(String orderno);
}
