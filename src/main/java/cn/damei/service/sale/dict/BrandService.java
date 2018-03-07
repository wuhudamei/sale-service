package cn.damei.service.sale.dict;

import cn.damei.common.service.CrudService;
import cn.damei.entity.sale.dict.Brand;
import cn.damei.dto.StatusDto;
import cn.damei.repository.sale.dict.BrandDao;
import org.springframework.stereotype.Service;

@Service
public class BrandService extends CrudService<BrandDao,Brand> {

    /**
     * 添加或修改数据字典
     * @param brand
     * @return
     */
    public Object addOrUpdate(Brand brand) {
        if (brand.getId() == null) {
            this.entityDao.insert(brand);
            return StatusDto.buildDataSuccessStatusDto("添加成功");
        }

        this.entityDao.update(brand);
        return StatusDto.buildDataSuccessStatusDto("修改成功");
    }

}