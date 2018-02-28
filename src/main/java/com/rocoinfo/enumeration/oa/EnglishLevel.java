package com.rocoinfo.enumeration.oa;

/**
 * <dl>
 * <dd>Description: 英语水平</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/22 下午1:46</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
public enum EnglishLevel {

    BASE("基本"), GENERAL("一般"), PROFICIENCY("熟练"), MASTER("精通");

    private String label;

    EnglishLevel(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
