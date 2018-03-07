package cn.damei.service.sale.dameiorder;

import cn.damei.common.service.CrudService;
import cn.damei.utils.WebUtils;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.sale.dameiorder.DameiOrder;
import cn.damei.entity.sale.dameiorganization.DameiOrganization;
import cn.damei.repository.sale.dameiorganization.DameiOrganizationDao;
import cn.damei.shiro.ShiroUser;
import cn.damei.repository.sale.dameiorder.DameiOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DameiOrderService extends CrudService<DameiOrderDao, DameiOrder> {
	
	@Autowired
	private DameiOrderDao dameiOrderDao;
	@Autowired
	private DameiOrganizationDao organizationDao;

	public boolean checkUsed(Long contractCompany) {
		boolean flag=false;
		DameiOrganization organization = organizationDao.getById(contractCompany);
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
    public List<DameiOrder> getOrderInfoByCondition(Map<String,Object> parMap) {
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
				DynamicDataSourceHolder.setDataSource("taiyuanDataSourceDamei"); //设置操作的数据源
			} else if (companyName.contains("bj")) {
				DynamicDataSourceHolder.setDataSource("dataSourceDamei"); //设置操作的数据源
			}

		try {
			List<DameiOrder> midOrderList = dameiOrderDao.getOrderInfoByCondition(parMap);
			for (DameiOrder order : midOrderList) {
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
