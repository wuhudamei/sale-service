<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>催单记录</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <%@include file="/include/admin/head.jsp" %>
    <link rel="stylesheet" href="/static/css/tab.css">
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
            <!-- ibox start -->
            <div class="ibox">
                <div class="ibox-content">
                    <div class="form-group clear">
                        <label for="remarks" class="col-sm-2 control-label">操作记录</label>

                        <table class="col-sm-10 table table-bordered" id="remarks">
                            <thead>
                            <td>编号</td>
                            <td>投诉原因</td>
                            <td>催单说明</td>
                            <td>操作时间</td>
                            </thead>
                            <tr v-for="(index, remark) in remarks">
                                <td>{{$index+1}}</td>
                                <td>{{remark.complaintType.name}}</td>
                                <td>{{remark.remark}}</td>
                                <td>{{remark.operationDate}}</td>
                            </tr>
                        </table>

                        <div class="modal-center">
                            <div class="m-t-lg " style="text-align: center">
                                <button  type="button" class="btn btn-primary"
                                         data-dismiss="modal" onclick="window.history.go(-1)">返回
                                </button>
                            </div>
                        </div>
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

<!-- template 应该永远比 script靠前 -->
<%@include file="/WEB-INF/views/admin/component/breadcrumb.jsp" %>
<script src="/static/admin/js/mixins.js"></script>
<script src="/static/admin/js/filters.js"></script>
<script src="/static/admin/js/components/breadcrumb.js"></script>



<!-- 主方法，每页均需引用 -->
<script src="/static/admin/js/main.js"></script>
<script src="/static/admin/js/sale/workorder/remaindList.js"></script>
</body>
</html>