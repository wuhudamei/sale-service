package com.rocoinfo.service.sale.dict;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.sale.dict.Brand;
import com.rocoinfo.entity.sale.dict.Brand;
import com.rocoinfo.repository.sale.dict.BrandDao;
import com.rocoinfo.repository.sale.dict.BrandDao;
import org.springframework.stereotype.Service;

import java.util.List;

/**
/*
/*@author asher
/*@time 2017-03-08 14:23:39
**/
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