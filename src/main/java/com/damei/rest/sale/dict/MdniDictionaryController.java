package com.damei.rest.sale.dict;

import com.damei.common.BaseController;
import com.damei.entity.sale.dict.MdniDictionary;
import com.google.common.collect.Maps;
import com.damei.dto.StatusBootTableDto;
import com.damei.dto.StatusDto;
import com.damei.service.sale.dict.MdniDictionaryService;
import com.damei.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping(value = "/api/dict/dic")
public class MdniDictionaryController extends BaseController {
    @Autowired
    MdniDictionaryService mdniDictionaryService;

    /**
     * 数据字典分页列表
     *
     * @param keyword
     * @param
     * @param offset
     * @param pageSize
     * @param orderColumn
     * @param orderSort
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam(value = "keyword", required = false) String keyword,
                       @RequestParam(value = "searchType", required = false) Long searchType,
                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                       @RequestParam(value = "sortName", defaultValue = "id") String orderColumn,
                       @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "searchType", searchType);

        PageRequest page = new PageRequest(offset, pageSize, new Sort(Sort.Direction.valueOf(orderSort.toUpperCase()), orderColumn));

        return StatusBootTableDto.buildDataSuccessStatusDto(mdniDictionaryService.searchScrollPage(params, page));
    }

    /**
     * 数据字典增加或修改
     *
     * @param mdniDictionary
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object add(@RequestBody MdniDictionary mdniDictionary) {
        mdniDictionaryService.addOrUpdate(mdniDictionary);
        return StatusDto.buildDataSuccessStatusDto("操作成功");
    }

    /**
     * 删除数据字典
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public Object delete(@PathVariable Long id) {
        mdniDictionaryService.deleteById(id);
        return StatusDto.buildDataSuccessStatusDto("删除成功");
    }

    /**
     * 获取父类代码
     *
     * @return
     */
    @RequestMapping("/getNode/{type}")
    public Object getNode(@PathVariable("type") String type) {
        return StatusDto.buildDataSuccessStatusDto(mdniDictionaryService.getNode(Long.parseLong(type)));
    }

    /**
     * 根据id获取用户信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StatusDto getById(@PathVariable(value = "id") Long id) {

        MdniDictionary dic = mdniDictionaryService.getById(id);
        if (dic == null) {
            return StatusDto.buildFailureStatusDto("获取数据字典不存在！");
        }
        return StatusDto.buildDataSuccessStatusDto(dic);

    }

    /**
     * 通过类型获取字典内容
     *
     * @param parentType
     * @param type
     * @return
     */
    @RequestMapping(value = "/getByType")
    public Object getByType(@RequestParam(value = "parentType", defaultValue = "0") int parentType,
                            @RequestParam Integer type) {
        return StatusDto.buildDataSuccessStatusDto(mdniDictionaryService.getByType(parentType, type));
    }

    @RequestMapping(value = "/getByComLiableDep")
    public Object getByComLiableDep(@RequestParam(value = "depId") Integer depId) {
        return StatusDto.buildDataSuccessStatusDto(this.mdniDictionaryService.findByCompanyLiableDep(depId));
    }
}