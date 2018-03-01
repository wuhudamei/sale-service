package com.damei.entity.sale.organization;


import com.damei.dto.OrganizationTreeDto;
import com.damei.entity.sale.account.User;
import com.damei.entity.IdEntity;
import com.damei.enumeration.oa.DEPTYPE;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MdniOrganization extends IdEntity {
	private static final long serialVersionUID = 1L;

    private String orgCode;

    private String orgName;

    private Long parentId;

    private String familyCode;

    private Integer status=1;

    private String createDate;

    private Long createUser;

    private String type;

    private DEPTYPE depType;

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getFamilyCode() {
        return familyCode;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode == null ? null : familyCode.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate == null ? null : createDate.trim();
    }

    public Long getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DEPTYPE getDepType() {
        return depType;
    }

    public void setDepType(DEPTYPE depType) {
        this.depType = depType;
    }

    public static OrganizationTreeDto buildOrganizationTree(OrganizationTreeDto curCode, List<MdniOrganization> orgList) {
        if (CollectionUtils.isEmpty(orgList) || curCode == null) {
            return curCode;
        }

        List<OrganizationTreeDto> childNodeList = new ArrayList<>();

        MdniOrganization org = null;
        State state = new State();
        for (int index = 0; index < orgList.size(); index++) {
            org = orgList.get(index);
            if (org == null) {
                continue;
            }
            if (curCode.getId().equals(org.getParentId())) {
                OrganizationTreeDto childNode = new OrganizationTreeDto();
                childNode.setId(org.getId());
                childNode.setText(org.getOrgName());
                if(index == 0){
                    state.setOpened(true);
                }
                childNode.setState(state);
                childNodeList.add(childNode);
            }
        }
        if (CollectionUtils.isNotEmpty(childNodeList)) {
            curCode.setChildren(childNodeList);
        }
        for (OrganizationTreeDto dto : childNodeList) {
            buildOrganizationTree(dto, orgList);
        }
        return curCode;
    }

    public static OrganizationTreeDto buildOrgTreeWithJob(OrganizationTreeDto curCode,
                                            List<MdniOrganization> orgList, User user) {
        if (CollectionUtils.isEmpty(orgList) || curCode == null || user == null) {
            return null;
        }
        List<OrganizationTreeDto> childNodeList = new ArrayList<>();
        MdniOrganization org = null;

        List<String> depIdList = null;
        State state = null;

        if(StringUtils.isNotBlank(user.getPartTimeJob())) {
            depIdList = Arrays.asList(user.getPartTimeJob().split("&"));
        }

        for (int i = 0; i < orgList.size(); i++) {
            org = orgList.get(i);
            if (org == null) {
                continue;
            }
            if (curCode.getId().equals(org.getParentId())) {
                OrganizationTreeDto childNode = new OrganizationTreeDto();
                childNode.setId(org.getId());
                childNode.setText(org.getOrgName());


                if(depIdList != null && depIdList.contains(org.getId().toString())){
                    state = new State();
                    state.setOpened(true);
                    state.setSelected(true);
                }else{
                    if(i == 0){
                        state = new State();
                        state.setOpened(true);
                        state.setDisabled(true);
                    }else{
                        if("COMPANY".equals(org.getType())){
                            state = new State();
                            state.setDisabled(true);
                        }else{
                            state = new State();
                        }

                    }
                }

                childNode.setState(state);
                childNodeList.add(childNode);
            }
        }
        if (CollectionUtils.isNotEmpty(childNodeList)) {
            curCode.setChildren(childNodeList);
        }
        for (OrganizationTreeDto dto : childNodeList) {
            buildOrgTreeWithJob(dto, orgList, user);
        }
        return curCode;
    }
}