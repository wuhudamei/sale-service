package com.rocoinfo.enumeration;

/**
 * <dl>
 * <dd>Description: </dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2016/6/29 13:54</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */

public enum Gender {

    MALE("男"), FEMALE("女"),OTHER("未选择");

    Gender(String label) {
        this.label = label;
    }

    private String label;

    public String getLabel() {
        return label;
    }
}
