package com.rocoinfo.repository.sale.customer;


import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.customer.Customer;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
/*
/*@author weiys
/*@time 2017-05-10 13:57:53
**/
@Repository
public interface CustomerDao extends CrudDao<Customer> {
    List<Customer> findCustomerByNameAndMobile(@Param("customerName") String customerName,
                                              @Param("customerMobile") String customerMobile,
                                              @Param("companyId") Long companyId);
    
    List<Customer> findCustomerByNameOrMobile(@Param("keyword") String keyword);
    
    /**
     * 门店客户库/集团客户库:
     * 查询 当前登录人所在公司下的客户列表
     * @author Paul
     * @date 2017年6月28日 上午11:16:53
     * @param params 条件
     * @return
     */
	List<Customer> findCustomerWithStore(Map<String, Object> params);
	
	
    List<Customer> findCustomerByNameOrMobile(@Param("keyword") String keyword,
                                              @Param("companyId") Long company);

    /**
     * 查询门店客户库/集团客户库 总条数
     * @author Paul
     * @date 2017年6月30日 上午10:23:51
     * @param params 条件
     * @return
     */
	long countCustomerWithStore(Map<String, Object> params);
}