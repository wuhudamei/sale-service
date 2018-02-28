<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>数据字典</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <link href="${ctx}/static/css/wx/zTreeStyle.css" rel="stylesheet">
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
                    <div class="zTreeDemoBackground">
                        <ul id="treeDemo" class="ztree"></ul>
                    </div>
                    <div class="row">
                        <form id="searchForm" @submit.prevent="query">
                            <div class="col-md-2">
                                <!--v-clickoutside 加入指令，方法处理隐藏效果,同一个页面内多处使用的话，最好调用不同的方法-->
                                <div class="form-group" v-clickoutside="clickOutTT">
                                    <label class="sr-only" for="keyword"></label>
                                    <input
                                            @click="showTreeCheck=true"
                                            type="text"
                                            placeholder="名称/代码" class="form-control"/>
                                    <!--:show-tree.sync 双向绑定是否显示树属性 一般开始的时候的 false 如果不需要切换隐藏/显示的时候是true-->
                                    <!--@on-change 绑定节点有checkbox的时候checkbox变化的事件，传入参数为该节点-->
                                    <!--@on-click 绑定节点被点击事件，传入参数为该节点-->
                                    <z-tree v-ref:nodes-checkbox :nodes="nodesCheck" :show-tree.sync="showTreeCheck" @on-change="treeCheckboxChange" @on-click="treeClick"></z-tree>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group" v-clickoutside="clickOut">
                                    <input
                                            @click="showTreeSelect=true"
                                            type="text"
                                            placeholder="名称/代码" class="form-control"/>
                                    <!--如果不需要checkbox选择框，则添加属性 :show-checkbox="false"-->
                                    <z-tree v-ref:nodes-select :nodes="nodesSelect" :show-tree.sync="showTreeSelect" @on-click="treeClick" :show-checkbox="false"></z-tree>
                                </div>
                            </div>

                            <!-- 将剩余栅栏的长度全部给button -->
                            <div class="col-md-7">
                                <shiro:hasPermission name="dictionary:add">
                                    <div class="form-group">
                                        <button @click="createBtnClickHandler" id="createBtn" type="button"
                                                class="btn btn-outline btn-primary">获取选择的节点
                                        </button>
                                    </div>
                                </shiro:hasPermission>
                            </div>


                            <div id="modal" class="modal fade" tabindex="-1" data-width="760">
                                <validator name="validation">
                                    <form name="createMirror" novalidate class="form-horizontal" role="form">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                            <h3>编辑组织机构</h3>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <label class="sr-only" for="keyword"></label>
                                                <input
                                                        @click="showTreeCheck=true"
                                                        type="text"
                                                        placeholder="名称/代码" class="form-control"/>
                                                <!--:show-tree.sync 双向绑定是否显示树属性 一般开始的时候的 false 如果不需要切换隐藏/显示的时候是true-->
                                                <!--@on-change 绑定节点有checkbox的时候checkbox变化的事件，传入参数为该节点-->
                                                <!--@on-click 绑定节点被点击事件，传入参数为该节点-->
                                                <z-tree v-ref:nodes-checkbox :nodes="nodesCheck" :show-tree.sync="showTreeCheck" @on-change="treeCheckboxChange" @on-click="treeClick"></z-tree>
                                            </div>
                                            <div class="form-group" :class="{'has-error':$validation.name.invalid && submitBtnClick}">
                                                <label for="orgName" class="col-sm-2 control-label">组织机构名称</label>
                                                <div class="col-sm-8">
                                                    <input v-model="org.orgName"
                                                           v-validate:name="{
                                    required:{rule:true,message:'请输入组织机构名称'},
                                    maxlength:{rule:20,message:'组织机构名称最长不能超过20个字符'}
                                }"
                                                           maxlength="20"
                                                           data-tabindex="1"
                                                           id="orgName" name="orgName" type="text" class="form-control"
                                                           placeholder="组织机构名称">
                                                    <span v-cloak v-if="$validation.name.invalid && submitBtnClick" class="help-absolute">
                            <span v-for="error in $validation.name.errors">
                              {{error.message}} {{($index !== ($validation.name.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
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
                        </form>
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
<%@include file="/WEB-INF/views/admin/component/ztree.jsp" %>

<script src="/static/admin/js/mixins.js"></script>
<script src="/static/admin/js/filters.js"></script>
<script src="/static/admin/js/components/breadcrumb.js"></script>

<script src="/static/admin/js/components/ztree.js"></script>

<script src="/static/admin/js/jquery.ztree.core.js"></script>
<script src="/static/admin/js/jquery.ztree.excheck.js"></script>

<!-- 主方法，每页均需引用 -->
<script src="/static/admin/js/main.js"></script>
<script src="/static/admin/js/demo/tree.js"></script>
</body>
</html>