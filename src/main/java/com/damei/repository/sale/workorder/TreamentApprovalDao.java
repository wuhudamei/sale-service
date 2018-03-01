package com.damei.repository.sale.workorder;


import com.damei.entity.sale.workorder.TreamentApproval;
import com.damei.common.persistence.CrudDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TreamentApprovalDao extends CrudDao<TreamentApproval> {
     List<TreamentApproval> approvalList(Map<String, Object> params);
}