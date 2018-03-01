package com.damei.rest.sale.customer;

import com.damei.common.BaseController;
import com.damei.dto.StatusBootTableDto;
import com.damei.entity.sale.customer.Customer;
import com.damei.service.sale.customer.CustomerService;
import com.damei.service.sale.organization.MdniOrganizationService;
import com.damei.utils.MapUtils;
import com.damei.utils.WebUtils;
import com.google.common.collect.Maps;
import com.damei.Constants;
import com.damei.dto.StatusDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/customer")
public class CustomerController extends BaseController {

    @Autowired
    CustomerService customerService;
    @Autowired
    private MdniOrganizationService mdniOrganizationService;
    
    @RequestMapping("/findCustomer")
    public Object findByNameOrMobile(@RequestParam(required = false,defaultValue = "") String keyword,
                                     @RequestParam(value = "offset", defaultValue = "0") int offset,
                                     @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                                     @RequestParam(defaultValue = "id") String orderColumn,
                                     @RequestParam(defaultValue = "DESC") String orderSort,
                                     Long cusCompany) {

        if(cusCompany != null && StringUtils.isNotBlank(keyword)) {
            Map<String, Object> params = Maps.newHashMap();
            MapUtils.putNotNull(params, "keyword", keyword);
            MapUtils.putNotNull(params, "cusCompany", cusCompany);

            PageRequest page = new PageRequest(offset, pageSize, new Sort(Sort.Direction.valueOf(orderSort.toUpperCase()), orderColumn));

            return StatusBootTableDto.buildDataSuccessStatusDto(customerService.searchScrollPage(params, page));
        }else {
            return StatusBootTableDto.buildDataSuccessStatusDto(Collections.EMPTY_LIST, 0L);
        }
    }
    @RequestMapping("/storeList")
    public Object storeList(String keyword,
    		@RequestParam(required = false, defaultValue = "0") Integer offset, 
			@RequestParam(required = false, defaultValue = "20") Integer limit,
			@RequestParam(defaultValue = "id") String orderColumn,
			@RequestParam(defaultValue = "DESC") String orderSort ){
    	
    	Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "keyword", keyword);
		//当前登录人所在公司
		MapUtils.putNotNull(params, "company", WebUtils.getLoggedUser().getCompany());
		MapUtils.putNotNull(params, "offset", offset);
		MapUtils.putNotNull(params, "limit", limit);
		params.put("sort", new Sort(Sort.Direction.valueOf(orderSort), orderColumn));
		
		//创建分页对象
		PageRequest pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));
		params.put(Constants.PAGE_OFFSET, pageable.getOffset()/pageable.getPageSize());
        params.put(Constants.PAGE_SIZE, pageable.getPageSize());
        params.put(Constants.PAGE_SORT, pageable.getSort());
        
        Page<Customer> customerList= customerService.findCustomerWithStore(params,pageable);
        return StatusBootTableDto.buildDataSuccessStatusDto(customerList);
    }
    
    /**
     * 集团客户库:
     * 根据分公司查询的客户列表
     *
     * @date 2017年6月28日 下午3:11:43
     * @version 2.0
     * @param keyword 姓名/手机号查询
     * @param companyId 公司id 如果是集团公司,传null
     * @param blackFlag 是否有很名单标记
     * @return 客户集合
     */
    @RequestMapping("/groupList")
    public Object groupList(String keyword, Long companyId, Boolean blackFlag,
    		@RequestParam(required = false, defaultValue = "0") Integer offset, 
			@RequestParam(required = false, defaultValue = "20") Integer limit,
			@RequestParam(defaultValue = "id") String orderColumn,
			@RequestParam(defaultValue = "DESC") String orderSort ){
    	
    	Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "keyword", keyword);
		MapUtils.putNotNull(params, "company", companyId);
		MapUtils.putNotNull(params, "blackFlag", blackFlag);

		MapUtils.putNotNull(params, "offset", offset);
		MapUtils.putNotNull(params, "limit", limit);
		params.put("sort", new Sort(Sort.Direction.valueOf(orderSort), orderColumn));
		
		//创建分页对象
		PageRequest pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.DESC, "id"));
		params.put(Constants.PAGE_OFFSET, pageable.getOffset()/pageable.getPageSize());
        params.put(Constants.PAGE_SIZE, pageable.getPageSize());
        params.put(Constants.PAGE_SORT, pageable.getSort());
        
        Page<Customer> customerList= customerService.findCustomerWithStore(params,pageable);
        return StatusBootTableDto.buildDataSuccessStatusDto(customerList);
    }

    @RequestMapping("/update")
    public Object update(Customer customer){
        try {
            customerService.update(customer);
            return StatusDto.buildSuccessStatusDto("操作成功!");
        }catch (Exception e){
            e.printStackTrace();
            return StatusDto.buildFailureStatusDto("操作失败!");
        }
    }
}
