<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>待审核</title>
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
                <div class="ibox-content">
                    <form id="searchForm" @submit.prevent="query">
                        <div class="row">
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label class="sr-only" for="keyword"></label>
                                    <select
                                            v-model="form.keyword"
                                            id="keyword" name="keyword"
                                             class="form-control">
                                        <option value="" >请选择状态</option>
                                        <option value="PROCESSING">未过期</option>
                                        <option value="COMPLETED">已过期</option>
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


<script src="/static/admin/vendor/webuploader/webuploader.js"></script>
<script src="/static/admin/js/components/webuploader.js"></script>

<!-- template 应该永远比 script靠前 -->
<%@include file="/WEB-INF/views/admin/component/breadcrumb.jsp" %>
<script src="/static/admin/js/mixins.js"></script>
<script src="/static/admin/js/filters.js"></script>
<script src="/static/admin/js/components/breadcrumb.js"></script>

<!-- 主方法，每页均需引用 -->
<script src="/static/admin/js/main.js"></script>
<script src="/static/admin/js/sale/workorder/approvalList.js"></script>
</body>
</html>