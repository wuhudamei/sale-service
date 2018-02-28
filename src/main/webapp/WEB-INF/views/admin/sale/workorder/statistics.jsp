<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>报表统计</title>
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
        <div id="container" class="wrapper wrapper-content" v-cloak>
            <div id="breadcrumb">
                <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
            </div>
            <!-- ibox start -->
            <div class="ibox">
                <div class="ibox-content">
                    <div class="row" style="border-top:1px solid #fff;border-bottom:10px solid #fff;">
                        <form id="searchForm" @submit.prevent="query">
                            <div class="col-sm-2">
                                <div class="form-group">
                                    <select
                                            v-model="form.liable1"
                                            id="liable1"
                                            name="liable1"
                                            class="form-control">
                                        <option v-for="qryType in queryLiableTypes" :value="qryType.id">
                                            {{qryType.name}}
                                        </option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <div class="form-group clearfix">
                                    <input type="text"
                                           v-el:start-date
                                           readonly
                                           class="form-control"
                                           v-model="form.startDate"
                                           placeholder="查询开始时间">
                                </div>
                            </div>
                            <div class="col-sm-2">
                                <div class="form-group clearfix">
                                    <input type="text"
                                           v-el:end-date
                                           readonly
                                           class="form-control" v-model="form.endDate"
                                           placeholder="查询结束时间">
                                </div>
                            </div>
                            <div class="col-sm-1">
                                <button type="submit" class="btn btn-block btn-outline btn-default" alt="搜索" title="搜索">
                                    <i class="fa fa-search"></i>
                                </button>
                            </div>
                            <div class="col-md-5 text-right">

                                <div class="form-group">
                                    <div v-if="customs" class="form-group">
                                            <button @click="exportWorkOrder" id="exportBtn" type="button"
                                                    class="btn btn-outline btn-primary">导出工单
                                            </button>
                                    </div>
                                </div>

                            </div>
                        </form>
                    </div>

                    <div class="row">
                        <div id="customTable">
                            <table class="table table-bordered table-hover table-striped">
                                <thead>
                                <tr>
                                    <th style="text-align: center; " data-field="position" tabindex="0">
                                        <div class="th-inner sortable both">序号</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="text-align: center; " data-field="position" tabindex="1">
                                        <div class="th-inner sortable both">投诉{{liableTypeName}}共{{customerNum}}户
                                            {{orderTotal}}个事项
                                        </div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="text-align: center; " data-field="position" tabindex="2">
                                        <div class="th-inner sortable both">汇总</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr v-for="custom in customs">
                                    <td style="text-align: center; ">{{$index+1}}</td>
                                    <td style="text-align: center; ">{{$key}}</td>
                                    <td style="text-align: center; ">{{custom}}</td>
                                </tr>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th colspan="2" style="text-align: center; " data-field="position" tabindex="0">
                                        <div class="th-inner sortable both">总计</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                    <th style="text-align: center; " data-field="position" tabindex="1">
                                        <div class="th-inner sortable both">{{orderTotal}}</div>
                                        <div class="fht-cell"></div>
                                    </th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>

                    <div class="row"></div>

                    <div class="row">
                        <table class="table table-bordered table-hover table-striped">
                            <thead>
                            <tr>
                                <th style="text-align: center; " data-field="position" tabindex="1">
                                    <div class="th-inner sortable both">序号</div>
                                    <div class="fht-cell"></div>
                                </th>
                                <th style="text-align: center; " data-field="position" tabindex="1">
                                    <div class="th-inner sortable both">责任类别二</div>
                                    <div class="fht-cell"></div>
                                </th>
                                <th style="text-align: center; " data-field="position" tabindex="1">
                                    <div class="th-inner sortable both">汇总</div>
                                    <div class="fht-cell"></div>
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr v-for="liable in liable2s">
                                <td style="text-align: center; ">{{$index+1}}</td>
                                <td style="text-align: center; ">{{$key}}</td>
                                <td style="text-align: center; ">{{liable}}</td>
                            </tr>
                            </tbody>
                            <tfoot>
                            <tr>
                                <th colspan="2" style="text-align: center; " data-field="position" tabindex="1">
                                    <div class="th-inner sortable both">合计</div>
                                    <div class="fht-cell"></div>
                                </th>
                                <th style="text-align: center; " data-field="position" tabindex="1">
                                    <div class="th-inner sortable both">{{orderTotal}}</div>
                                    <div class="fht-cell"></div>
                                </th>
                            </tr>
                            </tfoot>
                        </table>
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
<script src="/static/admin/js/sale/workorder/statistics.js?ver=1.0"></script>
</body>
</html>