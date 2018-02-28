<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>组织架构管理</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <%--<link href="/static/hplus/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">--%>
    <%--<link href="/static/hplus/css/plugins/iCheck/custom.css" rel="stylesheet">--%>
    <%--<link href="/static/hplus/js/plugins/layer/skin/layer.css" rel="stylesheet">--%>
    <link rel="stylesheet" href="/static/hplus/css/plugins/jsTree/themes/default/style.min.css"/>
    <link href="/static/hplus/js/plugins/layer/skin/layer.css" rel="stylesheet">
    <%@include file="/include/admin/head.jsp" %>
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<div id="wrapper">
    <%@include file="/include/admin/nav.jsp" %>
    <!-- 顶部 -->
    <%@include file="/include/admin/header.jsp" %>
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <!-- container -->
        <!-- 内容部分 start-->
        <!-- 面包屑 -->
        <div id="container" class="wrapper wrapper-content">
            <div id="breadcrumb">
                <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
            </div>
            <!-- ibox start -->
            <div class="ibox">
                <div class="wrapper-content animated fadeInRight">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="ibox">
                                <div class="ibox-content">
                                    <div class="row">
                                        <form id="searchForm">
                                            <div class="col-md-12 text-left">
                                                <div class="form-group" id="buttons">
                                                    <shiro:hasPermission name="organization:add">
                                                        <a id="createBtn" @click="createBtnClickHandler"
                                                           class="btn btn-outline btn-primary">新增组织</a>
                                                    </shiro:hasPermission>

                                                    <shiro:hasPermission name="organization:edit">
                                                        <a id="editBtn" @click="editBtn"
                                                           class="btn btn-outline btn-primary">编辑</a>
                                                    </shiro:hasPermission>

                                                    <shiro:hasPermission name="organization:delete">
                                                        <a id="deleteBtn" @click="deleteBtn"
                                                           class="btn btn-outline btn-danger">删除</a>
                                                    </shiro:hasPermission>

                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="ibox-content">
                                        <div class="row">
                                            <div class="col-md-5">
                                                <div id="jstree"></div>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- ibox end -->
        </div>
        <!-- container end-->
        <div id="modal" class="modal fade" tabindex="-1" data-width="760">
            <validator name="validation">
                <form name="createMirror" novalidate class="form-horizontal" role="form">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3>编辑组织机构</h3>
                    </div>
                    <div class="modal-body">

                        <div class="form-group" :class="{'has-error':$validation.name.invalid && submitBtnClick}">
                            <label for="orgName" class="col-sm-2 control-label">组织机构名称</label>
                            <div class="col-sm-8">
                                <input v-model="org.orgName"
                                       v-validate:name="{
                                    required:{rule:true,message:'请输入组织机构名称'},
                                    maxlength:{rule:20,message:'组织机构名称最长不能超过20个字符'}
                                }"
                                       maxlength="20"
                                       data-tabindex="1"
                                       id="orgName" name="orgName" type="text" class="form-control"
                                       placeholder="组织机构名称">
                                <span v-cloak v-if="$validation.name.invalid && submitBtnClick" class="help-absolute">
                            <span v-for="error in $validation.name.errors">
                              {{error.message}} {{($index !== ($validation.name.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                            </div>
                        </div>
                        <div class="form-group" :class="{'has-error':$validation.name.invalid && submitBtnClick}">
                            <label for="orgCode" class="col-sm-2 control-label">组织机构代码</label>
                            <div class="col-sm-8">
                                <input v-model="org.orgCode"
                                       v-validate:name="{
                                    required:{rule:true,message:'请输入组织机构名称'},
                                    maxlength:{rule:20,message:'组织机构名称最长不能超过20个字符'}
                                }"
                                       maxlength="20"
                                       data-tabindex="2"
                                       id="orgCode" name="orgCode" type="text" class="form-control"
                                       placeholder="组织机构名称">
                                <span v-cloak v-if="$validation.name.invalid && submitBtnClick" class="help-absolute">
                            <span v-for="error in $validation.name.errors">
                              {{error.message}} {{($index !== ($validation.name.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="type" class="col-sm-2 control-label">组织机构类型</label>
                            <div class="col-sm-3">
                                <select
                                        v-validate:type="{required:true}"
                                        v-model="org.type"
                                        id="type"
                                        name="type"
                                        data-tabindex="3"
                                        class="form-control"
                                        @change="chooseDepartment()">
                                    <option value="COMPANY">公司</option>
                                    <option value="DEPARTMENT">部门</option>
                                    <option value="SUPPLIER">供应商</option>
                                </select>
                            </div>
                        </div>

                        <div v-show="org.type =='DEPARTMENT'" class="form-group">
                            <label for="dep_type" class="col-sm-2 control-label">部门类型</label>
                            <div class="col-sm-3">
                                <select
                                        v-model="org.depType"
                                        id="dep_type"
                                        name="dep_type"
                                        data-tabindex="4"
                                        class="form-control">
                                    <option value="GROUPCUSTOMERSERVICE">集团客管部</option>
                                    <option value="FILIALECUSTOMERSERVICE">分公司客管部</option>
                                    <option value="LIABLEDEPARTMENT">责任部门</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
                        <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
                    </div>
                </form>
            </validator>
        </div>

    </div>
    <%@include file="/include/admin/footer.jsp" %>

    <!--右侧部分结束-->
</div>
<!-- template 应该永远比 script靠前 -->
<%@include file="/WEB-INF/views/admin/component/breadcrumb.jsp" %>

<script src="/static/admin/js/mixins.js"></script>
<script src="/static/admin/js/filters.js"></script>
<script src="/static/admin/js/components/breadcrumb.js"></script>
<script src="/static/hplus/js/plugins/validate/jquery.validate.min.js"></script>
<script src="/static/hplus/js/plugins/validate/messages_zh.min.js"></script>
<script src="/static/hplus/js/plugins/iCheck/icheck.min.js"></script>

<script src="/static/hplus/js/plugins/sweetalert/sweetalert.min.js"></script>
<script src="/static/hplus/js/plugins/jsTree/jstree.js"></script>
<!-- 主方法，每页均需引用 -->
<script src="/static/admin/js/main.js"></script>
<script src="/static/admin/js/sale/organization/orglist.js"></script>
</body>
</html>