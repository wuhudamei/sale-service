package com.rocoinfo.repository.sale.dict;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.dict.MdniDictionary;

/**
/*
/*@author asher
/*@time 2017-03-08 14:23:39
**/
@Repository
public interface MdniDictionaryDao extends CrudDao<MdniDictionary> {

    /**
     * 获取父类代码
     * @return
     */
    List<MdniDictionary> getNode(@Param("type") Long type);

    /**
     * 通过名称获取字典内容
     * @param name
     * @return
     */
    MdniDictionary getByName(@Param("name") String name);

    /**
     * 通过类型获取字典内容
     * @return
     */
    List<MdniDictionary> getByType(@Param("parentCode") Integer parentCode,
                                   @Param("type") Integer type);

    List<MdniDictionary> findByCompanyLiableDep(@Param("liableDepartment") Integer liableDep);
}