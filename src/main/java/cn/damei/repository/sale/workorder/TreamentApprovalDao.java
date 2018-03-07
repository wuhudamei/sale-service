package cn.damei.repository.sale.workorder;


import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sale.workorder.TreamentApproval;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TreamentApprovalDao extends CrudDao<TreamentApproval> {
     List<TreamentApproval> approvalList(Map<String, Object> params);
}