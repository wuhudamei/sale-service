package cn.damei.repository.sale.workorder;


import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sale.workorder.TreamentResult;
import org.springframework.stereotype.Repository;

@Repository
public interface TreamentResultDao extends CrudDao<TreamentResult> {
    TreamentResult getBack(Long workorderId);
}