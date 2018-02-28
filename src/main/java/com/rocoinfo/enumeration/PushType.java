package com.rocoinfo.enumeration;

/**
 * <dl>
 * <dd>Description: 美得你</dd>
 * <dd>@date：2017/9/11  15:44</dd>
 * <dd>@author：Ryze</dd>
 * </dl>
 */

public enum PushType {
    PUSH("推送失败"),TURNDOWN("在驳回失败");

    private String label;

    PushType(String label) {
        this.label = label;
    }

    public String getLable() {
        return this.label;
    }
}
