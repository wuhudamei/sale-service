package com.rocoinfo.entity.sale.organization;


import com.rocoinfo.dto.OrganizationTreeDto;
import com.rocoinfo.entity.IdEntity;
import com.rocoinfo.entity.sale.account.User;
import com.rocoinfo.enumeration.oa.DEPTYPE;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *    组织机构
 *   @author asher
 *   @time 2017-03-08 14:14:18
**/
public class MdniOrganization extends IdEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// 如:
    //    北京 bj
    //    上海:sh
    //    广州:gz 等 mdni_organization.org_code
    private String orgCode;

    private String orgName;

    private Long parentId;

    // 例: 1-2-3  mdni_organization.family_code
    private String familyCode;

    // 0:无效 
    // 1:正常 mdni_organization.status
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

    /**
     * 构建目录树,递归操作
     *
     *
     * @return 目录树
     */
    public static OrganizationTreeDto buildOrganizationTree(OrganizationTreeDto curCode, List<MdniOrganization> orgList) {
        if (CollectionUtils.isEmpty(orgList) || curCode == null) {
            return curCode;
        }

        List<OrganizationTreeDto> childNodeList = new ArrayList<>();

        // 构造根节点
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
                    //根节点展开,其他都关闭
                    state.setOpened(true);
                }
                childNode.setState(state);
                childNodeList.add(childNode);
            }
        }
        if (CollectionUtils.isNotEmpty(childNodeList)) {
            // 设置子节点
            curCode.setChildren(childNodeList);
        }
        // 递归构造子节点
        for (OrganizationTreeDto dto : childNodeList) {
            buildOrganizationTree(dto, orgList);
        }
        return curCode;
    }

    /**
     * 构建目录树,递归操作
     * 在用户列表中加载 jsTree 并选中和展开 已选的兼职部门
     *  只可以选择部门
     * @return 目录树
     */
    public static OrganizationTreeDto buildOrgTreeWithJob(OrganizationTreeDto curCode,
                                            List<MdniOrganization> orgList, User user) {
        if (CollectionUtils.isEmpty(orgList) || curCode == null || user == null) {
            return null;
        }
        List<OrganizationTreeDto> childNodeList = new ArrayList<>();
        // 构造根节点
        MdniOrganization org = null;

        //用户兼职部门数组
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


                //判断用户兼职id集合 是否包含当前orgId  如果包含了当前id,就选中当前
                if(depIdList != null && depIdList.contains(org.getId().toString())){
                    //选中
                    state = new State();
                    state.setOpened(true);
                    state.setSelected(true);
                }else{
                    if(i == 0){
                        //跟节点打开 不可选
                        state = new State();
                        state.setOpened(true);
                        state.setDisabled(true);
                    }else{
                        //如果当前org的type是COMPANY 就不可选择
                        if("COMPANY".equals(org.getType())){
                            state = new State();
                            state.setDisabled(true);
                        }else{
                            //其他情况 默认
                            state = new State();
                        }

                    }
                }

                childNode.setState(state);
                childNodeList.add(childNode);
            }
        }
        if (CollectionUtils.isNotEmpty(childNodeList)) {
            // 设置子节点
            curCode.setChildren(childNodeList);
        }
        // 递归构造子节点
        for (OrganizationTreeDto dto : childNodeList) {
            buildOrgTreeWithJob(dto, orgList, user);
        }
        return curCode;
    }
}