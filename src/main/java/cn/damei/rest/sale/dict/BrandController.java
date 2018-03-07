package cn.damei.rest.sale.dict;

import cn.damei.common.BaseController;
import cn.damei.dto.StatusBootTableDto;
import cn.damei.entity.sale.dict.Brand;
import cn.damei.service.sale.dict.BrandService;
import com.google.common.collect.Maps;
import cn.damei.dto.StatusDto;
import cn.damei.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/api/dict/brand")
public class BrandController extends BaseController {
    @Autowired
    BrandService brandService;

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
                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "20") int pageSize,
                       @RequestParam(value = "sortName", defaultValue = "id") String orderColumn,
                       @RequestParam(value = "sortOrder", defaultValue = "DESC") String orderSort) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);

        PageRequest page = new PageRequest(offset, pageSize, new Sort(Sort.Direction.valueOf(orderSort.toUpperCase()), orderColumn));

        return StatusBootTableDto.buildDataSuccessStatusDto(brandService.searchScrollPage(params, page));
    }

    /**
     * 数据字典增加或修改
     *
     * @param brand
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Object add(@RequestBody Brand brand) {
        brandService.addOrUpdate(brand);
        return StatusDto.buildDataSuccessStatusDto("操作成功");
    }

    @RequestMapping(value = "/findAll")
    public Object findAll(){
        return StatusDto.buildDataSuccessStatusDto(brandService.findAll());
    }

    /**
     * 删除数据字典
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete/{id}")
    public Object delete(@PathVariable Long id) {
        brandService.deleteById(id);
        return StatusDto.buildDataSuccessStatusDto("删除成功");
    }


    /**
     * 根据id获取用户信息
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public StatusDto getById(@PathVariable(value = "id") Long id) {

        Brand dic = brandService.getById(id);
        if (dic == null) {
            return StatusDto.buildFailureStatusDto("品牌不存在！");
        }
        return StatusDto.buildDataSuccessStatusDto(dic);

    }

}