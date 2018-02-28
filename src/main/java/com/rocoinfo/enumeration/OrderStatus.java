package com.rocoinfo.enumeration;

/**
 * <dl>
 * <dd>Description: </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-3-10 16:05:17</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
public enum OrderStatus {
    CREATE("未派单"),RECEIVED("已接收"),PROCESSING("处理中"),REFUSED("申诉"),
    REFUSEDAGAIN("申诉无效"),PENDING("待处理"), URGE("催单"),ASSIGN("待分配"), NREPLY("待回复"),
    INVALIDITY("无效工单"),
    //以下是 工单已完成之后的状态
    COMPLETED("已完成"), SATISFIED("回访满意"),COMMONLY("一般满意"), UNSATISFIED("回访不满意"),
    INVALIDVISIT("暂无评价"), FAILUREVISIT("不再回访"), NVISIT("待回访"), CONSULTOVER("咨询完毕"),
    UNEXECUTED("回访未执行");

    private String label;

    OrderStatus(String label) {
        this.label = label;
    }

    public String getLable() {
        return this.label;
    }
}
