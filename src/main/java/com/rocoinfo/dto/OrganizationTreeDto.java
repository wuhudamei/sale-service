package com.rocoinfo.dto;

import java.util.List;

import com.rocoinfo.entity.sale.organization.State;

/**
 * 树形结构dto
 */
public class OrganizationTreeDto {

    public OrganizationTreeDto() {
        super();
    }

    public OrganizationTreeDto(Long id, String text) {
        super();
        this.id = id;
        this.text = text;
    }

    /**
     * 节点id
     */
    private Long id;

    /**
     * 子节点
     */
    private List<OrganizationTreeDto> children;

    /**
     * 文本信息
     */
    private String text;


    /**
     * 展开
     */
    private State state;

    public OrganizationTreeDto(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<OrganizationTreeDto> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationTreeDto> children) {
        this.children = children;
    }


    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
