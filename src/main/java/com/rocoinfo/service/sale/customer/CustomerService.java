package com.rocoinfo.service.sale.customer;
import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.sale.customer.Customer;
import com.rocoinfo.entity.sale.workorder.WorkOrder;
import com.rocoinfo.repository.sale.customer.CustomerDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
/*
/*@author weiys
/*@time 2017-05-10 13:57:53
**/
@Service
public class CustomerService extends CrudService<CustomerDao,Customer> {

	@Autowired
	private CustomerDao customerDao;
	
    public List<Customer> findCustomerByNameAndMobile(String customerName,String customerMobile,Long companyId) {
        return this.entityDao.findCustomerByNameAndMobile(customerName,customerMobile,companyId);
    }
    public List<Customer> findCustomerByNameOrMobile(String keyword,Long companyId) {
        return this.entityDao.findCustomerByNameOrMobile(keyword,companyId);
    }
    
    /**
     * 门店客户库/集团客户库:
     * 查询 当前登录人所在公司下的客户列表
     * @author Paul
     * @date 2017年6月28日 上午11:16:53
     * @param params 查询条件
     * @param pageable 分页数据
     * @return 分页对象
     */
	public Page<Customer> findCustomerWithStore(Map<String, Object> params, PageRequest pageable) {
		List<Customer> customers = Collections.emptyList();
    	//查询该条件下的结果总数
    	long count = customerDao.countCustomerWithStore(params);
    	if (count > 0) {
    		customers = customerDao.findCustomerWithStore(params);
    	}
		return new PageImpl<Customer>(customers, pageable, count);
	}
}