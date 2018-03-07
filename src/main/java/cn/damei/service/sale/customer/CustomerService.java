package cn.damei.service.sale.customer;
import cn.damei.common.service.CrudService;
import cn.damei.entity.sale.customer.Customer;
import cn.damei.repository.sale.customer.CustomerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class CustomerService extends CrudService<CustomerDao,Customer> {

	@Autowired
	private CustomerDao customerDao;
	
    public List<Customer> findCustomerByNameAndMobile(String customerName, String customerMobile, Long companyId) {
        return this.entityDao.findCustomerByNameAndMobile(customerName,customerMobile,companyId);
    }
    public List<Customer> findCustomerByNameOrMobile(String keyword,Long companyId) {
        return this.entityDao.findCustomerByNameOrMobile(keyword,companyId);
    }
    
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