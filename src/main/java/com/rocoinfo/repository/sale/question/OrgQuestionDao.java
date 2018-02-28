package com.rocoinfo.repository.sale.question;

import com.rocoinfo.common.persistence.CrudDao;
import com.rocoinfo.entity.sale.question.OrgQuestion;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <dl>
 * <dd>Description: 美得你 责任部门对应事项分类 dao</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/6/28</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */
@Repository
public interface OrgQuestionDao extends CrudDao<OrgQuestion> {
	/**
	 * 根据部门id查询 对应的事项分类
	 * 
	 * @param orgId
	 * @return
	 */
	List<OrgQuestion> findListByOrgId(long orgId);

	/**
	 * 通过dicId和orgId 查询OrgQuestion集合
	 * 
	 * @param dicId
	 * @param orgId
	 * @return
	 */
	List<OrgQuestion> getByOrgIdAndDicId(OrgQuestion orgQuestion);

	/**
	 * 查询部门问题
	 * 
	 * @param orgId
	 * @return
	 */
	List<Map<String,Object>> findOrgQuestion(Long orgId);

}
