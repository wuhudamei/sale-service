package com.rocoinfo.enumeration;

/**
 * <dl>
 * <dd>Description: 工单轨迹表  操作类型 </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-3-16 09:48:31</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
public enum OrderExecuteStatus {

	CREATE("新建工单"),CONSULTOVER("咨询完毕"),OPERATION("处理"),
    OPERATIONAGAIN("重新分派责任部门"),RECEIVE("接收"),FOLLOWUP("跟进"),
    REJECT("申诉"),REJECTAGAIN("被驳回"),ASSIGN("分配"), REPLY("回复"),
    CALLBACK("再联系"), VISIT("回访"),FINISHORDER("完成工单"), REMARK("备注"),
    CLOSED("结案"),UNEXECUTED("回访未执行"),REMAINDER("催单"),SELECTREMAINDER("查看催单")
    ,MODIFYEXPECTEDTIME("修改预期时间"), TURNSEND("转派"), INVALID("无效");

    private String label;

    OrderExecuteStatus(String label) {
        this.label = label;
    }

    public String getLable() {
        return this.label;
    }
}
