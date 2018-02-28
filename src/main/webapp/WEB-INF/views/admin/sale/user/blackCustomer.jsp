<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>黑名单客户库</title>
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
    <div id="page-wrapper" class="gray-bg dashbard-1" >
        <!-- container -->
        <!-- 内容部分 start-->
        <!-- 面包屑 -->
        <div id="container" class="wrapper wrapper-content"  v-cloak>
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
                                    <label class="sr-only" for="keyword"></label>
                                    <input
                                            v-model="form.keyword"
                                            id="keyword"
                                            name="keyword"
                                            type="text"
                                            placeholder="姓名|手机号" class="form-control"/>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <select v-model="form.companyId"
                                            id="status"
                                            name="status"
                                            placeholder="选择门店"
                                            class="form-control">
                                        <option v-for="org in organizations" :value="org.id" >{{org.orgName}}</option>
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
    
    <!-- 显示该客户下的工单列表 -->
	<div id="modalBrand" class="modal fade" tabindex="-1" data-width="600"
		v-cloak>
		<div class="row">
			&nbsp;
		</div>
		<div class="modal-body">
			<table id="dataTableBrand" width="100%"
				class="table table-striped table-bordered table-hover" />
			</table>
		</div>
		<div class="modal-footer">
			<button type="button" @click="addWorkOrder" class="btn btn-primary">发起工单</button>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button" data-dismiss="modal" class="btn">关闭</button>
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
<script src="/static/admin/js/sale/user/blackCustomer.js"></script>
</body>
</html>