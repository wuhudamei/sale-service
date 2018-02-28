<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>延期申核</title>
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
                            <Tab-pane label="延期申核" style="padding-left: 35px" >

                                <div class="row">
                                    <h3 >延期详情</h3>
                                    <hr/>
                                    <p><span><strong>申请姓名：</strong></span><span>{{approval.name}}</span></p>
                                    <p><span><strong>预计时间：</strong></span><span>{{approval.oldTime}}</span></p>
                                    <p><span><strong>延期时间：</strong></span><span>{{approval.newTime}}</span></p>
                                    <p><span><strong>延期说明：</strong></span><span>{{approval.remarks}}</span></p>
                                    <hr/>
                                </div>

                                <div class="row">
                                    <validator name="validation">
                                        <form id="searchForm" class="form-horizontal" >
                                            <h3 >审核详情</h3>
                                            <hr/>
                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">审核结果：</label>
                                                <div class="col-sm-7">
                                                    <label class="radio-inline">
                                                        <input type="radio" name="inlineRadioOptions" id="inlineRadio1" v-model="result.approvalResult" value="1"> 通过
                                                    </label>
                                                    <label class="radio-inline">
                                                        <input type="radio" name="inlineRadioOptions" id="inlineRadio2"  v-model="result.approvalResult" value="2"> 驳回
                                                    </label>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-3 control-label">审核说明：</label>
                                                <div class="col-sm-7">
                                                    <textarea v-model="result.remarks"
                                                              maxlength="500"
                                                              id="remarks"
                                                              name="remarks"
                                                              class="form-control">

                                                    </textarea>
                                                </div>
                                            </div>



                                            <div class="modal-center">
                                                <button @click="submitClickHandler" :disabled="submitting" type="button"
                                                class="btn btn-primary">提交
                                                </button>
                                                &nbsp;
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
<script src="/static/admin/js/sale/workorder/approval.js"></script>
</body>
</html>