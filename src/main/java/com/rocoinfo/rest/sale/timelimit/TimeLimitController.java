package com.rocoinfo.rest.sale.timelimit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rocoinfo.Constants;
import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusBootTableDto;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.sale.account.User;
import com.rocoinfo.entity.sale.customer.Customer;
import com.rocoinfo.entity.sale.dict.MdniDictionary;
import com.rocoinfo.entity.sale.mdniorder.MdniOrder;
import com.rocoinfo.entity.sale.organization.MdniOrganization;
import com.rocoinfo.entity.sale.timelimit.SaleTimeLimit;
import com.rocoinfo.entity.sale.workorder.WorkOrder;
import com.rocoinfo.entity.sale.workorder.WorkOrderForExport;
import com.rocoinfo.entity.sale.workorder.WorkOrderRemark;
import com.rocoinfo.enumeration.OrderStatus;
import com.rocoinfo.service.sale.customer.CustomerService;
import com.rocoinfo.service.sale.dict.MdniDictionaryService;
import com.rocoinfo.service.sale.mdniorder.MdniOrderService;
import com.rocoinfo.service.sale.organization.MdniOrganizationService;
import com.rocoinfo.service.sale.question.OrgQuestionService;
import com.rocoinfo.service.sale.timelimit.TimeLimitService;
import com.rocoinfo.service.sale.workorder.WorkOrderRemarkService;
import com.rocoinfo.service.sale.workorder.WorkOrderService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.DateUtil;
import com.rocoinfo.utils.DateUtils;
import com.rocoinfo.utils.MapUtils;
import com.rocoinfo.utils.WebUtils;
import com.rocoinfo.utils.excel.ExcelUtil;

/**
 * <dl>
 * <dd>Description: 工单接口Controller</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-3-13 10:04:25</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/mdni/timeLimit")
public class TimeLimitController extends BaseController {

	private static Logger logger = LoggerFactory.getLogger(TimeLimitController.class);// 日志

	@Autowired
	private TimeLimitService timeLimitService;
	@Autowired
	private OrgQuestionService orgQuestionService;
	@Autowired
	private MdniDictionaryService mdniDictionaryService;
    @Autowired
    MdniOrganizationService mdniOrganizationService;
	/**
	 * 美得你合同信息列表
	 *
	 * @param keyword
	 *            客户手机号
	 * @return
	 */
	@RequestMapping(value = "/list")
	public Object mdnOrderList(@RequestParam(required = false) Long companyId,
			@RequestParam(required = false) Long departmentId, @RequestParam(required = false) Long questionCategoryId,
			@RequestParam(required = false) Long questionTypeId, @RequestParam(required = false) Long duration,
			@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit,
			@RequestParam(defaultValue = "id") String sort,
			@RequestParam(defaultValue = "DESC") String order) {
		PageRequest pageable = new PageRequest(offset, limit,new Sort(Sort.Direction.valueOf(order.toUpperCase()), sort));
		Map<String, Object> params = Maps.newHashMap();
		params.put("companyId", companyId);
		params.put("questionCategoryId", questionCategoryId);
		params.put("questionTypeId", questionTypeId);
		params.put("duration", duration);
		params.put("departmentId", departmentId);
		Page<SaleTimeLimit> searchScrollPage = timeLimitService.searchScrollPage(params, pageable);
		if (searchScrollPage == null) {
			return StatusBootTableDto.buildDataSuccessStatusDto();
		}
		return StatusBootTableDto.buildDataSuccessStatusDto(searchScrollPage);
	}
	public boolean checkResult(SaleTimeLimit saleTimeLimit) {
		return false;
	}
	@RequestMapping(value = "/save")
	public Object save(HttpServletRequest request, HttpServletResponse response, ModelMap model,
			SaleTimeLimit saleTimeLimit) {
		
		ShiroUser user = WebUtils.getLoggedUser();
		saleTimeLimit.setCreateUser(user.getId());
		Long id = saleTimeLimit.getId();
		// 天转小时保存
		saleTimeLimit.setDuration(saleTimeLimit.getDuration() * 24);
		saleTimeLimit.setCrateDate(new Date());
		if(null == id) { //插入
			boolean checkResult = timeLimitService.checkResult(saleTimeLimit);
			if(!checkResult) {  //没有这一分类的才可以添加
				return	StatusDto.buildDataFailureStatusDto("该类数据已存在");
			}
			timeLimitService.insert(saleTimeLimit);
		}else {
			timeLimitService.update(saleTimeLimit);
		}
		return StatusDto.buildDataSuccessStatusDto("添加成功");
	}

	@RequestMapping(value = "/info")
	public Object info(@RequestParam(required = false) Long id) {
		SaleTimeLimit info = timeLimitService.info(id);
		info.setDuration(info.getDuration()/24);
		//部门
		  List<MdniOrganization> mdniOrganizationList = mdniOrganizationService.findDepartment(info.getCompanyId()+"");
		//问题类型
		  List<Map<String, Object>> findOrgQuestion = this.orgQuestionService.findOrgQuestion(info.getDepartmentId());
		//事项分类
		List<MdniDictionary> byType = mdniDictionaryService.getByType(Integer.parseInt(info.getQuestionCategoryId()+""),6);
		HashMap<Object, Object> resultMap = Maps.newHashMap();
		resultMap.put("info", info);
		resultMap.put("department", mdniOrganizationList);
		resultMap.put("questionCategory", findOrgQuestion);
		resultMap.put("questionType", byType);
		return StatusDto.buildDataSuccessStatusDto(resultMap);
	}
	@RequestMapping(value = "/del")
	public Object del(@RequestParam(required = false) Long id) {
		this.timeLimitService.deleteById(id);
		return StatusDto.buildDataSuccessStatusDto("操作成功");
	}


	/**
	 *  通过门店,部门,类别,类型  查找时限,并计算好 最终完成时间
	 * @param companyId
	 * @param departmentId
	 * @param questionCategoryId
	 * @param questionTypeId
	 * @return
	 */
	@RequestMapping(value = "/getLimitTime")
	public Object getLimitTimeByQuery(Long companyId,Long departmentId,Long questionCategoryId,
						   Long questionTypeId, String createDate) {
		if(companyId == null || departmentId == null || questionCategoryId == null
				|| questionTypeId == null){
			return StatusDto.buildFailureStatusDto("门店,部门,事项分类,问题类型不能为空!");
		}
		return StatusDto.buildDataSuccessStatusDto(timeLimitService.getLimitTimeByQuery(companyId,
				departmentId, questionCategoryId, questionTypeId, createDate));
	}
}
