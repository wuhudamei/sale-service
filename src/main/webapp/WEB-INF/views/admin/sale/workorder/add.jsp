<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>发起工单</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <%@include file="/include/admin/head.jsp" %>
    <link href="${ctx}/static/admin/css/zTreeStyle.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/admin/vendor/webuploader/webuploader.css">
    <link rel="stylesheet" href="/static/hplus/css/plugins/jsTree/themes/default/style.min.css"/>
    <link href="/static/hplus/js/plugins/layer/skin/layer.css" rel="stylesheet">
    <%@include file="/include/admin/head.jsp" %>
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
        <div id="container" class="wrapper wrapper-content">
            <div id="breadcrumb">
                <bread-crumb :crumbs="breadcrumbs"></bread-crumb>
            </div>
            <!-- ibox start -->
            <div class="ibox" v-cloak>
                <div class="ibox-content">
                    <validator name="validation">
                        <div id="searchForm" @submit.prevent="query" class="form-horizontal" role="form">
                            <div class="form-group">
                                <div class="col-sm-4 control-label">
                                    <a @click="showOrderBtnClickHandler" href="javascript:;">查询合同+</a>
                                </div>
                                <div class="col-sm-4 control-label">
                                    <a @click="showCustomerBtnClickHandler" href="javascript:;">查询本地客户+</a>
                                </div>
                            </div>

                            <input v-model="form.customerId" data-tabindex="1"
                                   id="customerId" name="customerId" type="hidden" readonly class="form-control">

                        <div  class="row form-group">
                            <div
                                 :class="{'has-error':$validation.customername.invalid && $validation.touched}">
                                <label for="customerName" class="col-sm-3 control-label">姓名</label>
                                <div class="col-sm-2">
                                    <input v-model="form.customerName"
                                           v-validate:customername="{required:true}"
                                           maxlength="20"
                                           data-tabindex="1"
                                           id="customerName" name="name" type="text" class="form-control"
                                           placeholder="姓名" :disabled="liabled"/>
                                    <div v-if="$validation.customername.invalid && $validation.touched"
                                         class="help-absolute">
                                        <span v-if="$validation.customername.invalid">请填写姓名</span>
                                    </div>
                                </div>
                            </div>
                            <div
                                 :class="{'has-error':$validation.phone.invalid && $validation.touched}">
                                <label for="customerMobile" class="col-sm-2 control-label">电话</label>
                                <div class="col-sm-2">
                                    <input v-model="form.customerMobile"
                                           v-validate:phone="{ required:{rule:true},
                                                                telphone:{rule:true,message:'请输入正确的手机号'}
                                                             }"
                                           minlength="11"
                                           maxlength="11"
                                           data-tabindex="1"
                                           id="customermobile" name="customermobile" class="form-control"
                                           placeholder="电话" :disabled="liabled">
                                    <span v-cloak v-if="$validation.phone.invalid && $validation.touched" class="help-absolute">
                                        <span v-for="error in $validation.phone.errors">
                                          {{error.message}} {{($index !== ($validation.phone.errors.length -1)) ? ',':''}}
                                        </span>
                                    </span>
                                </div>

                            </div>
                        </div>

                            <div class="form-group">
                                <label for="customerAddress" class="col-sm-3 control-label">地址</label>
                                <div class="col-sm-6">
                                    <input v-model="form.customerAddress"
                                           maxlength="20"
                                           data-tabindex="1"
                                           id="customerAddress" name="customerAddress" type="text" class="form-control"
                                           placeholder="地址" :disabled="liabled">
                                </div>
                            </div>

                            <div class="form-group row" v-show="form.contractStartTime || form.contractCompleteTime">
                                <label for="contractStartTime" class="col-sm-3 control-label">合同开始时间</label>
                                <div class="col-sm-2">
                                    <input v-model="form.contractStartTime"
                                           maxlength="20"
                                           data-tabindex="1"
                                           id="contractStartTime" name="contractStartTime" readonly type="text"
                                           class="form-control"
                                           placeholder="合同开始时间">
                                </div>
                                <label for="contractCompleteTime" class="col-sm-2 control-label">合同竣工时间</label>
                                <div class="col-sm-2">
                                    <input v-model="form.contractCompleteTime"
                                           maxlength="20"
                                           data-tabindex="1"
                                           id="contractCompleteTime" name="contractCompleteTime" readonly type="text"
                                           class="form-control" placeholder="合同竣工时间">
                                </div>
                            </div>

                            <div class="form-group row" v-show="form.actualStartTime || form.actualCompletionTime">
                                <label for="actualStartTime" class="col-sm-3 control-label">实际开工时间</label>
                                <div class="col-sm-2">
                                    <input v-model="form.actualStartTime"
                                           maxlength="20"
                                           data-tabindex="1"
                                           id="actualStartTime" name="actualStartTime" readonly type="text"
                                           class="form-control"
                                           placeholder="实际开工时间">
                                </div>

                                <label for="actualCompletionTime" class="col-sm-2 control-label">实际竣工时间</label>
                                <div class="col-sm-2">
                                    <input v-model="form.actualCompletionTime"
                                           maxlength="20"
                                           data-tabindex="1"
                                           id="actualCompletionTime" name="actualCompletionTime" readonly type="text"
                                           class="form-control"
                                           placeholder="实际竣工时间">
                                </div>
                            </div>

                            <div class="form-group row" v-show="form.paymentStage ">
                                <label for="paymentStage" class="col-sm-3 control-label">客户收款状态</label>
                                <div class="col-sm-2">
                                    <input v-model="form.paymentStage"
                                           maxlength="20"
                                           data-tabindex="1"
                                           id="paymentStage" name="paymentStage" readonly type="text"
                                           class="form-control" placeholder="客户收款状态">
                                </div>
                            </div>


                            <div class="form-group row">
                                <hr style=" border: dashed 1px; width: 56%; margin-right: 26%; color: grey"/>
                            </div>
                            <div class="row form-group">
                                <div v-if="user.departmentType=='GROUPCUSTOMERSERVICE'"
                                     :class="{'has-error':$validation.company.invalid && $validation.touched}">
                                    <label for="company" class="col-sm-3 control-label">门店</label>
                                    <div class="col-sm-2" >
                                        <select :disabled="liabled"
                                                v-validate:company="{required:true}"
                                                v-model="form.liableCompany"
                                                id="company"
                                                name="liableCompany"
                                                class="form-control" >
                                            <option value="">请选择门店</option>
                                            <option v-if="company.parentId!=0" v-for="company in companies"
                                                    :value="company.id">{{company.orgName}}
                                            </option>
                                        </select>
                                        <div v-if="$validation.company.invalid && $validation.touched"
                                             class="help-absolute">
                                            <span v-if="$validation.company.invalid">请选择门店</span>
                                        </div>
                                    </div>
                                </div>
                                <div  v-if="user.departmentType!='GROUPCUSTOMERSERVICE'" >
                                    <label for="compName" class="col-sm-3 control-label">门店</label>
                                    <div class="col-sm-2">
                                        <input v-model="form.compName"
                                               maxlength="20"
                                               data-tabindex="1"
                                               id="compName" name="compName" readonly type="text"
                                               class="form-control">
                                    </div>
                                </div>

                                <input v-model="form.liableDepartment"
                                       id="liableDepartment" name="liableDepartment" type="hidden" class="form-control"/>
                                <div v-clickoutside="clickOut"
                                     :class="{'has-error':$validation.liabledepartmentname.invalid && $validation.touched}">
                                    <label for="liableDepartmentName" class="col-sm-2 control-label">责任部门</label>
                                    <div class="col-sm-2">
                                        <input v-model="liableDepartmentName"
                                               v-validate:liabledepartmentname="{ required:{rule:true,message:'请选择责任部门'} }"
                                               maxlength="20"
                                               data-tabindex="1"
                                               id="liableDepartmentName" name="liableDepartmentName" readonly type="text"
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

                            <div class="form-group"
                                 :class="{'has-error':$validation.problem.invalid && $validation.touched}">
                                <label for="problem" class="col-sm-3 control-label">问题描述</label>
                                <div class="col-sm-6">
                                      <textarea
                                              v-validate:problem="{required:true}"
                                              v-model="form.problem"
                                              maxlength="3000"
                                              id="problem"
                                              name="problem"
                                              class="form-control"
                                              placeholder="请输入问题"></textarea>
                                    <div v-if="$validation.problem.invalid && $validation.touched"
                                         class="help-absolute">
                                        <span v-if="$validation.problem.invalid">请输入问题</span>
                                    </div>
                                </div>
                            </div>

                            <div class="row form-group">
                                <div  :class="{'has-error':$validation.questiontype1.invalid && $validation.touched}">
                                    <label for="questionType1" class="col-sm-3 control-label">事项分类</label>
                                    <div class="col-sm-2">
                                        <select
                                                v-validate:questiontype1="{required:true}"
                                                v-model="form.questionType1"
                                                id="questiontype1"
                                                name="questionType1"
                                                class="form-control">
                                            <option value="">事项分类</option>
                                            <option v-for="questionType1 in questionType1s" :value="questionType1.id">
                                                {{questionType1.name}}
                                            </option>
                                        </select>
                                        <div v-if="$validation.questiontype1.invalid && $validation.touched"
                                             class="help-absolute">
                                            <span v-if="$validation.questiontype1.invalid">请选择事项分类</span>
                                        </div>
                                    </div>

                                    <label for="brand" class="col-sm-2 control-label">品牌</label>
                                    <div class="col-sm-2">
                                        <select
                                                v-model="form.brand"
                                                id="brand"
                                                name="brand"
                                                class="form-control">
                                            <option value="0">请选择品牌</option>
                                            <option v-for="probrand in problemCatBrands" :value="probrand.brand.id">{{probrand.brand.name}}</option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <%--<input v-model="form.liablePerson"--%>
                            <%--id="liablePerson" name="liablePerson" type="hidden" class="form-control"/>--%>
                            <%--<div class="form-group"--%>
                            <%--:class="{'has-error':$validation.liablepersonname.invalid && $validation.touched}">--%>
                            <%--<label for="liablePersonName" class="col-sm-2 control-label">责任人</label>--%>
                            <%--<div class="col-sm-8">--%>
                            <%--<input v-model="form.liablePersonName"--%>
                            <%--v-validate:liablepersonname="{ required:{rule:true,message:'请选择责任人'} }"--%>
                            <%--maxlength="20"--%>
                            <%--data-tabindex="1"--%>
                            <%--id="liablePersonName" name="liablePersonName" @click="showliablePersonTable(2)" readonly--%>
                            <%--type="text" class="form-control" placeholder="点击选择责任人">--%>
                            <%--<div v-if="$validation.liablepersonname.invalid && $validation.touched"--%>
                            <%--class="help-absolute">--%>
                            <%--<span v-if="$validation.liablepersonname.invalid">请选择责任人</span>--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--</div>--%>

                            <%--<input v-model="form.styListMobile"--%>
                            <%--id="styListMobile" name="styListMobile" type="hidden" class="form-control"/>--%>
                            <%--<div class="form-group">--%>
                            <%--<label for="styListName" class="col-sm-2 control-label">设计师</label>--%>
                            <%--<div class="col-sm-8">--%>
                            <%--<input v-model="form.styListName"--%>
                            <%--maxlength="20"--%>
                            <%--data-tabindex="1"--%>
                            <%--id="styListName" name="styListName" type="text" readonly class="form-control"--%>
                            <%--placeholder="设计师">--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--<input v-model="form.contractorMobile"--%>
                            <%--id="contractorMobile" name="contractorMobile" type="hidden" class="form-control"/>--%>
                            <%--<div class="form-group">--%>
                            <%--<label for="contractor" class="col-sm-2 control-label">项目经理</label>--%>
                            <%--<div class="col-sm-8">--%>
                            <%--<input v-model="form.contractor"--%>
                            <%--maxlength="20"--%>
                            <%--data-tabindex="1"--%>
                            <%--id="contractor" name="contractor" type="text" readonly class="form-control"--%>
                            <%--placeholder="项目经理">--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <%--<input v-model="form.supervisorMobile"--%>
                            <%--id="supervisorMobile" name="supervisorMobile" type="hidden" class="form-control"/>--%>
                            <%--<div class="form-group">--%>
                            <%--<label for="superVisorName" class="col-sm-2 control-label">监理</label>--%>
                            <%--<div class="col-sm-8">--%>
                            <%--<input v-model="form.superVisorName"--%>
                            <%--maxlength="20"--%>
                            <%--data-tabindex="1"--%>
                            <%--id="superVisorName" name="superVisorName" type="text" readonly class="form-control"--%>
                            <%--placeholder="监理">--%>
                            <%--</div>--%>
                            <%--</div>--%>
                            <div class="row form-group">
                                <div :class="{'has-error':$validation.questiontype2.invalid && $validation.touched}">
                                    <label for="questionType2" class="col-sm-3 control-label">问题类型</label>
                                    <div class="col-sm-2">
                                        <select
                                                v-validate:questiontype2="{required:true}"
                                                v-model="form.questionType2"
                                                id="questionType2"
                                                name="questionType2"
                                                class="form-control">
                                            <option value="">问题类型</option>
                                            <option v-for="questionType2 in questionType2s" :value="questionType2.id">
                                                {{questionType2.name}}
                                            </option>
                                        </select>
                                        <div v-if="$validation.questiontype2.invalid && $validation.touched"
                                             class="help-absolute">
                                            <span v-if="$validation.questiontype2.invalid">请选择问题类型</span>
                                        </div>
                                    </div>
                                    <label for="customerFeedbackTime" class="col-sm-2 control-label">客户要求回电时间</label>
                                    <div class="col-sm-2">
                                        <input
                                                v-model="form.customerFeedbackTime"
                                                v-el:customer-feedback-time
                                                id="customerFeedbackTime"
                                                name="customerFeedbackTime"
                                                type="text"
                                                readonly
                                                class="form-control datepicker"
                                                placeholder="请选择客户要求回电时间">
                                    </div>
                                </div>
                            </div>

                            <div class="form-group row">
                                <hr style=" border: dashed 1px; width: 56%; margin-right: 26%; color: grey"/>
                            </div>

                            <div class="row form-group">
                                <div :class="{'has-error':$validation.type.invalid && $validation.touched}">
                                    <label for="workType" class="col-sm-3 control-label">工单分类</label>
                                    <div class="col-sm-2">
                                        <select
                                                v-model="form.workType"
                                                v-validate:type="{
                                        required:{rule:true,message:'请选择工单类别'}
                                    }"
                                                id="workType"
                                                name="workType"
                                                class="form-control">
                                            <option value="">请选择</option>
                                            <option value="PRESALE">售前</option>
                                            <option value="SELLING">售中</option>
                                            <option value="AFTERSALE">售后</option>
                                        </select>
                                        <div v-if="$validation.type.invalid && $validation.touched"
                                             class="help-absolute">
                                            <span v-if="$validation.type.invalid">请选择工单分类</span>
                                        </div>
                                    </div>
                                </div>

                                <label for="source" class="col-sm-2 control-label">工单来源</label>
                                <div class="col-sm-2" :class="{'has-error':$validation.source.invalid && $validation.touched}">
                                        <select
                                                v-model="form.source"
                                                v-validate:source="{
                                                    required:{rule:true,message:'请选择工单来源'}
                                                     }"
                                                id="source"
                                                name="source"
                                                class="form-control">
                                            <option value="">请选择</option>
                                            <option v-for="s in sourceList" :value="s.id">{{s.name}}</option>
                                        </select>
                                        <div v-if="$validation.source.invalid && $validation.touched"
                                             class="help-absolute">
                                            <span v-if="$validation.source.invalid">请选择工单来源</span>
                                        </div>
                                    </div>

                                    <%--<label for="source" class="col-sm-2 control-label">工单来源</label>
                                    <div class="col-sm-2">
                                        <select
                                                v-model="form.source"
                                                id="source"
                                                name="source"
                                                class="form-control">
                                            <option value="">请选择</option>
                                            <option v-for="s in sourceList" :value="s.id">{{s.name}}</option>
                                        </select>
                                    </div>--%>

                            </div>

                            <div class="row form-group">
                                <div :class="{'has-error':$validation.importantdegree1.invalid && $validation.touched}">
                                    <label for="importantDegree1" class="col-sm-3 control-label">重要程度</label>
                                    <div class="col-sm-2">
                                        <select
                                                v-validate:importantdegree1="{required:true}"
                                                v-model="form.importantDegree1"
                                                id="importantDegree1"
                                                name="importantDegree1"
                                                class="form-control">
                                            <option v-for="impor in importantDegree1s" :value="impor.id">
                                                {{impor.name}}
                                            </option>
                                        </select>
                                        <div v-if="$validation.importantdegree1.invalid && $validation.touched"
                                             class="help-absolute">
                                            <span v-if="$validation.importantdegree1.invalid">请选择重要程度</span>
                                        </div>
                                    </div>

                                    <label for="liableType1" class="col-sm-2 control-label">投诉原因</label>
                                    <div class="col-sm-2">
                                        <select
                                                v-model="form.liableType1"
                                                id="liableType1"
                                                name="liableType1"
                                                class="form-control">
                                            <option value="">请选择投诉原因</option>
                                            <option v-for="liable1 in liableType1s" :value="liable1.id">{{liable1.name}}
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>

                            <input v-model="form.orderId"
                                   data-tabindex="1"
                                   id="orderId" name="orderId" type="hidden" class="form-control">



                            <div v-if="links.length>0" class="form-group">
                                <label for="problem" class="col-sm-2 control-label"></label>
                                <div class="col-sm-7">
                                    <ul>
                                        <li v-for="link in links" style="float: left;list-style-type:none;">

                                            <a v-if="link.a!=''" :href="link.a" target="_blank">
                                                <img :src="link.a" alt="" style="width: 105px;margin-left: 60px">
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="problem" class="col-sm-2 control-label"></label>
                                <div class="col-sm-8">
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
                                                v-if="form.photo != '' && form.photo != null" type="button"
                                                class="btn btn-sm btn-danger">
                                            删除附件
                                        </button>
                                    </div>
                                </div>
                            </div>
                </div>
                <div class="modal-center">
                    <button @click="consultClickHandler" :disabled="submitting" type="button" class="btn btn-success">
                        咨询完毕
                    </button>
                    &nbsp;&nbsp;
                    <button @click="submitClickHandler" :disabled="submitting" type="button"
                            class="btn btn-primary">发起工单
                    </button>
                </div>

                </form>
                </validator>
                <!-- <div class="columns columns-right btn-group pull-right"></div> -->
            </div>
        </div>
        <!-- ibox end -->
    </div>
    <!-- container end-->


</div>
<%@include file="/include/admin/footer.jsp" %>
<!-- 客户列表 -->
<div id="customerDiv" class="modal fade" tabindex="-1" data-width="1000">
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="searchForm" action='#'>
                    <div v-if="user.departmentType=='GROUPCUSTOMERSERVICE'" class="col-md-3">
                        <div class="form-group">
                            <select
                                    v-model="form.cusCompany"
                                    id="cusCompany"
                                    name="cusCompany"
                                    class="form-control">
                                <option value="" selected>选择门店</option>
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
                                   maxlenth="11"
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

<div id="mdnOrder" class="modal fade" tabindex="-1" data-width="1000">
    <!-- ibox start -->
    <div class="ibox">
        <div class="ibox-content">
            <div class="row">
                <form id="searchForm2" action='#'>
                    <div v-if="user.departmentType=='GROUPCUSTOMERSERVICE'"  class="col-md-3">
                        <div class="form-group" >
                            <select
                                    v-model="form.contractCompany"
                                    id="contractCompany"
                                    name="contractCompany"
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
                            <%--<button @click="searchOrderClickHandler" :disabled="submitting"--%>
                                    <%--type="button" class="btn btn-block btn-outline btn-default">--%>
                                <%--<i class="fa fa-search"></i></button>--%>

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
<!--右侧部分结束-->
</div>
<!-- template 应该永远比 script靠前 -->
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
<script src="/static/admin/js/sale/workorder/add.js?ver=1.0"></script>
</body>
</html>