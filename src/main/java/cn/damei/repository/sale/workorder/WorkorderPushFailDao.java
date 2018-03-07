package cn.damei.repository.sale.workorder;

import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sale.workorder.WorkorderPushFail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkorderPushFailDao extends CrudDao<WorkorderPushFail> {
    List <WorkorderPushFail> getByWorkorderId(Long workorderId);

}
