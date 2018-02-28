package com.rocoinfo.enumeration;

/**
 * <dl>
 * <dd>Description: </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017-3-8 13:39:14</dd>
 * <dd>@author：Andy</dd>
 * </dl>
 */
public enum Status {

    NORMAL("正常"), INVALID("无效"), DELETE("已删除");

    private String label;

    Status(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
