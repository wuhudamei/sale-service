package com.rocoinfo.entity.sale.plan;

import com.rocoinfo.entity.IdEntity;

/**
 * <dl>
 * <dd>Description: g用户计划实体</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/15 10:43</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
public class UserPlan extends IdEntity {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long userId;
    /**
     * 计划内容
     */
    private String content;

    /**
     * 计划开始时间
     */
    private String startTime;

    /**
     * 计划结束时间
     */
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
