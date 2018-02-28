package com.rocoinfo.service.sale.dict;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.dto.StatusDto;
import com.rocoinfo.entity.sale.dict.MdniDictionary;
import com.rocoinfo.repository.sale.dict.MdniDictionaryDao;

/**
/*
/*@author asher
/*@time 2017-03-08 14:23:39
**/
@Service
public class MdniDictionaryService  extends CrudService<MdniDictionaryDao,MdniDictionary> {

    /**
     * 添加或修改数据字典
     * @param mdniDictionary
     * @return
     */
    public Object addOrUpdate(MdniDictionary mdniDictionary) {
        if (mdniDictionary.getId() == null) {
            if(mdniDictionary.getParentCode()==null){
                mdniDictionary.setParentCode(0);
            }
            //给默认值
            mdniDictionary.setStatus(0);
            this.entityDao.insert(mdniDictionary);
            return StatusDto.buildDataSuccessStatusDto("添加成功");
        }

        this.entityDao.update(mdniDictionary);
        return StatusDto.buildDataSuccessStatusDto("修改成功");
    }

    /**
     * 获取父类代码
     * @return
     */
    public List<MdniDictionary> getNode(Long type){
        return this.entityDao.getNode(type);
    }

    /**
     * 通过名称获取字典
     * @param name
     * @return
     */
   public MdniDictionary getByName(String name){
       return this.entityDao.getByName(name);
   }

    /**
     * 通过类型获取字典内容
     * @return
     */
    public List<MdniDictionary> getByType(Integer parentCode,Integer type){
        return this.entityDao.getByType(parentCode,type);
    }

    public List<MdniDictionary> findByCompanyLiableDep(Integer liableDep){
        return this.entityDao.findByCompanyLiableDep(liableDep);
    }
}