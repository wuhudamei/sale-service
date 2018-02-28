<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>不再回访</title>
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
            <div class="ibox" v-cloak>
                <div class="ibox-content" >
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
                                    <select v-model="form.companyId"
                                            id="companyId" name="companyId"
                                            placeholder="选择门店"
                                            class="form-control" @change="findOrganizations(true)">
                                        <option v-for="org in organizations" :value="org.id" >{{org.orgName}}</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="col-md-2">
                                <div class="form-group">
                                    <select v-model="form.departmentId"
                                            id="departmentId" name="departmentId"
                                            placeholder="选择部门"
                                            class="form-control" >
                                        <option value="" >--选择部门--</option>
                                        <option v-for="org in departments" :value="org.id" >{{org.orgName}}</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="col-md-2">
                                <div class="form-group">
                                    <select v-model="form.questionType1"
                                            id="questionType1"
                                            name="questionType1"
                                            placeholder="选择事项分类"
                                            class="form-control">
                                            
                                        <option value="" >--事项分类--</option>
                                        <option v-for="pro in problemCategories" :value="pro.id" >{{pro.name}}</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="col-md-2">
                                <div class="form-group">
                                    <select v-model="form.complaintType"
                                            id="complaintType"
                                            name="complaintType"
                                            placeholder="选择投诉原因"
                                            class="form-control">
                                            
                                        <option value="" >--投诉原因--</option>
                                        <option v-for="complaintType in complaintTypes" :value="complaintType.id" >{{complaintType.name}}</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="col-md-2">
                                <div class="form-group">
                                    <select v-model="form.importantDegree1"
                                            id="importantDegree1"
                                            name="importantDegree1"
                                            placeholder="选择重要程度"
                                            class="form-control">
                                            
                                        <option value="" >--重要程度--</option>
                                        <option v-for="importantDegree1 in importances" :value="importantDegree1.id" >{{importantDegree1.name}}</option>
                                    </select>
                                </div>
                            </div>
                    	</div>
                    	
                    	<div class="row">
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label class="sr-only" for="visitedTimes"></label>
                                    <input
                                            v-model="form.visitedTimes"
                                            id="visitedTimes" name="visitedTimes"
                                            type="number" min="1" step="1"
                                            placeholder="回访次数" class="form-control"/>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="customerFeedbackTime" style="line-height: 34px;cursor:pointer;">
                                        <input
                                                v-model="form.customerFeedbackTime"
                                                id="customerFeedbackTime"
                                                name="customerFeedbackTime"
                                                type="checkbox"
                                                placeholder="客户要求回电时间" />
                                        &nbsp;&nbsp;客户要求回电时间
                                    </label>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="treamentTime" style="line-height: 34px;cursor:pointer;">
                                        <input
                                                v-model="form.treamentTime"
                                                id="treamentTime"
                                                name="treamentTime"
                                                type="checkbox"
                                                placeholder="完成时间" />
                                        &nbsp;&nbsp;完成时间
                                    </label>
                                </div>
                            </div>
                            
                            <div class="col-md-2">
                                <div class="form-group">
                                    <input v-model="form.startDate"
                                           id="startDate" name="startDate"
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
						</div>
					</form> 
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
<script src="/static/admin/js/sale/workorder/workOrderListWithUnsuccessful.js"></script>
</body>
</html>