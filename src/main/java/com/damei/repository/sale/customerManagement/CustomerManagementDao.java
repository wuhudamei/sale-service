package com.damei.repository.sale.customerManagement;

import com.damei.common.persistence.CrudDao;
import com.damei.entity.sale.customerManagement.CustomerManagement;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerManagementDao extends CrudDao<CustomerManagement> {

    List<CustomerManagement> findCustomerManagementByOrderNo(String orderno);
}
