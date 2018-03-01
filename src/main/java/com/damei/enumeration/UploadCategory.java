package com.damei.enumeration;

import org.apache.commons.lang3.StringUtils;

public enum UploadCategory {

    CONTRACT("contract"), UEDITOR("ueditor"), NOTICEBOARD("noticeboard"), WORK_ORDER_PHOTO("work_order_photo");
    private String path;

    UploadCategory(String path) {
        this.path = path;
    }

    public static UploadCategory parsePathToCategory(String path) {
        for (UploadCategory category : UploadCategory.values()) {
            if (StringUtils.equalsIgnoreCase(path, category.getPath())) {
                return category;
            }
        }
        return null;
    }

    public String getPath() {
        return path;
    }
}
