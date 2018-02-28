<%--
  Created by IntelliJ IDEA.
  User: losemycat
  Date: 2016/11/3
  Time: 18:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.rocoinfo.entity.account.User" %>
<div class=" border-bottom white-bg">
    <nav class="navbar navbar-static-top" role="navigation">
        <div class="navbar-header">
            <button aria-controls="navbar" aria-expanded="false" data-target="#navbar" data-toggle="collapse"
                    class=" navbar-toggle collapsed" type="button">
                <i class="fa fa-reorder"></i>
            </button>
            <a href="/wx" class="navbar-logo" target="_self">
                <img src="${ctx}/static/img/logo.png" width="118" height="46"/>
            </a>
        </div>
        <div class="navbar-collapse collapse nav-time" id="navbar">
            <ul class="nav navbar-nav text-center">
                <li class="dropdown">
                    <a aria-expanded="false" role="button" class="dropdown-toggle" data-toggle="dropdown" id="a1">
                        工时概况
                    </a>
                </li>
                <li class="dropdown">
                    <a aria-expanded="false" role="button" class="dropdown-toggle" data-toggle="dropdown" id="a2">
                        填写工时</a>
                </li>
                <li class="dropdown">
                    <a aria-expanded="false" role="button" class="dropdown-toggle" data-toggle="dropdown" id="a3">
                        签到签退</a>
                </li>
                <li class="dropdown">
                    <a aria-expanded="false" role="button" class="dropdown-toggle" data-toggle="dropdown" id="a4">
                        个人工时 </a>
                </li>
                <li class="dropdown">
                    <a aria-expanded="false" role="button" class="dropdown-toggle" data-toggle="dropdown" id="a5">
                        项目列表</a>
                </li>
                <li class="dropdown">
                    <a aria-expanded="false" role="button" class="dropdown-toggle" data-toggle="dropdown" id="a6">
                        编辑项目 </a>
                </li>
                <li class="dropdown" id="projectCheck">
                    <a aria-expanded="false" role="button" class="dropdown-toggle" data-toggle="dropdown" id="a7">
                        项目审批</a>
                </li>
                <li class="dropdown" id="categoryMan">
                    <a aria-expanded="false" role="button" class="dropdown-toggle" data-toggle="dropdown" id="a8">
                        分类管理 </a>
                </li>
                <li class="dropdown">
                    <a aria-expanded="false" role="button" class="dropdown-toggle" data-toggle="dropdown" id="a9">
                        请假申请 </a>
                </li>
                <li class="dropdown">
                    <a aria-expanded="false" role="button" class="dropdown-toggle" data-toggle="dropdown" id="a10">
                        人员情况 </a>
                </li>
            </ul>
        </div>
    </nav>
    <div class="row text-center nav-button-group">
        <div class="col-xs-4">
            <button type="button" class="btn btn-xs btn-primary" id="b1">签到</button>
        </div>
        <div class="col-xs-4">
            <button type="button" class="btn btn-xs btn-primary" id="b2">填写工时</button>
        </div>
        <div class="col-xs-4">
            <button type="button" class="btn btn-xs btn-primary" id="b3">工时概况</button>
        </div>
    </div>
</div>
<%
//获取登录用户信息 zzc 2016-11-22 17:36:31
User user = (User)request.getSession().getAttribute("login_user");
String jobNo = "";
Long userId = 0L;
if(user != null){
	jobNo = user.getJobNo();
	userId = user.getId();
}
%>
<input type="hidden" id="jobNo" value="<%=jobNo %>">
<input type="hidden" id="userId" value="<%=userId %>">
<script type="text/javascript">
    (function () {
    	if($("#jobNo").val() == "000142" || $("#userId").val() == "10009"){
        	$("#projectCheck").css("display","block");
        	$("#categoryMan").css("display","block");
    	}else{
        	$("#projectCheck").css("display","none");
        	$("#categoryMan").css("display","none");
    	}
        // 工时概况
        $('#a1').on('click', function (e) {
            window.location.href = '/wx/laborHour/overview';
            e.stopPropagation();
        });

        // 填写工时
        $('#a2').on('click', function (e) {
            window.location.href = '/wx/laborHour/add';
            e.stopPropagation();
        });

        // 签到签退
        $('#a3').on('click', function (e) {
            window.location.href = '/api/sign/goSign?type=sign';
            e.stopPropagation();
        });

        // 个人工时
        $('#a4').on('click', function (e) {
            window.location.href = '/wx/laborHour/personal';
            e.stopPropagation();
        });

        // 项目列表
        $('#a5').on('click', function (e) {
            window.location.href = '/wx/projectList';
            e.stopPropagation();
        });

        // 编辑项目
        $('#a6').on('click', function (e) {
            window.location.href = '/wx/projectCreate';
            e.stopPropagation();
        });

        // 项目审批
        $('#a7').on('click', function (e) {
            window.location.href = '/wx/projectExamine';
            e.stopPropagation();
        });

        // 分类管理
        $('#a8').on('click', function (e) {
            window.location.href = '/wx/categoryList';
            e.stopPropagation();
        });

        // 请假申请
        $('#a9').on('click', function (e) {
            window.location.href = '/wx/leave';
            e.stopPropagation();
        });

        // 人员情况
        $('#a10').on('click', function (e) {
            window.location.href = '/wx/personsituation';
            e.stopPropagation();
        });

        // 签到签退按钮
        $('#b1').on('click', function (e) {
            window.location.href = '/api/sign/goSign';
            e.stopPropagation();
        });

        // 填写工时按钮
        $('#b2').on('click', function (e) {
            window.location.href = '/wx/laborHour/add';
            e.stopPropagation();
        });

        // 工时概况按钮
        $('#b3').on('click', function (e) {
            window.location.href = '/wx/laborHour/overview';
            e.stopPropagation();
        });
    })();
</script>
