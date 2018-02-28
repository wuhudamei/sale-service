package com.rocoinfo.enumeration.oa;

/**
 * <dl>
 * <dd>Description: 婚姻状况</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/22 下午1:45</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
public enum MaritalStatus {

    MARRIED("已婚"), UNMARRIED("未婚");

    private String label;

    MaritalStatus(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
