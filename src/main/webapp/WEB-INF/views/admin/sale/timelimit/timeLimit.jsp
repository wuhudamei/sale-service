<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>责任部门问题设置</title>
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
                                    <select v-model="form.companyId"
                                            id="companyId" 
                                            name="companyId"
                                            placeholder="选择门店"
                                            class="form-control" @change="findOrganizations()">
                                        <option value="" selected="selected">--选择门店--</option>
                                        <option v-for="org in organizations" :value="org.id" >{{org.orgName}}</option>
                                    </select>
                                </div>
                            </div>
                            
                            <div class="col-md-2">
                                <div class="form-group">
                                    <select v-model="form.departmentId"
                                            id="departmentId" 
                                            name="departmentId"
                                            placeholder="选择部门"
                                            class="form-control" @change="findOrgQuestion()">
                                        <option value="" selected="selected">--选择部门--</option>
                                        <option v-for="org in departments" :value="org.id" >{{org.orgName}}</option>
                                    </select>
                                </div>
                            </div>
                            <div class="col-md-2">
                                <div class="form-group">
                                    <label class="sr-only" for="questionCategoryId"></label>
                                    <select 
                                   			v-model="form.questionCategoryId"
                                            id="questionCategoryId"
                                            name="questionCategoryId"
                                            class="form-control" @change="findQuestionType()">
                                        <option  value="" selected="selected">--请选择事项分类--</option>
                                        <option v-for="question in orgquestions" :value="question.dicId" >{{question.dicName}}</option>
                                    </select>
                                </div>
                            </div>
                             <div class="col-md-2">
                                <div class="form-group">
                                    <label class="sr-only" for="questionTypeId"></label>
                                    <select 
                                    		v-model="form.questionTypeId"
                                            id="questionTypeId"
                                            name="questionTypeId"
                                            class="form-control">
                                        <option  value="" selected="selected">--请选择问题类型--</option>
                                        <option v-for="question in questions" :value="question.id" >{{question.name}}</option>
                                    </select>
                                </div>
                            </div>
  							<div class="col-md-2">
                                <div class="form-group">
                                    <label class="sr-only" for="duration"></label>
                                    <input
                                            v-model="form.duration"
                                            id="duration"
                                            name="duration"
                                            type="text"
                                            placeholder="最长时间(天)" class="form-control"/>
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
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel"  aria-hidden="true">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						添加问题处理时限
					</h4>
				</div>
				<validator name="validation">
				<form id="form_data" class="form-horizontal">
					<div class="modal-body">
						<div class="form-group" :class="{'has-error':$validation.companyId.invalid}  ">
							  <label for="account" class="col-sm-2 control-label">门店:</label>
							   <div class="col-sm-8"  >
							   <select v-model="form.companyId"
                                      id="companyId" name="companyId"
                                      class="form-control" @change="findOrganizations()"
                                      v-validate:company-id="{required:{rule:true,message:'请选择门店'}}"
                                      >
                              <option value="" selected="selected">--选择门店--</option>
                              <option v-for="org in organizations" :value="org.id" >{{org.orgName}}</option>
                              </select>
                               <span v-cloak v-if="$validation.companyId.invalid " class="help-absolute">
                                   <span v-for="error in $validation.companyId.errors">
                                     {{error.message}}
                                   </span>
                               </span>
                              </div>
						</div>
						<div class="form-group" :class="{'has-error':$validation.departmentId.invalid}  ">
							<label for="name" class="col-sm-2 control-label">部门：</label>
							<div class="col-sm-8">
							<select v-model="form.departmentId"
                                   id="departmentId" 
                                   name="departmentId"
                                   placeholder="选择部门"
                                   class="form-control" @change="findOrgQuestion()"
                                   v-validate:department-id="{required:{rule:true,message:'请选择部门'}}"
                                   >
                               <option value="" selected="selected">--选择部门--</option>
                               <option v-for="org in departments" :value="org.id" >{{org.orgName}}</option>
                           </select>
                           <span v-cloak v-if="$validation.departmentId.invalid " class="help-absolute">
                                  <span v-for="error in $validation.departmentId.errors">
                                    {{error.message}}
                                  </span>
                           </span>
                           </div>
						</div>
						<div class="form-group" :class="{'has-error':$validation.questionCategoryId.invalid}  ">
							<label for="name" class="col-sm-2 control-label">事项分类：</label>
							<div class="col-sm-8">
							  <select  v-model="form.questionCategoryId"
                                       id="questionCategoryId"
                                       name="questionCategoryId"
                                       class="form-control" @change="findQuestionType()"
                                   	   v-validate:question-category-id="{required:{rule:true,message:'请选择事项分类'}}">
                                   <option  value="" selected="selected">--选择事项分类--</option>
                                   <option v-for="question in orgquestions" :value="question.dicId" >{{question.dicName}}</option>
                               </select>
                               <span v-cloak v-if="$validation.questionCategoryId.invalid " class="help-absolute">
                                  <span v-for="error in $validation.questionCategoryId.errors">
                                    {{error.message}}
                                  </span>
                          	  </span>
                          	  </div>
						</div>
						<div class="form-group" :class="{'has-error':$validation.questionTypeId.invalid}  ">
							<label for="name" class="col-sm-2 control-label">问题类型：</label>
							<div class="col-sm-8">
							  <select  v-model="form.questionTypeId"
                                       id="questionTypeId"
                                       name="questionTypeId"
                                       class="form-control"
									   v-validate:question-type-id="{required:{rule:true,message:'请选择问题类型'}}">
                                   <option  value="" selected="selected">--选择问题类型--</option>
                                   <option v-for="question in questions" :value="question.id" >{{question.name}}</option>
                               </select>
                                  <span v-cloak v-if="$validation.questionTypeId.invalid " class="help-absolute">
                                  <span v-for="error in $validation.questionTypeId.errors">
                                    {{error.message}}
                                  </span>
                          	  </span>
                          	  </div>
						</div>
						<div class="form-group" :class="{'has-error':$validation.duration.invalid}  ">
							<label for="name" class="col-sm-2 control-label">时限：</label>
							<div class="col-sm-8">
							<input type="text" class="form-control"  
							v-model="form.duration" id="duration" 
							name ="duration" placeholder="以(天)为单位"
							 v-validate:duration="{required:{rule:true,message:'请输入时限'}}">
							<input  v-model="form.id"
                                    id="id"
                                    name="id"
                                    type="hidden"
                                    class="form-control"/>
                            <span v-cloak v-if="$validation.duration.invalid " class="help-absolute">
                                  <span v-for="error in $validation.duration.errors">
                                    {{error.message}}
                                  </span>
                          	  </span>
                          	  </div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">
							关闭
						</button>
						<button type="button" @click="saveTimeLimit()" class="btn btn-primary">
							提交
						</button>
					</div>
				</form>
				</validator>
		</div><!-- /.modal -->

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
<script src="/static/admin/js/sale/timelimit/timeLimit.js"></script>
</body>
</html>