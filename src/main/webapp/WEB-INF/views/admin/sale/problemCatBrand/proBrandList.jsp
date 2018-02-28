<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>品牌事项分类</title>
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
                    <div class="row">
                        <form id="searchForm" @submit.prevent="query">

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label class="sr-only" for="keyword"></label>
                                    <input
                                            v-model="form.keyword"
                                            id="keyword" name="keyword"
                                            type="text"
                                            placeholder="品牌" class="form-control"/>
                                </div>
                            </div>

                            <div class="col-md-2">
                                <div class="form-group">
                                    <label class="sr-only" for="questionType1"></label>
                                    <select v-model="form.questionType1"
                                            id="questionType1"
                                            name="questionType1"
                                            class="form-control">
                                        <option  value="">--事项分类--</option>
                                        <option v-for="dict in dictList" :value="dict.id" >{{dict.name}}</option>
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
                            <!-- 将剩余栅栏的长度全部给button -->
                            <div class="col-md-12 text-right">
                                <%--<shiro:hasPermission name="admin:role:add">--%>
                                    <div class="form-group">
                                        <button @click="createBtnClickHandler" id="createBtn" type="button"
                                                class="btn btn-outline btn-primary">新增
                                        </button>
                                    </div>
                               <%-- </shiro:hasPermission>--%>
                            </div>
                        </form>
                    </div>
                    <!-- <div class="columns columns-right btn-group pull-right"></div> -->
                    <table v-el:data-table id="dataTable" width="100%"
                           class="table table-striped table-bordered table-hover">
                    </table>
                </div>
            </div>
            <!-- ibox end -->
        </div>
        <!-- container end-->

        <!-- 添加modal-->
        <div id="modal" class="modal fade" tabindex="-1" data-width="760">
            <validator name="validation">
                <form name="createMirror" novalidate class="form-horizontal" role="form">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3>新增品牌事项分类</h3>
                    </div>
                    <div class="modal-body">
                    	<br>
                        <div class="form-group" :class="{'has-error':($validation.questionType1Id.invalid && $validation.touched)}">
                            <label for="questionType1Id" class="col-sm-2 control-label">事项分类</label>
                            <div class="col-sm-8">
                                <select v-model="problemCatbrand.questionType1Id" 
                                		v-validate:question-Type1-Id="{required:true}"
                                            id="questionType1Id"
                                            name="questionType1Id"
                                            class="form-control">
                                        <option  value="">--请选择事项分类--</option>
                                        <option v-for="dict in dictList" :value="dict.id" >{{dict.name}}</option>
                                    </select>
                                <div v-if="$validation.questionType1Id.invalid && $validation.touched"
                                     class="help-absolute">
                                    <span v-if="$validation.questionType1Id.invalid">请选择事项分类</span>
                                </div>
                            </div>
                        </div>
                        
                        <div class="form-group" :class="{'has-error':($validation.brandId.invalid && $validation.touched)}">
                            <label for="brandId" class="col-sm-2 control-label">品牌</label>
                            <div class="col-sm-8">
                                <select v-model="problemCatbrand.brandId" 
                                		v-validate:brand-Id="{required:true}"
                                            id="brandId"
                                            name="brandId"
                                            class="form-control">
                                        <option  value="">--请选择品牌--</option>
                                        <option v-for="brand in brandList" :value="brand.id" >{{brand.name}}</option>
                                    </select>
                                <div v-if="$validation.brandId.invalid && $validation.touched"
                                     class="help-absolute">
                                    <span v-if="$validation.brandId.invalid">请选择品牌</span>
                                </div>
                            </div>
                        </div>


                    </div>
                    <div class="modal-footer">
                        <button :disabled="disabled" type="button" data-dismiss="modal" class="btn">取消</button>
                        <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>
                    </div>
                </form>
            </validator>
        </div>

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
<script src="/static/admin/js/sale/problemCatBrand/proBrandList.js"></script>
</body>
</html>