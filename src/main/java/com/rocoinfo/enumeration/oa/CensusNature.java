package com.rocoinfo.enumeration.oa;

/**
 * <dl>
 * <dd>Description: 户籍性质</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/22 下午1:39</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
public enum CensusNature {
    BJ_CITY("北京城镇"), BJ_COUNTRY("北京农村"), OTHER_CITY("外地城镇"), OTHER_COUNTRY("外地农村");

    private String label;

    CensusNature(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
