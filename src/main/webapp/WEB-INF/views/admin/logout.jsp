<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0"><meta name="renderer" content="webkit">

    <title>美得你登录</title>
    <meta name="keywords" content="美得你,售后服务系统,登录">
    <meta name="description" content="美得你售后服务系统登录">

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
   <div id=loginCont class="middle-box text-center loginscreen  animated fadeInDown" style="width:400px">
	    <div style="margin: 20px 0px">
	      <img src="/static/img/logo.png">
	    </div>
	    </br>
		<h3><img src="/static/img/success.png" style="margin-top: -14px;">&nbsp;&nbsp;您已成功退出美得你客户管理系统  <a href="/index">重新登录</a></h3>
</div>
</body>
</html>
