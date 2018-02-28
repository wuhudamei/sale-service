<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>工单列表</title>
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
            <div class="ibox">
                <div class="ibox-content">
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
                                    <select v-model="form.questionType1"
                                            id="questionType1"
                                            name="questionType1"
                                            placeholder="选择事项分类"
                                            class="form-control">

                                        <option value="">事项分类</option>
                                        <option v-for="pro in problemCategories" :value="pro.id">{{pro.name}}</option>
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

                                        <option value="">投诉原因</option>
                                        <option v-for="complaintType in complaintTypes" :value="complaintType.id">
                                            {{complaintType.name}}
                                        </option>
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

                                        <option value="">重要程度</option>
                                        <option v-for="importantDegree1 in importances" :value="importantDegree1.id">
                                            {{importantDegree1.name}}
                                        </option>
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

                        <div class="row">
                            <div v-if="form.status=='CREATE' || form.status=='RECEIVED'|| form.status=='PROCESSING' || form.status=='URGE'"
                                 class="col-md-2">
                                <div class="form-group">
                                    <label for="fenpaiDate" style="line-height: 34px;cursor:pointer;">
                                        <input
                                                v-model="form.fenpaiDate"
                                                id="fenpaiDate"
                                                name="fenpaiDate" type="checkbox"/>
                                        分派时间
                                    </label>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label for="customerFeedbackTime" style="line-height: 34px;;cursor:pointer;">
                                        <input
                                                v-model="form.customerFeedbackTime"
                                                id="customerFeedbackTime"
                                                name="customerFeedbackTime"
                                                type="checkbox"/>
                                        客户要求回电时间
                                    </label>
                                </div>
                            </div>
                            <div v-if="form.status=='ASSIGN' " class="col-md-2">
                                <div class="form-group">
                                    <label for="refuseDate" style="line-height: 34px;;cursor:pointer;">
                                        <input
                                                v-model="form.refuseDate"
                                                id="refuseDate"
                                                name="refuseDate"
                                                type="checkbox"
                                        />
                                        申诉时间
                                    </label>
                                </div>
                            </div>
                            <div v-if="form.status=='REFUSEDAGAIN' " class="col-md-2">
                                <div class="form-group">
                                    <label for="refusedagainDate" style="line-height: 34px;;cursor:pointer;">
                                        <input
                                                v-model="form.refusedagainDate"
                                                id="refusedagainDate"
                                                name="refusedagainDate"
                                                type="checkbox"
                                        />
                                        转派时间
                                    </label>
                                </div>
                            </div>
                            <div v-if="form.status=='PROCESSING' " class="col-md-2">
                                <div class="form-group">
                                    <label for="expectDate" style="line-height: 34px;;cursor:pointer;">
                                        <input
                                                v-model="form.expectDate"
                                                id="expectDate"
                                                name="expectDate"
                                                type="checkbox"
                                        />
                                        预计完成时间
                                    </label>
                                </div>
                            </div>

                            <div v-if="form.status=='ASSIGN'" class="col-md-2">
                                <div class="form-group">
                                    <label for="faqiDate" style="line-height: 34px;;cursor:pointer;">
                                        <input
                                                v-model="form.faqiDate"
                                                id="faqiDate"
                                                name="faqiDate"
                                                type="checkbox"/>
                                        发起时间
                                    </label>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group clearfix">
                                    <input v-el:start-date
                                           id="startDate"
                                           maxlength="20"
                                           readonly
                                           data-tabindex="1"
                                           name="startDate" type="text" class="form-control datepicker"
                                           type="text" class="form-control" v-model="form.startDate"
                                           placeholder="开始日期">
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group clearfix">
                                    <input v-el:end-date
                                           id="endDate"
                                           maxlength="20"
                                           readonly
                                           data-tabindex="1"
                                           name="endDate" type="text" class="form-control datepicker"
                                           type="text" class="form-control" v-model="form.endDate"
                                           placeholder="结束日期">
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

    <!--右侧部分结束-->
</div>


<!-- 处理-->
<div id="orderModel" class="modal fade" tabindex="-1" data-width="800">
    <validator name="validation">
        <form name="createMirror" novalidate class="form-horizontal" role="form">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3>工单操作</h3>
            </div>
            <div class="modal-body" v-if="flag == 1"><!-- 处理动作 -->
                <div class="form-group">
                    <label class="col-sm-3 control-label">姓名</label>
                    <div class="col-sm-8">
                        <input v-model="order.customerName"
                               maxlength="20"
                               data-tabindex="1"
                               type="text" readonly class="form-control" placeholder="姓名">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">电话</label>
                    <div class="col-sm-8">
                        <input v-model="order.customerMobile"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="电话">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">地址</label>
                    <div class="col-sm-8">
                        <input v-model="order.customerAddress"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="地址">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">合同开始时间</label>
                    <div class="col-sm-8">
                        <input v-model="order.contractStartTime"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="合同开始时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">合同竣工时间</label>
                    <div class="col-sm-8">
                        <input v-model="order.contractCompleteTime"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text"
                               class="form-control" placeholder="合同竣工时间">
                    </div>
                </div>
                <div class="form-group">
                    <label for="problem" class="col-sm-3 control-label">问题</label>
                    <div class="col-sm-8">
                      <textarea
                              v-model="order.problem"
                              maxlength="500"
                              class="form-control"
                              readonly
                              placeholder="问题"></textarea>
                    </div>
                </div>
                <div class="form-group"
                     :class="{'has-error':($validation.treamenttime.invalid && $validation.touched)}">
                    <label for="treamentTime" class="col-sm-3 control-label">完成时间</label>
                    <div class="col-sm-8">
                        <input
                                v-validate:treamenttime="{required:true}"
                                v-model="form.treamentTime"
                                v-el:treament-time
                                id="treamentTime"
                                name="treamentTime"
                                type="text"
                                readonly
                                class="form-control datepicker"
                                placeholder="请选择完成时间">
                        <div v-if="$validation.treamenttime.invalid && $validation.touched"
                             class="help-absolute">
                            <span v-if="$validation.treamenttime.invalid">请选择完成时间</span>
                        </div>
                    </div>
                </div>
                <div class="form-group" :class="{'has-error':$validation.treamentplan.invalid && $validation.touched}">
                    <label for="treamentPlan" class="col-sm-3 control-label">处理方案</label>
                    <div class="col-sm-8">
                    <textarea v-validate:treamentplan="{required:true}"
                              v-model="form.treamentPlan"
                              maxlength="500"
                              id="treamentPlan"
                              name="treamentPlan"
                              class="form-control"
                              placeholder="请输入处理方案"></textarea>
                        <div v-if="$validation.treamentplan.invalid && $validation.touched"
                             class="help-absolute">
                            <span v-if="$validation.treamentplan.invalid">请输入处理方案</span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="brand" class="col-sm-3 control-label">品牌</label>
                    <div class="col-sm-8">
                        <input
                                v-model="form.brand"
                                maxlength="20"
                                id="brand"
                                data-tabindex="1"
                                type="text" class="form-control"
                                placeholder="品牌">
                    </div>
                </div>

            </div>
            <div class="modal-body" v-if="flag == 2"><!-- 回复动作 -->
                <div class="form-group">
                    <label class="col-sm-3 control-label">姓名</label>
                    <div class="col-sm-8">
                        <input v-model="order.customerName"
                               maxlength="20"
                               data-tabindex="1"
                               type="text" readonly class="form-control" placeholder="姓名">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">电话</label>
                    <div class="col-sm-8">
                        <input v-model="order.customerMobile"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="电话">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">地址</label>
                    <div class="col-sm-8">
                        <input v-model="order.customerAddress"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="地址">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">合同开始时间</label>
                    <div class="col-sm-8">
                        <input v-model="order.contractStartTime"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="合同开始时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">合同竣工时间</label>
                    <div class="col-sm-8">
                        <input v-model="order.contractCompleteTime"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text"
                               class="form-control" placeholder="合同竣工时间">
                    </div>
                </div>
                <div class="form-group">
                    <label for="problem" class="col-sm-3 control-label">问题</label>
                    <div class="col-sm-8">
                      <textarea
                              v-model="order.problem"
                              maxlength="500"
                              class="form-control"
                              readonly
                              placeholder="问题"></textarea>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">处理方案</label>
                    <div class="col-sm-8">
                        <input v-model="order.treamentPlan"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="电话">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">完成时间</label>
                    <div class="col-sm-8">
                        <input v-model="order.treamentTime"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="完成时间">
                    </div>
                </div>
                <div class="form-group"
                     :class="{'has-error':($validation.feedbacktime.invalid && $validation.touched)}">
                    <label for="feedbackTime" class="col-sm-3 control-label">回复时间</label>
                    <div class="col-sm-8">
                        <input
                                v-validate:feedbacktime="{required:true}"
                                v-model="form.feedbackTime"
                                v-el:feedback-time
                                id="feedbackTime"
                                name="feedbackTime"
                                type="text"
                                readonly
                                class="form-control datepicker"
                                placeholder="请选择回复时间">
                        <div v-if="$validation.feedbacktime.invalid && $validation.touched"
                             class="help-absolute">
                            <span v-if="$validation.feedbacktime.invalid">请选择回复时间</span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="feedbackRmk" class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-8">
                    <textarea
                            v-model="form.feedbackRmk"
                            maxlength="500"
                            id="feedbackRmk"
                            name="feedbackRmk"
                            class="form-control"
                            placeholder="请输入回复内容或者申诉理由"></textarea>
                    </div>
                </div>
            </div>
            <div class="modal-body" v-if="flag == 3">
                <div class="form-group">
                    <label class="col-sm-3 control-label">姓名</label>
                    <div class="col-sm-8">
                        <input v-model="order.customerName"
                               maxlength="20"
                               data-tabindex="1"
                               type="text" readonly class="form-control" placeholder="姓名">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">电话</label>
                    <div class="col-sm-8">
                        <input v-model="order.customerMobile"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="电话">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">地址</label>
                    <div class="col-sm-8">
                        <input v-model="order.customerAddress"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="地址">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">合同开始时间</label>
                    <div class="col-sm-8">
                        <input v-model="order.contractStartTime"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="合同开始时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">合同竣工时间</label>
                    <div class="col-sm-8">
                        <input v-model="order.contractCompleteTime"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text"
                               class="form-control" placeholder="合同竣工时间">
                    </div>
                </div>
                <div class="form-group">
                    <label for="problem" class="col-sm-3 control-label">问题</label>
                    <div class="col-sm-8">
                      <textarea
                              v-model="order.problem"
                              maxlength="500"
                              class="form-control"
                              readonly
                              placeholder="问题"></textarea>
                    </div>
                </div><!-- 回访动作 -->
                <div class="form-group" :class="{'has-error':$validation.visitresult.invalid && $validation.touched}">
                    <label for="visitResult" class="col-sm-3 control-label">回访结果</label>
                    <div class="col-sm-8">
                    <textarea
                            v-validate:visitresult="{required:true}"
                            v-model="form.visitResult"
                            maxlength="500"
                            id="visitResult"
                            name="visitResult"
                            class="form-control"
                            placeholder="请输入回访结果"></textarea>
                        <div v-if="$validation.visitresult.invalid && $validation.touched"
                             class="help-absolute">
                            <span v-if="$validation.visitresult.invalid">请输入回访结果</span>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="importantDegree1" class="col-sm-3 control-label">工单状态</label>
                    <div class="col-sm-8">
                        <select
                                v-model="form.orderStatus"
                                id="orderStatus"
                                name="orderStatus"
                                class="form-control">
                            <option value="COMPLETED" selected>已解决</option>
                            <option value="UNEXECUTED">回访未执行</option>
                            <option value="CALLBACK">再联系</option>
                        </select>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button :disabled="submitting" type="button"
                        data-dismiss="modal" class="btn" @click="cancel">取消
                </button>
                <button v-if="flag == 1||flag==2" @click="rejectClickHandler" :disabled="submitting" type="button"
                        class="btn btn-danger">申诉
                </button>
                <button @click="submitClickHandler" :disabled="submitting" type="button" class="btn btn-primary">提交
                </button>
            </div>
        </form>
    </validator>
</div>

<!-- 编辑备注-->
<div id="remarkModel" class="modal fade" tabindex="-1" data-width="500">
    <validator name="validation">
        <form name="createMirror" novalidate class="form-horizontal" role="form">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3>工单备注</h3>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-3 control-label">姓名</label>
                    <div class="col-sm-8">
                        <input v-model="order.customerName"
                               maxlength="20"
                               data-tabindex="1"
                               type="text" readonly class="form-control" placeholder="姓名">
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label">电话</label>
                    <div class="col-sm-8">
                        <input v-model="order.customerMobile"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="电话">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">地址</label>
                    <div class="col-sm-8">
                        <input v-model="order.customerAddress"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="地址">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">合同开始时间</label>
                    <div class="col-sm-8">
                        <input v-model="order.contractStartTime"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text" class="form-control"
                               placeholder="合同开始时间">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label">合同竣工时间</label>
                    <div class="col-sm-8">
                        <input v-model="order.contractCompleteTime"
                               maxlength="20"
                               data-tabindex="1"
                               readonly type="text"
                               class="form-control" placeholder="合同竣工时间">
                    </div>
                </div>
                <div class="form-group">
                    <label for="problem" class="col-sm-3 control-label">问题</label>
                    <div class="col-sm-8">
                      <textarea
                              v-model="order.problem"
                              maxlength="500"
                              class="form-control"
                              readonly
                              placeholder="问题"></textarea>
                    </div>
                </div>
                <div class="form-group" :class="{'has-error':$validation.remark.invalid && $validation.touched}">
                    <label for="remark" class="col-sm-3 control-label">备注</label>
                    <div class="col-sm-8">
                    <textarea rows="4"
                              v-validate:remark="{required:true}"
                              v-model="form.remark"
                              maxlength="500"
                              id="remark"
                              name="remark"
                              class="form-control"
                              placeholder="请输入备注"></textarea>
                        <div v-if="$validation.remark.invalid && $validation.touched"
                             class="help-absolute">
                            <span v-if="$validation.remark.invalid">请输入备注</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
                <button @click="submitClickHandler" :disabled="submitting" type="button" class="btn btn-primary">提交
                </button>
            </div>
        </form>
    </validator>
</div>

<!-- 合同列表 -->
<div id="mdnOrder" class="modal fade" tabindex="-1" data-width="1000">
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="searchForm" action='#'>
                    <div class="col-md-4">
                        <div class="form-group">
                            <input v-model="form.keyword"
                                   type="text"
                                   placeholder="电话号/客户姓名" class="form-control"/>
                        </div>
                    </div>
                    <div class="col-md-3 text-right">
                        <div class="form-group">
                            <button id="searchBtn" type="button" @click.prevent.stop="query"
                                    class="btn btn-block btn-outline btn-default" alt="搜索"
                                    title="搜索">
                                <i class="fa fa-search"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <!-- <div class="columns columns-right btn-group pull-right"></div> -->
            <table id="dataTable" width="100%" class="table table-striped table-bordered table-hover">
            </table>

            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn">关闭</button>
                <button type="button" @click="commitUsers" class="btn btn-primary">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 合同列表 -->
<div id="customerDiv" class="modal fade" tabindex="-1" data-width="1000">
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="searchForm" action='#'>
                    <div class="col-md-3">
                        <div class="form-group">
                            <select
                                    v-model="form.cusCompany"
                                    id="cusCompany"
                                    name="cusCompany"
                                    class="form-control">
                                <option value="">选择门店</option>
                                <option v-if="company.parentId!=0" v-for="company in companies" :value="company.id">
                                    {{company.orgName}}
                                </option>
                            </select>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="form-group">
                            <input v-model="form.keyword"
                                   type="text"
                                   placeholder="姓名/电话号" class="form-control"/>
                        </div>
                    </div>

                    <div class="col-md-3 text-right">
                        <div class="form-group">
                            <button id="searchBtn" type="button" @click.prevent.stop="query"
                                    class="btn btn-block btn-outline btn-default" alt="搜索"
                                    title="搜索">
                                <i class="fa fa-search"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <!-- <div class="columns columns-right btn-group pull-right"></div> -->
            <table id="dataTable" width="100%" class="table table-striped table-bordered table-hover">
            </table>

            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn">关闭</button>
                <button type="button" @click="commitCustomer" class="btn btn-primary">确定</button>
            </div>
        </div>
    </div>
</div>
<!-- 责任人 -->
<div id="liablePersonModel" class="modal fade" tabindex="-1" data-width="800">
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="searchForm" @submit.prevent="query">
                    <div class="col-md-4">
                        <div class="form-group">
                            <input v-model="form.keyword"
                                   type="text"
                                   placeholder="工号、姓名" class="form-control"/>
                        </div>
                    </div>
                    <div class="col-md-3 text-right">
                        <div class="form-group">
                            <button id="searchBtn" type="submit" class="btn btn-block btn-outline btn-default" alt="搜索"
                                    title="搜索">
                                <i class="fa fa-search"></i>
                            </button>
                        </div>
                    </div>
                </form>
            </div>
            <table id="dataTable" width="100%" class="table table-striped table-bordered table-hover">
            </table>
            <div class="modal-footer">
                <button type="button" data-dismiss="modal" class="btn">关闭</button>
                <button type="button" @click="commitUsers" class="btn btn-primary">确定</button>
            </div>
        </div>
    </div>
</div>

<!--申请延期-->
<div id="remarkTime" class="modal fade" tabindex="-1"  data-width="800">
    <!-- ibox start -->
    <div class="modal-header" style="text-align: center;font-size: larger;font-weight: 600">
       申请延期
    </div>
    <div class="modal-boday">
        <validator name="validation">
                <form name="createMirror"  class="form-horizontal" role="form" >
                    <div class="form-group" :class="{'has-error':$validation.oldTime.invalid && $validation.touched}">
                        <label class="col-sm-3 control-label">原先预计时间</label>
                        <div class="col-sm-8">
                            <input v-model="oldTime"
                                   maxlength="20"
                                   data-tabindex="1"
                                   id="oldTime"
                                   v-el:old-time
                                   name="oldTime"
                                   readonly type="text"
                                   class="form-control datepicker"
                                   placeholder="原先预计时间">
                            <div v-if="$validation.oldTime.invalid && $validation.touched"
                                 class="help-absolute">
                                <span v-if="$validation.oldTime.invalid"></span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group" :class="{'has-error':$validation.newTime.invalid && $validation.touched}">
                        <label class="col-sm-3 control-label">延期预计时间</label>
                        <div class="col-sm-8">
                            <input v-model="newTime"
                                   v-validate:new-time="{required:true}"
                                   id="newTime"
                                   name="newTime" type="text"
                                   class="form-control datepicker"
                                   maxlength="20"
                                   v-el:new-time
                                   data-tabindex="1"
                                   readonly type="text"
                                   placeholder="延期预计时间">
                            <div v-if="$validation.newTime.invalid && $validation.touched"
                                 class="help-absolute">
                                <span v-if="$validation.newTime.invalid">请选择延期预计时间</span>
                            </div>
                        </div>
                    </div>

                    <div class="form-group" :class="{'has-error':$validation.remarks.invalid && $validation.touched}">
                        <label  class="col-sm-3 control-label">延期备注(原因)</label>
                        <div class="col-sm-8">
                    <textarea rows="4"
                              v-validate:remarks="{required:true}"
                              v-model="remarks"
                              maxlength="500"
                              name="remarks"
                              class="form-control"
                              placeholder="请输入延期备注"></textarea>
                            <div v-if="$validation.remarks.invalid && $validation.touched"
                                 class="help-absolute">
                                <span v-if="$validation.remarks.invalid">请输入延期备注</span>
                            </div>
                        </div>
                    </div>
                </form>
        </validator>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn">关闭</button>
        <button type="button" @click.prevent="submitapproval" class="btn btn-primary">确定</button>
    </div>
</div>

<!--驳回记录-->
<div id="back" class="modal fade" tabindex="-1"  >
    <!-- ibox start -->
    <div class="modal-header" style="text-align: center;font-size: larger;font-weight: 600">
        驳回记录
    </div>
    <div class="modal-boday">
        <div style="padding: 10px 10px 10px">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                    <th style="width: 15%">审批结果</th>
                    <th style="width: 15%">审批人</th>
                    <th style="width: 20%">审批时间</th>
                    <th>审批备注</th>
                </thead>
                <tbody>
                    <tr v-if="result!=null">
                        <td>{{{result.approvalResult| result}}}</td>
                        <td>{{result.name}}</td>
                        <td>{{result.createDate}}</td>
                        <td>{{result.remarks}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </div>
    <div class="modal-footer">
        <button type="button" data-dismiss="modal" class="btn">关闭</button>
    </div>
</div>

<script src="/static/admin/vendor/webuploader/webuploader.js"></script>
<script src="/static/admin/js/components/webuploader.js"></script>

<!-- template 应该永远比 script靠前 -->
<%@include file="/WEB-INF/views/admin/component/breadcrumb.jsp" %>
<script src="/static/admin/js/mixins.js"></script>
<script src="/static/admin/js/filters.js"></script>
<script src="/static/admin/js/components/breadcrumb.js"></script>
<%@include file="/WEB-INF/views/admin/component/ztree.jsp" %>
<script src="/static/admin/js/components/ztree.js"></script>
<script src="/static/admin/js/jquery.ztree.core.js"></script>
<script src="/static/admin/js/jquery.ztree.excheck.js"></script>

<!-- 主方法，每页均需引用 -->
<script src="/static/admin/js/main.js"></script>
<script src="/static/admin/js/sale/workorder/list.js?ver=1.0"></script>
</body>
</html>