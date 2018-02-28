<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>修改密码</title>
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
            <div class="ibox-content">
                <validator name="validation">
                    <form name="createMirror" novalidate class="form-horizontal" role="form">
                        <div class="text-center">
                            <h3>修改密码</h3>
                        </div>
                        <div>
                            <div class="form-group"
                                 :class="{'has-error':$validation.oldpassword.invalid && submitBtnClick}">
                                <label for="plainPwd" class="col-sm-2 control-label">原密码</label>
                                <div class="col-sm-8">
                                    <input v-model="user.plainPwd"
                                           v-validate:oldpassword="{
                                    required:{rule:true,message:'请输入原密码'}
                                }"
                                           maxlength="20"
                                           data-tabindex="1"
                                           id="plainPwd" name="plainPwd" type="password" class="form-control"
                                           placeholder="原密码">
                                    <span v-cloak v-if="$validation.oldpassword.invalid && submitBtnClick"
                                          class="help-absolute">
                            <span v-for="error in $validation.oldpassword.errors">
                              {{error.message}} {{($index !== ($validation.oldpassword.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                                </div>
                            </div>

                            <div class="form-group"
                                 :class="{'has-error':$validation.newpassword.invalid && submitBtnClick}">
                                <label for="loginPwd" class="col-sm-2 control-label">新密码</label>
                                <div class="col-sm-8">
                                    <input v-model="user.loginPwd"
                                           v-validate:newpassword="{
                                    required:{rule:true,message:'请输入描述'},
                                    required:{rule:password,message:'请输入正确格式的密码'}
                                }"
                                           maxlength="20"
                                           data-tabindex="1"
                                           id="loginPwd" name="loginPwd" type="password" class="form-control"
                                           placeholder="新密码">
                                    <span v-cloak v-if="$validation.newpassword.invalid && submitBtnClick"
                                          class="help-absolute">
                            <span v-for="error in $validation.newpassword.errors">
                              {{error.message}} {{($index !== ($validation.newpassword.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                                </div>
                            </div>

                            <div class="form-group"
                                 :class="{'has-error':$validation.confirmpwd.invalid && submitBtnClick}">
                                <label for="confirmPwd" class="col-sm-2 control-label">确认新密码</label>
                                <div class="col-sm-8">
                                    <input v-model="user.confirmPwd"
                                           v-validate:confirmpwd="{
                                    required:{rule:true,message:'请输入确认密码'},
                                    required:{rule:password,message:'请输入正确格式的密码'}
                                }"
                                           maxlength="20"
                                           data-tabindex="1"
                                           id="confirmPwd" name="confirmPwd" type="password" class="form-control"
                                           placeholder="输入确认密码">
                                    <span v-cloak v-if="$validation.confirmpwd.invalid && submitBtnClick"
                                          class="help-absolute">
                            <span v-for="error in $validation.confirmpwd.errors">
                              {{error.message}} {{($index !== ($validation.confirmpwd.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                                </div>
                            </div>
                        </div>
                        <div class="col-sm-12 m-t-lg modal-footer">
                            <button @click="submit" :disabled="disabled" type="button" class="btn btn-primary">修改密码
                            </button>
                        </div>
                    </form>
                </validator>
                <!-- ibox end -->
            </div>
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
<script src="/static/admin/js/sale/user/resetPwd.js"></script>
</body>
</html>