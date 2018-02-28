<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>工单处理报表</title>
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
                                        <option  value="">请选择公司</option>
                                        <option v-if="org.parentId!=0" v-for="org in companys" :value="org.id" >{{org.orgName}}</option>
                                    </select>
                                </div>
                            </div>



                            <div class="col-md-2">
                                <div class="form-group">
                                    <label class="sr-only" for="orgId"></label>
                                    <select v-model="form.departmentId"
                                            id="orgId"
                                            name="orgId"
                                            class="form-control">
                                        <option  value="">请选择部门</option>
                                        <option v-for="org in orgs" :value="org.id" >{{org.orgName}}</option>
                                    </select>
                                </div>
                            </div>

                            <div class="col-md-2">
                            <div class="form-group">
                                <input v-model="form.beginDate"
                                       id="beginDate" name="beginDate"
                                       type="text" class="datepicker form-control"
                                       placeholder="开始时间" readonly="readonly"/>
                            </div>
                        </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <input
                                            v-model="form.endDate"
                                            id="endDate" name="endDate"
                                            type="text" class="datepicker form-control"
                                            placeholder="结束时间" readonly="readonly"/>
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
<script src="/static/admin/js/sale/reportCenter/workDealGroup.js"></script>
</body>
</html>