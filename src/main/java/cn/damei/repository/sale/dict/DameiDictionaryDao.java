package cn.damei.repository.sale.dict;

import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sale.dict.DameiDictionary;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DameiDictionaryDao extends CrudDao<DameiDictionary> {

    List<DameiDictionary> getNode(@Param("type") Long type);

    DameiDictionary getByName(@Param("name") String name);

    List<DameiDictionary> getByType(@Param("parentCode") Integer parentCode,
                                    @Param("type") Integer type);

    List<DameiDictionary> findByCompanyLiableDep(@Param("liableDepartment") Integer liableDep);
}