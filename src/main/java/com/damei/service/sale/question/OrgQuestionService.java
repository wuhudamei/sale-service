package com.damei.service.sale.question;

import com.damei.common.service.CrudService;
import com.damei.entity.sale.question.OrgQuestion;
import com.damei.repository.sale.question.OrgQuestionDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrgQuestionService extends CrudService<OrgQuestionDao,OrgQuestion> {
	
	@Autowired
	private OrgQuestionDao orgQuestionDao;
	
    /**
     * 根据部门id查询 对应的事项分类
     * @param orgId
     * @return
     */
    public List<OrgQuestion> findListByOrgId(long orgId){
        return entityDao.findListByOrgId(orgId);
    }
    
    /**
     * 校验是否已经重复添加了:
     * 	检验标准: 通过 事项分类id和品牌id 是否能查询到对象
     * @param orgQuestion 问题实体
     * @return 重复:true,不重复:false
     */
	public boolean checkRepeat(OrgQuestion orgQuestion) {
		boolean result = false;
		List<OrgQuestion> orgQuestions = orgQuestionDao.getByOrgIdAndDicId(orgQuestion);
		if(orgQuestions != null && orgQuestions.size() > 0){
			//已经查询到了,故重复
			result = true;
		}
		return result;
	}
	
	public List<Map<String,Object>> findOrgQuestion(Long orgId){
		return orgQuestionDao.findOrgQuestion(orgId); 
	}
}
