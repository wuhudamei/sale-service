package com.damei.entity.sale.plan;

import com.damei.entity.IdEntity;

public class UserPlan extends IdEntity {

	private static final long serialVersionUID = 1L;
	private Long userId;
    private String content;

    private String startTime;

    private String endTime;

    private String status;

    public UserPlan() {
    }

    public UserPlan(Long id, String content,String startTime, String endTime) {
        super(id);

        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
