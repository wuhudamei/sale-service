<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"><meta name="renderer" content="webkit">

    <title>大美登录</title>
    <meta name="keywords" content="大美,售后服务系统,登录">
    <meta name="description" content="大美售后服务系统登录">

    <link rel="shortcut icon" href="/static/admin/img/favicon.ico">
	<link rel="stylesheet" href="/static/admin/css/lib.css">
	<link rel="stylesheet" href="/static/admin/css/style.css">
	<script src="/static/admin/js/lib.js"></script>
	  <!-- 页面公用 -->
  <script>
  var ctx = '${ctx}';
  </script>
</head>

<body class="gray-bg">
    <div  id=loginCont class="middle-box text-center loginscreen  animated fadeInDown">
        <div>
            <div>
                <h1 class="logo-name"><img src="/static/img/logo.png"></h1>
            </div>
            <h3>欢迎使用 大美售后管理系统</h3>

            <form v-on:submit.prevent="submit" role="form">
                <div class="form-group">
                    <input 
                     v-model="form.username"
                     id="username" name="username" type="text" class="form-control" placeholder="请输入用户名" required="">
                </div>
                <div class="form-group">
                    <input 
                    v-model="form.password"
                    type="password" class="form-control" placeholder="请输入密码" required="">
                </div>
                <button :disabled="submitting" type="submit" class="btn btn-block btn-primary">登 录</button>
                </p>

            </form>
        </div>
    </div>
    <script src="/static/admin/js/login/login.js"></script>
</body>
</html>
