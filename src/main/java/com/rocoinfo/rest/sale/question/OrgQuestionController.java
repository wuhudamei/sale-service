package com.rocoinfo.rest.sale.question;

import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusBootTableDto;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.sale.organization.MdniOrganization;
import com.rocoinfo.entity.sale.question.OrgQuestion;
import com.rocoinfo.enumeration.oa.DEPTYPE;
import com.rocoinfo.service.sale.organization.MdniOrganizationService;
import com.rocoinfo.service.sale.question.OrgQuestionService;
import com.rocoinfo.utils.WebUtils;
import com.rocoinfo.weixin.util.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <dl>
 * 部门问题
 * <dd>Description: 美得你crm</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/28</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@RestController
@RequestMapping("/api/question")
@SuppressWarnings("all")
public class OrgQuestionController extends BaseController {
	@Autowired
	OrgQuestionService orgQuestionService;
	@Autowired
	MdniOrganizationService mdniOrganizationService;

	/**
	 * 根据责任部门的id 查询列表
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object search(@RequestParam(required = false) Long orgId, @RequestParam(required = false) Long companyId,
			@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit,
			@RequestParam(defaultValue = "id") String orderColumn,
			@RequestParam(defaultValue = "DESC") String orderSort) {
		Map<String, Object> map = MapUtils.of("orgId", orgId);
		map.put("companyId", companyId);
		PageRequest pageable = new PageRequest(offset, limit,
				new Sort(Sort.Direction.valueOf(orderSort.toUpperCase()), orderColumn));

		Page<OrgQuestion> orgQuestions = orgQuestionService.searchScrollPage(map, pageable);
		if (orgQuestions == null) {
			return StatusBootTableDto.buildDataSuccessStatusDto();
		}
		return StatusBootTableDto.buildDataSuccessStatusDto(orgQuestions);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public Object delete(@RequestParam long id) {
		try {
			orgQuestionService.deleteById(id);
		} catch (Exception e) {
			e.printStackTrace();
			return StatusDto.buildDataFailureStatusDto("删除失败！");
		}
		return StatusDto.buildDataSuccessStatusDto("删除成功");
	}

	/**
	 * 添加
	 * 
	 * @param orgQuestion
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Object add(@RequestBody OrgQuestion orgQuestion) {
		try {
			// 校验是否已经重复添加了
			boolean result = orgQuestionService.checkRepeat(orgQuestion);
			if (result) {
				return StatusDto.buildDataFailureStatusDto("该问题类型已经被添加,请勿重复添加!");
			}
			orgQuestion.setCreateTime(new Date());
			orgQuestion.setCreateUser(WebUtils.getLoggedUser().getId());
			orgQuestionService.insert(orgQuestion);
			return StatusDto.buildDataSuccessStatusDto("添加成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return StatusDto.buildDataFailureStatusDto("添加失败！");
		}
	}

	/**
	 * 查询责任部门
	 * 
	 * @return
	 */
	@RequestMapping(value = "/orgResponsibility", method = RequestMethod.GET)
	public Object findOrgResponsibility(@RequestParam(required = false) Long companyId) {
		List<MdniOrganization> all = mdniOrganizationService.findAll();
		// 过滤责任部门
		List<MdniOrganization> collect;
		if (companyId != null) {
			collect = all.stream()
					.filter(mdniOrganization -> mdniOrganization.getDepType() != null
							&& DEPTYPE.LIABLEDEPARTMENT.equals(mdniOrganization.getDepType())
							&& mdniOrganization.getParentId().equals(companyId))
					.collect(Collectors.toList());
		} else {
			collect = all.stream()
					.filter(mdniOrganization -> mdniOrganization.getDepType() != null
							&& DEPTYPE.LIABLEDEPARTMENT.equals(mdniOrganization.getDepType()))
					.collect(Collectors.toList());
		}
		return StatusDto.buildDataSuccessStatusDto(collect);
	}

	/**
	 * 根据部门查问题
	 * 
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/findOrgQuestion/{orgId}")
	public Object findOrgQuestion(@PathVariable Long orgId) {
		List<Map<String, Object>> findOrgQuestion = this.orgQuestionService.findOrgQuestion(orgId);
		return StatusDto.buildDataSuccessStatusDto(findOrgQuestion);
	}
	/**
	 * 根据部门查问题
	 * 
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/findQuestionType/{orgId}")
	public Object findQuestionType(@PathVariable Long orgId) {
		List<Map<String, Object>> findOrgQuestion = this.orgQuestionService.findOrgQuestion(orgId);
		return StatusDto.buildDataSuccessStatusDto(findOrgQuestion);
	}

}
