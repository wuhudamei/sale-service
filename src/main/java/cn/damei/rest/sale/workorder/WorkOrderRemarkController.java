package cn.damei.rest.sale.workorder;

import cn.damei.common.BaseController;
import cn.damei.entity.sale.workorder.WorkOrderRemark;
import cn.damei.service.sale.workorder.WorkOrderRemarkService;
import cn.damei.utils.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.damei.dto.StatusDto;

@RestController
@RequestMapping(value = "/damei/workOrderRmk")
public class WorkOrderRemarkController extends BaseController {

	 private static Logger logger = LoggerFactory.getLogger(WorkOrderController.class);//日志
	 @Autowired
	 private WorkOrderRemarkService workOrderRemarkService;
	 
	 
	 @RequestMapping("/add")
	 public Object add(WorkOrderRemark workOrderRemark){
		 try {
	            workOrderRemarkService.insertWorkOrderRemark(workOrderRemark, WebUtils.getLoggedUserId());
	            return StatusDto.buildDataSuccessStatusDto("操作成功!");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return StatusDto.buildDataFailureStatusDto("催单失败,[" + e.getMessage() + "]");
	        }
	 }
	 
	 /**
	  * 催单 :插入工单轨迹表  同时将工单表催单次数+1,且isRead为false
	  *
	  * @date 2017年7月5日 上午11:48:53
	  * @param workOrderRemark 工单轨迹
	  * @return
	  */
	 @RequestMapping("/remainder")
	 public Object remainder(WorkOrderRemark workOrderRemark){
		 try {
			 	workOrderRemarkService.remainder(workOrderRemark);
	            return StatusDto.buildDataSuccessStatusDto("操作成功!");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return StatusDto.buildDataFailureStatusDto("催单失败,[" + e.getMessage() + "]");
	        }
	 }

	@RequestMapping("/getRemainder/{id}")
	public Object findRemainderById(@PathVariable Long id) {
		return StatusDto.buildDataSuccessStatusDto(this.workOrderRemarkService.findRemainderById(id));
	}
	 
}
