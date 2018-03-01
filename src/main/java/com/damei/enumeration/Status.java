package com.damei.enumeration;

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
