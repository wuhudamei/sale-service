package cn.damei.service.sale.customerManagement;

import cn.damei.common.service.CrudService;
import cn.damei.entity.sale.customerManagement.CustomerManagement;
import cn.damei.repository.sale.customerManagement.CustomerManagementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerManagementService extends CrudService<CustomerManagementDao,CustomerManagement> {

    @Autowired
    private CustomerManagementDao customerManagementDao;

    public List<CustomerManagement> findCustomerManagement(String orderno) {

        List<CustomerManagement> customerManagementList = this.customerManagementDao.findCustomerManagementByOrderNo(orderno);

        return customerManagementList;
    }
}
