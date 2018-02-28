package com.rocoinfo.rest.sale.dict;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rocoinfo.common.BaseController;
import com.rocoinfo.dto.StatusBootTableDto;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.sale.dict.ProblemCatBrand;
import com.rocoinfo.service.sale.dict.ProblemCatBrandService;
import com.rocoinfo.shiro.ShiroUser;
import com.rocoinfo.utils.MapUtils;
import com.rocoinfo.utils.WebUtils;

/**
 * <dl>
 * <dd>Description: 美得你售后 事项分类与品牌中间关系  controller</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/7/31</dd>
 * <dd>@author：Paul</dd>
 * </dl>
 */
@RestController
@RequestMapping(value = "/api/dict/problemCatbrand")
public class ProblemCatBrandController extends BaseController{
	
	@Autowired
	private ProblemCatBrandService problemCatBrandService;
	
	/**
     * 问题/品牌 对应列表
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public Object search(String keyword, Long questionType1,
    					 @RequestParam(defaultValue = "0") int offset,
                         @RequestParam(defaultValue = "10") int limit,
                         @RequestParam(defaultValue = "id") String orderColumn,
                         @RequestParam(defaultValue = "DESC") String orderSort) {
        Map<String, Object> map = new HashMap<String, Object>();
        MapUtils.putNotNull(map,"keyword", keyword);
        MapUtils.putNotNull(map,"questionType1", questionType1);
        PageRequest pageable = new PageRequest(offset, limit, new Sort(Sort.Direction.valueOf(orderSort.toUpperCase()), orderColumn));

        Page<ProblemCatBrand> ProblemCatBrands = problemCatBrandService.searchScrollPage(map, pageable);
        if(ProblemCatBrands == null){
            return StatusBootTableDto.buildDataSuccessStatusDto();
        }
        return StatusBootTableDto.buildDataSuccessStatusDto(ProblemCatBrands);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public Object delete(@RequestParam Long id) {
        try {
        	problemCatBrandService.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return StatusDto.buildDataFailureStatusDto("删除失败！");
        }
        return StatusDto.buildDataSuccessStatusDto("删除成功");
    }

    /**
     * 添加
     * @param orgQuestion
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public Object add(ProblemCatBrand problemCatBrand) {
        try {
        	ShiroUser loggedUser = WebUtils.getLoggedUser();
        	if(loggedUser == null){
        		return StatusDto.buildDataFailureStatusDto("回话失效,请重新登录！");
        	}
        	//校验是否已经重复添加了
        	boolean result = problemCatBrandService.checkRepeat(problemCatBrand);
        	if(result){
        		return StatusDto.buildDataFailureStatusDto("该事项分类对应的品牌已经被添加,请勿重复添加!");
        	}
        	problemCatBrand.setCreateTime(new Date());
        	problemCatBrand.setCreateUser(loggedUser.getId());
            problemCatBrandService.insert(problemCatBrand);
            return StatusDto.buildDataSuccessStatusDto("添加成功!");
        } catch (Exception e) {
            e.printStackTrace();
            return StatusDto.buildDataFailureStatusDto("添加失败！");
        }
    }
    
    /**
     * 通过事项分类id 查询品牌事项分类集合
     * 		:用于下拉框
     * @author Paul
     * @date 2017年7月31日
     * @param questionType1Id 事项分类id
     * @return
     */
    @RequestMapping(value = "/findBrandsByQuestionId",method = RequestMethod.GET)
    public Object findBrandsByQuestionId(@RequestParam Long questionType1Id) {
        return StatusDto.buildDataSuccessStatusDto(
        		problemCatBrandService.findBrandsByQuestionId(questionType1Id));
    }
    
}
