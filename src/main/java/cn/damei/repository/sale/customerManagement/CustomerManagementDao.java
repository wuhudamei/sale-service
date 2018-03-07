package cn.damei.repository.sale.customerManagement;

import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sale.customerManagement.CustomerManagement;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerManagementDao extends CrudDao<CustomerManagement> {

    List<CustomerManagement> findCustomerManagementByOrderNo(String orderno);
}
