<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>责任部门问题设置</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
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
                <div class="ibox-content">
                    <div class="row">
                        <form id="searchForm" @submit.prevent="query">

                            <div class="col-md-2">
                                <div class="form-group">
                                    <select v-model="form.companyId"
                                            id="companyId" name="companyId"
                                            @click="queryOrg"
                                            placeholder="请选择公司"
                                            class="form-control">
                                        <option  value="">请选择分公司</option>
                                        <option v-if="org.parentId!=0" v-for="org in companys" :value="org.id" >{{org.orgName}}</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label class="sr-only" for="orgId"></label>
                                    <select v-model="form.orgId"
                                            id="orgId"
                                            name="orgId"
                                            class="form-control">
                                        <option  value="">请选择责任部门</option>
                                        <option v-for="org in orgs" :value="org.id" >{{org.orgName}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-1">
                                <div class="form-group">
                                    <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default"
                                            alt="搜索"
                                            title="搜索">
                                        <i class="fa fa-search"></i>
                                    </button>
                                </div>
                            </div>
                            <!-- 将剩余栅栏的长度全部给button -->
                            <div class="col-md-12 text-right">
                                <%--<shiro:hasPermission name="admin:role:add">--%>
                                    <div class="form-group">
                                        <button @click="createBtnClickHandler" id="createBtn" type="button"
                                                class="btn btn-outline btn-primary">新增
                                        </button>
                                    </div>
                               <%-- </shiro:hasPermission>--%>
                            </div>
                        </form>
                    </div>
                    <!-- <div class="columns columns-right btn-group pull-right"></div> -->
                    <table v-el:data-table id="dataTable" width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
            <!-- ibox end -->
        </div>
        <!-- container end-->

        <!-- 添加modal-->
        <div id="modal" class="modal fade" tabindex="-1" data-width="760">
            <validator name="validation">
                <form name="createMirror" novalidate class="form-horizontal" role="form">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3>新增问题类型</h3>
                    </div>
                    <div class="modal-body">
                        <div class="form-group" :class="{'has-error':($validation.company.invalid && $validation.touched)}">
                            <label for="company" class="col-sm-2 control-label">分公司</label>
                            <div class="col-sm-8">
                                <select
                                        v-validate:company="{required:true}"
                                        v-model="orgQuestion.company"
                                        id="company"
                                        name="company"
                                        data-tabindex="2"
                                        @click="queryOrg"
                                        class="form-control">
                                    <option  value="">请选择分公司</option>
                                    <option v-if="org.parentId!=0" v-for="org in companys" :value="org.id" >{{org.orgName}}</option>
                                </select>
                                <div v-if="$validation.company.invalid && $validation.touched"
                                     class="help-absolute">
                                    <span v-if="$validation.company.invalid">请选择分公司</span>
                                </div>
                            </div>
                        </div>


                        <div class="form-group" :class="{'has-error':($validation.org.invalid && $validation.touched)}">
                            <label for="org" class="col-sm-2 control-label">责任部门</label>
                            <div class="col-sm-8">
                                <select
                                        v-validate:org="{required:true}"
                                        v-model="orgQuestion.orgId"
                                        id="org"
                                        name="org"
                                        data-tabindex="2"
                                        class="form-control">
                                    <option  value="">请选择责任部门</option>
                                    <option v-for="org in orgs" :value="org.id" >{{org.orgName}}</option>
                                </select>
                                <div v-if="$validation.org.invalid && $validation.touched"
                                     class="help-absolute">
                                    <span v-if="$validation.org.invalid">请选择责任部门</span>
                                </div>
                            </div>
                        </div>


                        <div class="form-group" :class="{'has-error':($validation.org.invalid && $validation.touched)}">
                            <label for="question" class="col-sm-2 control-label">事项分类</label>
                            <div class="col-sm-8">
                                <select
                                        v-validate:question="{required:true}"
                                        v-model="orgQuestion.dicId"
                                        id="question"
                                        name="question"
                                        data-tabindex="2"
                                        class="form-control">
                                    <option  value="">请选择事项分类</option>
                                    <option v-for="question in questions" :value="question.id" >{{question.name}}</option>
                                </select>
                                <div v-if="$validation.question.invalid && $validation.touched"
                                     class="help-absolute">
                                    <span v-if="$validation.question.invalid">请选择事项分类</span>
                                </div>
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
<!-- 主方法，每页均需引用 -->
<script src="/static/admin/js/main.js"></script>
<script src="/static/admin/js/sale/question/question.js"></script>
</body>
</html>