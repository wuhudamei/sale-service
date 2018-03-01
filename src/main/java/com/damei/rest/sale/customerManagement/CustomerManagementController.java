package com.damei.rest.sale.customerManagement;

import com.damei.entity.sale.customerManagement.CustomerManagement;
import com.damei.service.sale.customerManagement.CustomerManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/customerManagement")
public class CustomerManagementController {

    @Autowired
    private CustomerManagementService customerManagementService;

    @RequestMapping(value = "/getCustomerManagementByOrderNo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public Object findCustomerManagement(@RequestParam(required = false) String orderno) {
        List<CustomerManagement>  customerManagementList = this.customerManagementService.findCustomerManagement(orderno);
        return  customerManagementList;
    }

}
