package com.rocoinfo.dto;

import com.rocoinfo.entity.sale.dict.MdniDictionary;

/**
 * <dl>
 * <dd>Description: 工单统计dto</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2017/3/22 12:19</dd>
 * <dd>@author：Kong</dd>
 * </dl>
 */
public class WorkOrderDto {
    /**
     * 售后系统工单id
     */
    private Long id;

    /**
     * 售后系统工单Code
     */
    private String orderCode;

    /**
     * 用户手机号
     */
    private String phone;

    /**
     * 用户地址
     */
    private String address;

    private Integer liableType1;
    /**
     * 责任类别1名称
     */
    private String liableType1Name;


    private Integer liableType2;
    /**
     * 责任类别2名称
     */
    private String liableType2Name;

    private WorkOrderDto(Builder builder) {
        this.id = builder.id;
        this.orderCode = builder.orderCode;
        this.phone = builder.phone;
        this.address = builder.address;
        this.liableType1 = builder.liableType1;
        this.liableType1Name = builder.liableType1Name;
        this.liableType2 = builder.liableType2;
        this.liableType2Name = builder.liableType2Name;
    }

    public WorkOrderDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getLiableType1() {
        return liableType1;
    }

    public void setLiableType1(Integer liableType1) {
        this.liableType1 = liableType1;
    }

    public String getLiableType1Name() {
        return liableType1Name;
    }

    public void setLiableType1Name(String liableType1Name) {
        this.liableType1Name = liableType1Name;
    }

    public Integer getLiableType2() {
        return liableType2;
    }

    public void setLiableType2(Integer liableType2) {
        this.liableType2 = liableType2;
    }

    public String getLiableType2Name() {
        return liableType2Name;
    }

    public void setLiableType2Name(String liableType2Name) {
        this.liableType2Name = liableType2Name;
    }

    /**
     * 工单统计构造器
     */
    public static class Builder {
        /**
         * 售后系统工单id
         */
        private Long id;

        /**
         * 售后系统工单Code
         */
        private String orderCode;


        /**
         * 用户手机号
         */
        private String phone;

        /**
         * 用户地址
         */
        private String address;

        private Integer liableType1;
        /**
         * 责任类别1名称
         */
        private String liableType1Name;

        private Integer liableType2;
        /**
         * 责任类别2名称
         */
        private String liableType2Name;

        public Builder setId(Long id) {
            this.id = id;
            return this;
        }

        public Builder setOrderCode(String orderCode) {
            this.orderCode = orderCode;
            return this;
        }

        public Builder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public Builder setAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder setLiableType1(Integer liableType1) {
            this.liableType1 = liableType1;
            return this;
        }

        public Builder setLiableType1Name(String liableType1Name) {
            this.liableType1Name = liableType1Name;
            return this;
        }

        public Builder setLiableType2(Integer liableType2) {
            this.liableType2 = liableType2;
            return this;
        }

        public Builder setLiableType2Name(String liableType2Name) {
            this.liableType2Name = liableType2Name;
            return this;
        }

        public Builder setLiableType1Name(MdniDictionary liableType1Name) {
            this.liableType1Name = liableType1Name.getName();
            return this;
        }


        public Builder setLiableType2Name(MdniDictionary liableType2Name) {
            this.liableType2Name = liableType2Name.getName();
            return this;
        }

        public WorkOrderDto build() {
            return new WorkOrderDto(this);
        }
    }


}
