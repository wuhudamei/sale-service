package com.rocoinfo.entity.wechat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 关注信息
 *
 * @author jinyu(foxinmy@gmail.com)
 * @date 2014年4月4日
 * @since JDK 1.6
 */
public class Following implements Serializable {

    private static final long serialVersionUID = 1917454368271027134L;

    /**
     * 关注总数
     */
    private int total;
    /**
     * 拉取的OPENID个数，最大值为10000
     */
    private int count;


    /**
     * 微信返回的数据
     */
    private Map<String, List<String>> data;

    /**
     * 列表数据，OPENID的列表
     */
    @JsonIgnore
    private List<String> openIds;

    /**
     * 拉取列表的后一个用户的OPENID
     */
    @JsonProperty(value = "next_openid")
    private String nextOpenId;


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Map<String, List<String>> getData() {
        return data;
    }

    public void setData(Map<String, List<String>> data) {
        this.data = data;
    }

    public List<String> getOpenIds() {

        if(data != null) {
            return data.get("openid");
        }
        return  null;
    }

    public void setOpenIds(List<String> openIds) {
        this.openIds = openIds;
    }

    public String getNextOpenId() {
        return nextOpenId;
    }

    public void setNextOpenId(String nextOpenId) {
        this.nextOpenId = nextOpenId;
    }
}
