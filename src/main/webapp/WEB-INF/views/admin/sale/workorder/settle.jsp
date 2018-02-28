<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>工单处理</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <%@include file="/include/admin/head.jsp" %>
    <link rel="stylesheet" href="/static/css/tab.css">
    <link rel="stylesheet" href="/static/hplus/css/plugins/jsTree/themes/default/style.min.css"/>
    <link href="/static/hplus/js/plugins/layer/skin/layer.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/admin/vendor/webuploader/webuploader.css">
    <style>
        .webuploader-pick{
            background: white
        }
    </style>
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
            <!-- ibox start -->
            <div class="ibox">
                <div class="ibox-content">
                    <div>
                        <Tabs type="card">
                            <Tab-pane label="处理">
                                <div class="row" >
                                    <validator name="validation">
                                        <form id="searchForm" class="form-horizontal" @submit.prevent="query">
                                            <div class="form-group"
                                                 :class="{'has-error':$validation.treamentplan.invalid && $validation.touched}">
                                                <div class="form-group">
                                                    <label for="treamentPlan"
                                                           class="col-sm-3 control-label">处理方案或申诉理由</label>
                                                    <div class="col-sm-7">
                                                <textarea v-validate:treamentplan="{required:true}"
                                                          v-model="treamentPlan"
                                                          maxlength="500"
                                                          id="treamentPlan"
                                                          name="treamentPlan"
                                                          class="form-control"
                                                          placeholder="请输入处理方案或申诉理由"></textarea>
                                                        <div v-if="$validation.treamentplan.invalid && $validation.touched"
                                                             class="help-absolute">
                                                            <span v-if="$validation.treamentplan.invalid">请输入处理方案或申诉理由</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="form-group">

                                                <div class="row">
                                                    <label class="col-sm-3 control-label">预计完成时间</label>
                                                    <div class="col-sm-3">
                                                        <div class="clearfix"
                                                             :class="{'has-error':($validation.treamenttime.invalid && $validation.touched)}">
                                                            <input
                                                                    v-model="treamentTime"
                                                                    v-el:treament-time
                                                                    id="treamentTime"
                                                                    name="treamentTime"
                                                                    type="text"
                                                                    readonly
                                                                    class="form-control datepicker"
                                                                    :placeholder="placeholder">
                                                            <div v-if="$validation.treamenttime.invalid && $validation.touched"
                                                                 class="help-absolute">
                                                                <span v-if="$validation.treamenttime.invalid">请选择完成时间</span>
                                                            </div>
                                                        </div>
                                                    </div>


                                                    <label for="brand" class="col-sm-1 control-label">品牌</label>
                                                    <div class="col-sm-3">
                                                        <select
                                                                v-model="brand"
                                                                id="brand"
                                                                name="brand"
                                                                class="form-control">
                                                            <option value="0">请选择品牌</option>
                                                            <option v-for="brand in brands" :value="brand.id">
                                                                {{brand.name}}
                                                            </option>
                                                        </select>
                                                    </div>

                                                </div>
                                            </div>

                                            <div class="form-group">

                                                <div class="row">

                                                    <label for="treamentTime" class="col-sm-2 control-label"></label>
                                                    <div class="col-sm-4" style="color:red">
                                                        此事项最大完成时限: &nbsp;{{duration}}&nbsp;天
                                                    </div>

                                                    <label for="brand" class="col-sm-1 control-label"></label>
                                                    <div class="col-sm-4" style="color:red">
                                                        材料部、好得很部、小定制部、木门事业部必须确认品牌!
                                                    </div>

                                                </div>
                                            </div>

                                            <div class="row form-group" v-if="photoLinks.length>0">
                                                <label class="col-sm-2 control-label"></label>
                                                <div class="col-sm-7">
                                                    <ul>
                                                        <li v-for="link in photoLinks" style="float: left;list-style-type:none;">
                                                            <a v-if="link.a != ''" :href="link.a" target="_blank">
                                                                <img :src="link.a" alt="" style="width: 105px;margin-left: 60px">
                                                            </a>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>

                                            <div class="form-group">
                                                <label class="col-sm-2 control-label"></label>
                                                <div class="col-sm-4">
                                                    <web-uploader
                                                            :w-multiple="true"
                                                            :type="webUploaderSub.type"
                                                            :w-server="webUploaderSub.server"
                                                            :w-accept="webUploaderSub.accept"
                                                            :w-file-size-limit="webUploaderSub.fileSizeLimit"
                                                            :w-file-single-size-limit="webUploaderSub.fileSingleSizeLimit"
                                                            :w-form-data="{category:'WORK_ORDER_PHOTO'}">
                                                        <button type="button" class="btn btn-warning">上传图片</button>
                                                    </web-uploader>
                                                </div>
                                                <div class="col-sm-3">
                                                    <button @click="deleteFlie()"
                                                            v-if="photo" type="button"
                                                            class="btn btn-sm btn-danger">
                                                        删除附件
                                                    </button>
                                                </div>
                                            </div>

                                            <div class="modal-center">
                                                <button @click="submitClickHandler" :disabled="submitting" type="button"
                                                        class="btn btn-primary">提交
                                                </button>
                                                <button v-if="showRejectBtn" @click="rejectClickHandler" :disabled="submitting" type="button"
                                                class="btn btn-danger">申诉
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
<script src="/static/admin/vendor/webuploader/webuploader.js"></script>
<script src="/static/admin/js/components/webuploader.js"></script>

<!-- template 应该永远比 script靠前 -->
<%@include file="/WEB-INF/views/admin/component/breadcrumb.jsp" %>
<script src="/static/admin/js/mixins.js"></script>
<script src="/static/admin/js/filters.js"></script>
<script src="/static/admin/js/components/breadcrumb.js"></script>
<script src="/static/admin/js/components/tab.js"></script>


<!-- 主方法，每页均需引用 -->
<script src="/static/admin/js/main.js"></script>
<script src="/static/admin/js/sale/workorder/settle.js?version=1.0"></script>
</body>
</html>