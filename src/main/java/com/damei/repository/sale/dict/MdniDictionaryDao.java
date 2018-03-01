package com.damei.repository.sale.dict;

import com.damei.entity.sale.dict.MdniDictionary;
import com.damei.common.persistence.CrudDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MdniDictionaryDao extends CrudDao<MdniDictionary> {

    List<MdniDictionary> getNode(@Param("type") Long type);

    MdniDictionary getByName(@Param("name") String name);

    List<MdniDictionary> getByType(@Param("parentCode") Integer parentCode,
                                   @Param("type") Integer type);

    List<MdniDictionary> findByCompanyLiableDep(@Param("liableDepartment") Integer liableDep);
}