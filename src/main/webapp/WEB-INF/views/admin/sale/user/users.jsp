<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>用户管理</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
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
                <div class="ibox-content">
                    <div class="row">
                        <form id="searchForm" @submit.prevent="query">

                            <div class="col-md-2">
                                <div class="form-group">
                                    <select v-model="form.companyId"
                                            id="companyId"
                                            placeholder="请选择公司"
                                            class="form-control"
                                            @change="queryDepartment">
                                        <option value="" selected>--选择公司--</option>
                                        <option v-for="company in organizations" :value="company.id">{{company.orgName}}
                                        </option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <div class="form-group">
                                        <select v-model="form.departmentId"
                                                id="departmentId"
                                                placeholder="请选择部门"
                                                class="form-control"
                                                @change="querySuppliers()">
                                            <option value="" selected>--选择部门--</option>
                                            <option v-for="department in departments" :value="department.id">
                                                {{department.orgName}}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-2" v-show="suppliers.length > 0">
                                <div class="form-group">
                                    <div class="form-group">
                                        <select v-model="form.supplierId"
                                                id="supplierId"
                                                placeholder="请选择供应商"
                                                class="form-control">
                                            <option value="" selected>--选择供应商--</option>
                                            <option v-for="supplier in suppliers" :value="supplier.id">
                                                {{supplier.orgName}}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label class="sr-only" for="keyword"></label>
                                    <input
                                            v-model="form.keyword"
                                            id="keyword"
                                            name="keyword"
                                            type="text"
                                            placeholder="工号/用户名" class="form-control"/>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <select v-model="form.status"
                                            id="status"
                                            name="status"
                                            placeholder="选择状态"
                                            class="form-control">
                                        <option value="">全部状态</option>
                                        <option value="NORMAL">启用</option>
                                        <option value="INVALID">禁用</option>
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
                            <%--<div class="col-md-7 text-right">--%>
                                <%--<shiro:hasPermission name="admin:user:add">--%>
                                    <%--<div class="form-group">--%>
                                        <%--<button @click="createBtnClickHandler" id="createBtn" type="button"--%>
                                                <%--class="btn btn-outline btn-primary">新增--%>
                                        <%--</button>--%>
                                    <%--</div>--%>
                                <%--</shiro:hasPermission>--%>
                            <%--</div>--%>
                            <div class="col-md-1">
                                <div class="form-group">
                                    <button id="synUser" type="submit" class="btn"
                                            alt="用户同步" :class="synClass" :disabled="synBtn"
                                            title="同步" @click="synUser">{{synName}}
                                    </button>
                                </div>
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

        <!-- 编辑/编辑的modal-->
        <div id="modal" class="modal fade" tabindex="-1" data-width="760">
            <validator name="validation">
                <form name="createMirror" novalidate class="form-horizontal" role="form">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3>{{title}}</h3>
                    </div>
                    <div class="modal-body">
                        <div class="form-group" :class="{'has-error':$validation.orgCode.invalid && submitBtnClick}">
                            <label for="orgCode" class="col-sm-2 control-label">账号</label>
                            <div class="col-sm-8">
                                <input v-model="user.orgCode"
                                       v-validate:org-Code="{
                                    required:{rule:true,message:'请输入账号'}
                                }"
                                       maxlength="20"
                                       data-tabindex="1"
                                       id="orgCode" name="orgCode" type="text" class="form-control"
                                       placeholder="账号" disabled>
                                <span v-cloak v-if="$validation.orgCode.invalid && submitBtnClick"
                                      class="help-absolute">
                            <span v-for="error in $validation.orgCode.errors">
                              {{error.message}} {{($index !== ($validation.account.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                            </div>
                        </div>

                        <div class="form-group" :class="{'has-error':$validation.name.invalid && submitBtnClick}">
                            <label for="name" class="col-sm-2 control-label">姓名</label>
                            <div class="col-sm-8">
                                <input v-model="user.name"
                                       v-validate:name="{
                                    required:{rule:true,message:'请输入姓名'}
                                }"
                                       maxlength="20"
                                       data-tabindex="1"
                                       id="name" name="name" type="text" class="form-control" placeholder="姓名">
                                <span v-cloak v-if="$validation.name.invalid && submitBtnClick" class="help-absolute">
                            <span v-for="error in $validation.name.errors">
                              {{error.message}} {{($index !== ($validation.name.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                            </div>
                        </div>

                        <div class="form-group" :class="{'has-error':$validation.phone.invalid && submitBtnClick}">
                            <label for="phone" class="col-sm-2 control-label">手机</label>
                            <div class="col-sm-8">
                                <input v-model="user.phone"
                                       v-validate:phone="{
                                    required:{rule:true,message:'请输入手机号'},
                                    mobile:{rule:true,message:'请输入正确的手机号'}
                                }"
                                       maxlength="20"
                                       data-tabindex="1"
                                       id="phone" name="phone" type="text" class="form-control" placeholder="手机号">
                                <span v-cloak v-if="$validation.phone.invalid && submitBtnClick" class="help-absolute">
                            <span v-for="error in $validation.phone.errors">
                              {{error.message}} {{($index !== ($validation.phone.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                            </div>
                        </div>

                        <div class="form-group" :class="{'has-error':$validation.email.invalid && submitBtnClick}">
                            <label for="email" class="col-sm-2 control-label">邮箱</label>
                            <div class="col-sm-8">
                                <input v-model="user.email"
                                       v-validate:email="{
                                    required:{rule:true,message:'请输入邮箱'},
                                    email:{rule:true,message:'请输入正确的邮箱'}
                                }"
                                       maxlength="20"
                                       data-tabindex="1"
                                       id="email" name="email" type="text" class="form-control" placeholder="邮箱">
                                <span v-cloak v-if="$validation.email.invalid && submitBtnClick" class="help-absolute">
                            <span v-for="error in $validation.email.errors">
                              {{error.message}} {{($index !== ($validation.email.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                            </div>
                        </div>

                        <div class="form-group" :class="{'has-error':$validation.sex.invalid && submitBtnClick}">
                            <label for="sex" class="col-sm-2 control-label">性别</label>
                            <div class="col-sm-8">
                                <select v-model="user.sex"
                                        v-validate:sex="{
                                            required:{rule:true,message:'请选择性别'}
                                        }"
                                        id="sex"
                                        name="sex"
                                        placeholder="选择性别"
                                        class="form-control">
                                    <option value="">选择性别</option>
                                    <option value="MALE">男</option>
                                    <option value="FEMALE">女</option>
                                </select>
                                <span v-cloak v-if="$validation.sex.invalid && submitBtnClick" class="help-absolute">
                                    <span v-for="error in $validation.sex.errors">
                                      {{error.message}} {{($index !== ($validation.sex.errors.length -1)) ? ',':''}}
                                    </span>
                                </span>
                            </div>
                        </div>

                        <div class="form-group">
                            <label for="remindFlag" class="col-sm-2 control-label">发送微信通知</label>
                            <div class="col-sm-8">
                                <select v-model="user.remindFlag"
                                        id="remindFlag"
                                        name="remindFlag"
                                        placeholder="选择是否发送微信通知"
                                        class="form-control">
                                    <option value="1">是</option>
                                    <option value="0">否</option>
                                </select>
                            </div>
                        </div>

                        <div class="form-group" :class="{'has-error':$validation.company.invalid && submitBtnClick}">
                            <label for="company" class="col-sm-2 control-label">公司</label>
                            <div class="col-sm-8">
                                <select v-model="user.company.id"
                                        v-validate:company="{
                                            required:{rule:true,message:'请选择公司'}
                                        }"
                                        id="company"
                                        placeholder="请选择公司"
                                        class="form-control">
                                    <option value="">选择公司</option>
                                    <option v-for="company in organizations" value="{{company.id}}">{{company.orgName}}
                                    </option>
                                </select>
                                <span v-cloak v-if="$validation.company.invalid && submitBtnClick"
                                      class="help-absolute">
                            <span v-for="error in $validation.company.errors">
                              {{error.message}} {{($index !== ($validation.company.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                            </div>
                        </div>

                        <div class="form-group" :class="{'has-error':$validation.department.invalid && submitBtnClick }">
                            <label for="department" class="col-sm-2 control-label">部门</label>
                            <div class="col-sm-8">
                                <select v-model="user.department.id"
                                        v-validate:department="{
                                            required:{rule:true,message:'请选择部门'}
                                        }"
                                        id="department"
                                        placeholder="请选择部门"
                                        class="form-control"
                                        @change="chooseSupplier()">
                                    <option value="">选择部门</option>
                                    <option v-for="department in departments" value="{{department.id}}">
                                        {{department.orgName}}
                                    </option>
                                </select>
                                <span v-if="$validation.department.invalid && submitBtnClick "
                                      class="help-absolute">
                                    <span v-for="error in $validation.department.errors">
                                      {{error.message}} {{($index !== ($validation.department.errors.length -1)) ? ',':''}}
                                    </span>
                                </span>
                            </div>
                        </div>

                        <!--选择供应商-->
                        <div v-show="suppliers != null" class="form-group">
                            <label for="supplierId" class="col-sm-2 control-label">供应商</label>
                            <div class="col-sm-8">
                                <select v-model="supplierId"
                                        id="supplierId"
                                        placeholder="请选择供应商"
                                        class="form-control">
                                    <option value="">选择供应商</option>
                                    <option v-for="department in suppliers" value="{{department.id}}">
                                        {{department.orgName}}
                                    </option>
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

        <div id="rolesModal" class="modal fade" tabindex="-1" data-width="760">
            <form name="createMirror" novalidate class="form-horizontal" role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3>设置用户角色</h3>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <div class="row" style="margin-left: 40px">
                            <div v-for="role in roles">
                                <div class="col-md-4 col-sm-4 col-xs-6  form-group role-item ellips">
                                    <label :for="role.id" +$index title="{{role.description}}">
                                        <input type="checkbox" :checked="role.checked"
                                               id=role"+$index
                                               @click="checkSub(role,$event)"
                                               data-checkbox="sub"
                                               data-rolename="role.name" data-rolevalue="role.id">
                                        {{role.name}}</label>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
                    <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
                </div>
            </form>
        </div>
        <%--兼职 jstree展示--%>
        <div id="jobModal" class="modal fade" tabindex="-1" data-width="760">
            <form name="createMirror" novalidate class="form-horizontal" role="form">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h3>设置兼职部门</h3>
                </div>
                <div class="modal-body">
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-md-5" id="jstreeParent">
                                <div id="jstree"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
                    <span id="submitId">
                        <button  @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
                    </span>
                </div>
            </form>
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
<script src="/static/hplus/js/plugins/jsTree/jstree.js"></script>
<script src="/static/admin/js/main.js"></script>
<script src="/static/admin/js/sale/user/users.js"></script>
</body>
</html>