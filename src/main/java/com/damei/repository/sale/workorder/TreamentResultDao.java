package com.damei.repository.sale.workorder;


import com.damei.common.persistence.CrudDao;
import com.damei.entity.sale.workorder.TreamentResult;
import org.springframework.stereotype.Repository;

@Repository
public interface TreamentResultDao extends CrudDao<TreamentResult> {
    TreamentResult getBack(Long workorderId);
}