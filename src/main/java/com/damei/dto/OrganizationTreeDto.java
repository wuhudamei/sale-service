package com.damei.dto;

import com.damei.entity.sale.organization.State;

import java.util.List;

public class OrganizationTreeDto {

    public OrganizationTreeDto() {
        super();
    }

    public OrganizationTreeDto(Long id, String text) {
        super();
        this.id = id;
        this.text = text;
    }

    private Long id;

    private List<OrganizationTreeDto> children;

    private String text;


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
