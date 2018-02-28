<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>转派</title>
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
                    <div>
                        <Tabs type="card">
                            <Tab-pane label="转派">
                                <div class="row">
                                    <validator name="validation">
                                        <form id="searchForm" class="form-horizontal" @submit.prevent="query">

                                            <div style="margin-left: 25%;margin-top: 3%" class="form-group" :class="{'has-error':$validation.department.invalid && $validation.touched}">
                                                <label for="department" class="col-sm-2 control-label">供应商</label>
                                                <div class="col-sm-4">
                                                    <select v-model="department"
                                                            v-validate:department="{
                                                                required:{rule:true,message:'请选择供应商'}
                                                            }"
                                                            id="department"
                                                            name="department"
                                                            placeholder="选择供应商"
                                                            class="form-control"
                                                            @change="getDeptName">
                                                        <option value="" selected>--选择供应商--</option>
                                                        <option v-for="department in departments" value="{{department.id}}">{{department.orgName}}</option>
                                                    </select>
                                                    <span v-cloak v-if="$validation.department.invalid && $validation.touched" class="help-absolute">
                                                        <span v-for="error in $validation.department.errors">
                                                          {{error.message}} {{($index !== ($validation.department.errors.length -1)) ? ',':''}}
                                                        </span>
                                                    </span>
                                                </div>
                                            </div>


                                            <div class="modal-center">
                                                <button @click="submitClickHandler" :disabled="submitting" type="button"
                                                        class="btn btn-primary">提交
                                                </button>
                                                <button :disabled="submitting" type="button"
                                                        data-dismiss="modal" class="btn" @click="cancel">取消
                                                </button>
                                            </div>
                                        </form>
                                    </validator>
                                </div>
                            </Tab-pane>
                            <Tab-pane label="工单信息">
                                <%@include file="/include/workorder/customerInfo.jsp" %>
                            </Tab-pane>
                            <Tab-pane label="处理记录">
                                <%@include file="/include/workorder/excuteInfo.jsp" %>
                            </Tab-pane>
                            <Tab-pane label="上传记录">
                                <%@include file="/include/workorder/uploadInfo.jsp" %>
                            </Tab-pane>
                        </Tabs>
                    </div>
                    <div>
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
<script src="/static/admin/js/components/tab.js"></script>


<!-- 主方法，每页均需引用 -->
<script src="/static/admin/js/main.js"></script>
<script src="/static/admin/js/sale/workorder/turnSend.js?version=1.0"></script>
</body>
</html>