package com.damei.enumeration;

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
