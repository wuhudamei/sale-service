package cn.damei.repository.sale.question;

import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sale.question.OrgQuestion;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface OrgQuestionDao extends CrudDao<OrgQuestion> {
	List<OrgQuestion> findListByOrgId(long orgId);

	List<OrgQuestion> getByOrgIdAndDicId(OrgQuestion orgQuestion);

	List<Map<String,Object>> findOrgQuestion(Long orgId);

}
