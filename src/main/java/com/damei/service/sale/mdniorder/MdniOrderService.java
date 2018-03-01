package com.damei.service.sale.mdniorder;

import com.damei.common.service.CrudService;
import com.damei.datasource.DynamicDataSourceHolder;
import com.damei.entity.sale.mdniorder.MdniOrder;
import com.damei.entity.sale.organization.MdniOrganization;
import com.damei.repository.sale.organization.MdniOrganizationDao;
import com.damei.shiro.ShiroUser;
import com.damei.utils.WebUtils;
import com.damei.repository.sale.mdniorder.MdniOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MdniOrderService extends CrudService<MdniOrderDao, MdniOrder> {
	
	@Autowired
	private MdniOrderDao mdniOrderDao;
	@Autowired
	private MdniOrganizationDao organizationDao;

	public boolean checkUsed(Long contractCompany) {
		boolean flag=false;
		MdniOrganization organization = organizationDao.getById(contractCompany);
		if(organization != null){
			String companyName = organization.getOrgCode();
			if (companyName.contains("sx")||companyName.contains("bj")) {
				flag=true;
			}
		}else{
			flag=false;
		}
		return flag;
	}
	/**
	 * 根据手机号或客户姓名查询合同信息
	 * @param parMap 参数map
	 */
    public List<MdniOrder> getOrderInfoByCondition(Map<String,Object> parMap) {
		Object company=parMap.get("contractCompany");
		Long compId=0L;
		//如果传入的company为空，则根据用户公司ID来查客户
    	if(company==null){
			ShiroUser user= WebUtils.getLoggedUser();
			compId=Long.parseLong(user.getCompany());
		}else {
    		//传入的公司不为空就根据传入的查询
			compId = Long.parseLong(company.toString());
		}
			String companyName = organizationDao.getById(compId).getOrgCode();
			if (companyName.contains("sx")) {
				DynamicDataSourceHolder.setDataSource("taiyuanDataSourceMdni"); //设置操作的数据源
			} else if (companyName.contains("bj")) {
				DynamicDataSourceHolder.setDataSource("dataSourceMdni"); //设置操作的数据源
			}

		try {
			List<MdniOrder> midOrderList = mdniOrderDao.getOrderInfoByCondition(parMap);
			for (MdniOrder order : midOrderList) {
				if (order.getIsFinalAmount() ==1)
				{
					order.setPaymentStage("尾款完成");
				}
				else if (order.getIsMediumAmount() == 1 && order.getIsFinalAmount()==0)
				{
					order.setPaymentStage("尾款收款中");
				}
				else if (order.getIsFirstAmount()==1 && order.getIsMediumAmount()==0)
				{
					order.setPaymentStage("中期款收款中");
				}
				else if (order.getIsModifyCost() == 1 && order.getIsFirstAmount()==0)
				{
					order.setPaymentStage("首期款收款中");
				}
				else if (order.getIsImprestAmount()==1 && order.getIsModifyCost()==0)
				{
					order.setPaymentStage("拆改费收款中");
				}
				else if (order.getIsImprestAmount()==0)
				{
					order.setPaymentStage("定金收款中");
				}
				else
				{
					order.setPaymentStage("待付款");
				}
			}
			
			return midOrderList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
			//throw e;
		}finally {
			DynamicDataSourceHolder.clearDataSource();//清除此数据源
		}
    }
}
