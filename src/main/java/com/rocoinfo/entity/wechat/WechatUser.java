package com.rocoinfo.entity.wechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rocoinfo.entity.IdEntity;

import java.io.Serializable;
import java.util.List;

public class WechatUser extends IdEntity implements Serializable {

    /**
     * 是否订阅公众号
     */
    private Integer subscribe;
    /**
     * openid
     */
    private String openid;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 城市
     */
    private String city;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 语言
     */
    private String language;

    /**
     * 头像
     */
    private String headimgurl;

    /**
     * 关注时间戳
     */
    private String subscribeTime;

    /**
     * 平台编码
     */
    private String unionid;

    /**
     * 备注
     */
    private String remark;

    /**
     * 所在组id
     */
    private Long groupid;

    @JsonProperty("tagid_list")
    private List<String> tagidList;

    public List<String> getTagidList() {
        return tagidList;
    }

    public void setTagidList(List<String> tagidList) {
        this.tagidList = tagidList;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    private static final long serialVersionUID = 1L;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getSubscribeTime() {
        return subscribeTime;
    }

    @JsonProperty(value = "subscribe_time")
    public void setSubscribeTime(String subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }
}