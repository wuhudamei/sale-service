<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>工单分配</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <%@include file="/include/admin/head.jsp" %>
    <link rel="stylesheet" href="/static/css/tab.css">
    <link href="${ctx}/static/admin/css/zTreeStyle.css" rel="stylesheet">
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

                    <Tabs type="card">
                        <Tab-pane label="分配">
                            <validator name="validation">
                                <div class="form-horizontal">

                                    <div class="row">

                                        <div class="form-group">
                                            <label class="col-sm-2 control-label">工单分类</label>
                                            <div class="col-sm-3">
                                                <select v-model="form.workType"
                                                        name="order.workType"
                                                        class="form-control" >
                                                    <option value="PRESALE">售前</option>
                                                    <option value="SELLING">售中</option>
                                                    <option value="AFTERSALE">售后</option>
                                                </select>
                                            </div>

                                            <div v-clickoutside="clickOut"
                                                 :class="{'has-error':$validation.liabledepartmentname.invalid && $validation.touched}">
                                                <label for="liableDepartmentName"
                                                       class="col-sm-2 control-label">责任部门</label>
                                                <div class="col-sm-3">
                                                    <input v-model="liableDepartmentName"
                                                           v-validate:liabledepartmentname="{ required:{rule:true,message:'请选择责任部门'} }"
                                                           maxlength="20"
                                                           data-tabindex="1"
                                                           id="liableDepartmentName" name="liableDepartmentName"
                                                           readonly type="text"
                                                           class="form-control" placeholder="责任部门"
                                                           @click="showOrgTree=true">
                                                    <div v-if="$validation.liabledepartmentname.invalid && $validation.touched"
                                                         class="help-absolute">
                                                        <span v-if="$validation.liabledepartmentname.invalid">请选择责任部门</span>
                                                    </div>
                                                    <z-tree v-ref:nodes-select
                                                            :nodes="orgData"
                                                            :show-tree.sync="showOrgTree"
                                                            @on-click="selectOrg"
                                                            :show-checkbox="false">
                                                    </z-tree>
                                                </div>
                                            </div>
                                        </div>

                                        <input v-model="form.liableDepartment"
                                               id="liableDepartment" name="liableDepartment" type="hidden"
                                               class="form-control"/>

                                    </div>


                                    <div class="row">
                                        <div class="form-group">
                                            <div :class="{'has-error':$validation.questiontype1.invalid && $validation.touched}">
                                                <label for="questionType1"
                                                       class="col-sm-2 control-label">事项分类</label>
                                                <div class="col-sm-3">
                                                    <select
                                                            v-validate:questiontype1="{required:true}"
                                                            v-model="form.questionType1"
                                                            id="questiontype1"
                                                            name="questionType1"
                                                            class="form-control">
                                                        <option value="">事项分类</option>
                                                        <option v-for="questionType1 in questionType1s"
                                                                :value="questionType1.id">{{questionType1.name}}
                                                        </option>
                                                    </select>
                                                    <div v-if="$validation.questiontype1.invalid && $validation.touched"
                                                         class="help-absolute">
                                                        <span v-if="$validation.questiontype1.invalid">请选择事项分类</span>
                                                    </div>
                                                </div>
                                            </div>

                                            <div :class="{'has-error':$validation.questiontype2.invalid && $validation.touched}">
                                                <label for="questionType2"
                                                       class="col-sm-2 control-label">问题类型</label>
                                                <div class="col-sm-3">
                                                    <select
                                                            v-validate:questiontype2="{required:true}"
                                                            v-model="form.questionType2"
                                                            id="questionType2"
                                                            name="questionType2"
                                                            class="form-control">
                                                        <option value="">问题类型</option>
                                                        <option v-for="questionType2 in questionType2s"
                                                                :value="questionType2.id">{{questionType2.name}}
                                                        </option>
                                                    </select>
                                                    <div v-if="$validation.questiontype2.invalid && $validation.touched"
                                                         class="help-absolute">
                                                        <span v-if="$validation.questiontype2.invalid">请选择问题类型</span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">问题</label>
                                                <div class="col-sm-8">
                                                     <textarea
                                                         v-model="form.problem"
                                                         maxlength="500"
                                                         class="form-control"
                                                         rows="4">
                                                     </textarea>
                                                </div>
                                            </div>
                                        </div>

                                        <div class="row">
                                            <div class="form-group">
                                                <label class="col-sm-2 control-label">分配意见</label>
                                                <div class="col-sm-8">
                                                    <textarea
                                                        v-model="form.suggestion"
                                                        maxlength="500"
                                                        class="form-control"
                                                        rows="3">
                                                    </textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>

                            </validator>
                            <div class="modal-center">
                                <button :disabled="submitting" type="button"
                                        data-dismiss="modal" class="btn" @click="cancel">取消</button>
                                <button @click="rejectClickHandler" :disabled="submitting" type="button"
                                        class="btn btn-danger">申诉无效
                                </button>
                                <button @click="submitClickHandler" :disabled="submitting" type="button"
                                        class="btn btn-primary">提交
                                </button>
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
<%@include file="/WEB-INF/views/admin/component/ztree.jsp" %>
<script src="/static/admin/js/components/ztree.js"></script>
<script src="/static/admin/js/jquery.ztree.core.js"></script>
<script src="/static/admin/js/jquery.ztree.excheck.js"></script>

<!-- 主方法，每页均需引用 -->
<script src="/static/admin/js/main.js"></script>
<script src="/static/admin/js/sale/workorder/edit.js"></script>
</body>
</html>