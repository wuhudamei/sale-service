<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>个人业绩</title>
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

                    <h3>今日业绩</h3>
                        <hr>
                        <div class="row">
                            <div class="col-md-3">首次派单:{{day.firstTimeCount}}</div>
                            <div class="col-md-3">多次来电:{{day.reminderCount}}</div>
                            <div class="col-md-3">咨询完毕:{{day.overTalk}}</div>
                            <div class="col-md-3"></div>
                        </div>
                        <div class="row">
                            <div class="col-md-3">回访满意:{{day.satisfactionVisit}}</div>
                            <div class="col-md-3">回访不满意:{{day.notSatisfactionVisit}}</div>
                            <div class="col-md-3">不再回访:{{day.failVisit}}</div>
                            <div class="col-md-3">暂无评价:{{day.invalidvisit}}</div>
                        </div>
                    <br/>   <br/>    <br/>
                    <h3>本周业绩</h3>
                    <hr>
                    <div class="row">
                        <div class="col-md-3">首次派单:{{week.firstTimeCount}}</div>
                        <div class="col-md-3">多次来电:{{week.reminderCount}}</div>
                        <div class="col-md-3">咨询完毕:{{week.overTalk}}</div>
                        <div class="col-md-3"></div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">回访满意:{{week.satisfactionVisit}}</div>
                        <div class="col-md-3">回访不满意:{{week.notSatisfactionVisit}}</div>
                        <div class="col-md-3">不再回访:{{week.failVisit}}</div>
                        <div class="col-md-3">暂无评价:{{week.invalidvisit}}</div>
                    </div>
                <br/>   <br/>   <br/>
                    <h3>当月业绩</h3>
                    <hr>
                    <div class="row">
                        <div class="col-md-3">首次派单:{{month.firstTimeCount}}</div>
                        <div class="col-md-3">多次来电:{{month.reminderCount}}</div>
                        <div class="col-md-3">咨询完毕:{{month.overTalk}}</div>
                        <div class="col-md-3"></div>
                    </div>
                    <div class="row">
                        <div class="col-md-3">回访满意:{{month.satisfactionVisit}}</div>
                        <div class="col-md-3">回访不满意:{{month.notSatisfactionVisit}}</div>
                        <div class="col-md-3">不再回访:{{month.failVisit}}</div>
                        <div class="col-md-3">暂无评价:{{month.invalidvisit}}</div>
                    </div>

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
<script src="/static/admin/js/sale/reportCenter/personalWork.js"></script>
</body>
</html>