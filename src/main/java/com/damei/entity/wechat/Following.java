package com.damei.entity.wechat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class Following implements Serializable {

    private static final long serialVersionUID = 1917454368271027134L;

    private int total;
    private int count;


    private Map<String, List<String>> data;
    @JsonIgnore
    private List<String> openIds;

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
