package cn.damei.rest.sale.dict;

import cn.damei.common.BaseController;
import cn.damei.entity.sale.dict.DameiDictionary;
import cn.damei.service.sale.dict.DameiDictionaryService;
import com.google.common.collect.Maps;
import cn.damei.dto.StatusBootTableDto;
import cn.damei.dto.StatusDto;
import cn.damei.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping(value = "/api/dict/dic")
public class DameiDictionaryController extends BaseController {
    @Autowired
    DameiDictionaryService dameiDictionaryService;

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

        return StatusBootTableDto.buildDataSuccessStatusDto(dameiDictionaryService.searchScrollPage(params, page));
    }

    /**
     * 数据字典增加或修改
     *
     * @param dameiDictionary
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object add(@RequestBody DameiDictionary dameiDictionary) {
        dameiDictionaryService.addOrUpdate(dameiDictionary);
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
        dameiDictionaryService.deleteById(id);
        return StatusDto.buildDataSuccessStatusDto("删除成功");
    }

    /**
     * 获取父类代码
     *
     * @return
     */
    @RequestMapping("/getNode/{type}")
    public Object getNode(@PathVariable("type") String type) {
        return StatusDto.buildDataSuccessStatusDto(dameiDictionaryService.getNode(Long.parseLong(type)));
    }

    /**
     * 根据id获取用户信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StatusDto getById(@PathVariable(value = "id") Long id) {

        DameiDictionary dic = dameiDictionaryService.getById(id);
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
        return StatusDto.buildDataSuccessStatusDto(dameiDictionaryService.getByType(parentType, type));
    }

    @RequestMapping(value = "/getByComLiableDep")
    public Object getByComLiableDep(@RequestParam(value = "depId") Integer depId) {
        return StatusDto.buildDataSuccessStatusDto(this.dameiDictionaryService.findByCompanyLiableDep(depId));
    }
}