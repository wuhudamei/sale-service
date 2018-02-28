package com.rocoinfo.service.sale.customerManagement;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.sale.customerManagement.CustomerManagement;
import com.rocoinfo.repository.sale.customerManagement.CustomerManagementDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13 0013.
 */
@Service
public class CustomerManagementService extends CrudService<CustomerManagementDao,CustomerManagement> {

    @Autowired
    private CustomerManagementDao customerManagementDao;

    public List<CustomerManagement> findCustomerManagement(String orderno) {

        List<CustomerManagement> customerManagementList = this.customerManagementDao.findCustomerManagementByOrderNo(orderno);

        return customerManagementList;
    }
}
