package cn.damei.repository.sale.customer;


import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sale.customer.Customer;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomerDao extends CrudDao<Customer> {
    List<Customer> findCustomerByNameAndMobile(@Param("customerName") String customerName,
                                               @Param("customerMobile") String customerMobile,
                                               @Param("companyId") Long companyId);
    
    List<Customer> findCustomerByNameOrMobile(@Param("keyword") String keyword);
    
	List<Customer> findCustomerWithStore(Map<String, Object> params);
	
	
    List<Customer> findCustomerByNameOrMobile(@Param("keyword") String keyword,
                                              @Param("companyId") Long company);

	long countCustomerWithStore(Map<String, Object> params);
}