package com.rocoinfo.service.sale.timelimit;

import com.rocoinfo.common.service.CrudService;
import com.rocoinfo.entity.sale.timelimit.SaleTimeLimit;
import com.rocoinfo.repository.sale.timelimit.TimeLimitDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Service
public class TimeLimitService extends CrudService<TimeLimitDao, SaleTimeLimit> {
	@Autowired
	private TimeLimitDao timeLimitDao;
	/**
	 * 修改获取详情
	 * @param id
	 * @return
	 */
	public SaleTimeLimit info(Long id){
		return timeLimitDao.info(id);
	}
	public boolean checkResult(SaleTimeLimit timeLimit) {
		SaleTimeLimit checkResult = timeLimitDao.checkResult(timeLimit);
		return (checkResult == null);
	}

	/**
	 *  通过门店,部门,类别,类型  查找时限,并计算好 最终完成时间
	 * @param companyId
	 * @param departmentId
	 * @param questionCategoryId
	 * @param questionTypeId
	 * @return
	 */
    public SaleTimeLimit getLimitTimeByQuery(Long companyId, Long departmentId,
						 Long questionCategoryId, Long questionTypeId, String createDate) {
		SaleTimeLimit saleTimeLimit = new SaleTimeLimit();
		saleTimeLimit.setCompanyId(companyId);
		saleTimeLimit.setDepartmentId(departmentId);
		saleTimeLimit.setQuestionCategoryId(questionCategoryId);
		saleTimeLimit.setQuestionTypeId(questionTypeId);
		//查询时限
		saleTimeLimit = timeLimitDao.checkResult(saleTimeLimit);

		try {
			if(StringUtils.isNotBlank(createDate) && saleTimeLimit != null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				//给工单创建时间 加 时限
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(sdf.parse(createDate));

				saleTimeLimit.setDuration(saleTimeLimit.getDuration()/24);
				calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + saleTimeLimit.getDuration().intValue());
				saleTimeLimit.setFinalDate(sdf.format(calendar.getTime()));
			}
		}catch (Exception e){
			e.printStackTrace();
		}

    	return saleTimeLimit;
    }
}
