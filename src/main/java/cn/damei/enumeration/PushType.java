package cn.damei.enumeration;

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
