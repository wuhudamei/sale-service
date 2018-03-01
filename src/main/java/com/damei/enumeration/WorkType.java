package com.damei.enumeration;

public enum  WorkType {
    PRESALE("售前"),SELLING("售中"),AFTERSALE("售后");

    private String label;

    WorkType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
