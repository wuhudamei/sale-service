package com.rocoinfo.entity.template;

import com.rocoinfo.entity.IdEntity;

/**
 * <dl>
 * <dd>Description: 微信工单进度通知模板</dd>
 * <dd>Company: 美得你</dd>
 * <dd>@date：2017-08-30 15:37:18</dd>
 * <dd>@author：Paul</dd>
 * </dl>
 */
public class WeChatWorkOrderStageTemplate extends IdEntity {

    /** 模板消息	头部内容**/
    private String head;
    /** 模板消息	详情路径**/
    private String url;
    /** 模板消息	接收者(此处为综管系统员工号)**/
    private String jobNo;
    /** 模板消息	参数1: 工单号**/
    private String keyword1;
    /** 模板消息	参数2: 工单进度**/
    private String keyword2;
    /** 模板消息	参数3: 工单处理人**/
    private String keyword3;
    /** 模板消息	详情: remark**/
    private String remark;


    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getJobNo() {
        return jobNo;
    }

    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "工单进度通知:{" +
                "head='" + head + '\'' +
                ", url='" + url + '\'' +
                ", jobNo='" + jobNo + '\'' +
                ", keyword1='" + keyword1 + '\'' +
                ", keyword2='" + keyword2 + '\'' +
                ", keyword3='" + keyword3 + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
