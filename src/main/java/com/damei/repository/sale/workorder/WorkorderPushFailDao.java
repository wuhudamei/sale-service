package com.damei.repository.sale.workorder;

import com.damei.common.persistence.CrudDao;
import com.damei.entity.sale.workorder.WorkorderPushFail;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkorderPushFailDao extends CrudDao<WorkorderPushFail> {
    List <WorkorderPushFail> getByWorkorderId(Long workorderId);

}
