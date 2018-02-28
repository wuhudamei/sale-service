<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>同步工单失败列表</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <%@include file="/include/admin/head.jsp" %>
    <link href="${ctx}/static/admin/css/zTreeStyle.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/admin/vendor/webuploader/webuploader.css">
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
                    <form id="searchForm" @submit.prevent="query">
                        <div class="row">
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label class="sr-only" for="keyword"></label>
                                    <input
                                            v-model="form.keyword"
                                            id="keyword" name="keyword"
                                            type="text"
                                            placeholder="姓名|手机号" class="form-control"/>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <select
                                            v-model="form.companyId"
                                            id="companyId"
                                            name="companyId"
                                            class="form-control"
                                            @change="findOrganizations()">
                                        <option value="">选择门店</option>
                                        <option v-if="company.parentId!=0" v-for="company in companies" :value="company.id">
                                            {{company.orgName}}
                                        </option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <select v-model="form.departmentId"
                                            id="departmentId"
                                            name="departmentId"
                                            placeholder="选择部门"
                                            class="form-control">
                                        <option value="">选择部门</option>
                                        <option v-for="complaintType in departments" :value="complaintType.id">
                                            {{complaintType.orgName}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group clearfix">
                                    <input v-el:start-date
                                           id="startDate"
                                           maxlength="20"
                                           readonly
                                           data-tabindex="1"
                                           name="startDate" type="text" class="form-control datepicker"
                                           type="text" class="form-control" v-model="form.startDate"
                                           placeholder="开始日期">
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group clearfix">
                                    <input v-el:end-date
                                           id="endDate"
                                           maxlength="20"
                                           readonly
                                           data-tabindex="1"
                                           name="endDate" type="text" class="form-control datepicker"
                                           type="text" class="form-control" v-model="form.endDate"
                                           placeholder="结束日期">
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
                        </div>
                    </form>
                    <table v-el:data-table id="dataTable" width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
            <!-- ibox end -->
        </div>
        <!-- container end-->
    </div>


    <%@include file="/include/admin/footer.jsp" %>

    <!--右侧部分结束-->
</div>


<script src="/static/admin/vendor/webuploader/webuploader.js"></script>
<script src="/static/admin/js/components/webuploader.js"></script>

<!-- template 应该永远比 script靠前 -->
<%@include file="/WEB-INF/views/admin/component/breadcrumb.jsp" %>
<script src="/static/admin/js/mixins.js"></script>
<script src="/static/admin/js/filters.js"></script>
<script src="/static/admin/js/components/breadcrumb.js"></script>
<%@include file="/WEB-INF/views/admin/component/ztree.jsp" %>
<script src="/static/admin/js/components/ztree.js"></script>
<script src="/static/admin/js/jquery.ztree.core.js"></script>
<script src="/static/admin/js/jquery.ztree.excheck.js"></script>

<!-- 主方法，每页均需引用 -->
<script src="/static/admin/js/main.js"></script>
<script src="/static/admin/js/sale/workorder/synList.js?ver=1.0"></script>
</body>
</html>