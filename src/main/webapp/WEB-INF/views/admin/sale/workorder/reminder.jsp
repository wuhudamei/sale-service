<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>催单</title>
<meta name="keywords" content="">
<meta name="description" content="">
<%@include file="/include/admin/head.jsp"%>
<link rel="stylesheet" href="/static/css/tab.css">
<link rel="stylesheet"
	href="/static/hplus/css/plugins/jsTree/themes/default/style.min.css" />
<link href="/static/hplus/js/plugins/layer/skin/layer.css"
	rel="stylesheet">
<%@include file="/include/admin/head.jsp"%>
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
	<div id="wrapper">
		<%@include file="/include/admin/nav.jsp"%>
		<!-- 顶部 -->
		<%@include file="/include/admin/header.jsp"%>
		<!--右侧部分开始-->

		<div id="page-wrapper" class="gray-bg dashbard-1">
			<!-- container -->
			<!-- 内容部分 start-->
			<!-- 面包屑 -->
			<div id="container" class="wrapper wrapper-content">
				<!-- ibox start -->
				<div class="ibox">
					<div class="ibox-content">
						<div>
							<Tabs type="card" > <Tab-pane
								label="催单">
							<div class="row">
								<validator name="validation">
								<form id="searchForm" class="form-horizontal"
									@submit.prevent="query">
									
									<!-- 投诉原因-->
									<div class="col-sm-6 m-t-lg" >
										<label for="complaintType" class="col-sm-3 control-label">
											投诉原因:
										</label>
										<div class="col-sm-8">
											<select v-model="form.complaintType" id="complaintType"
												name="complaintType" class="form-control" 
												placeholder="--请选择投诉原因--" >
												<option value="">--请选择投诉原因--</option>
												<option v-for="complaintType in complaintTypes" :value="complaintType.id">{{complaintType.name}}</option>
											</select>
			                          </div>
									</div>
									
									<!-- 催单说明-->
									<div class="col-sm-9 m-t-lg" :class="{'has-error':($validation.remark.invalid && $validation.touched)}">
										<label for="remark" class="col-sm-2 control-label">
											<font color="red">*</font> 催单说明: 
										</label>
										<div class="col-sm-10">
										<textarea v-validate:remark="{  required:true }"
											v-model="form.remark" id="remark"
											name="remark" maxlength="500" class="form-control"
											placeholder="催单说明" rows="5">
										</textarea>
											<div v-if="$validation.remark.invalid && $validation.touched"
					                               class="help-absolute">
					                            <span v-if="$validation.remark.invalid">请输入催单说明</span>
				                            </div>
			                          </div>
									</div>
									
									<div class="col-sm-12 m-t-lg modal-center">
										<button @click="submitClickHandler" :disabled="submitting"
										type="button" class="btn btn-primary">提交</button>
										&nbsp;
										<button :disabled="submitting" type="button"
												data-dismiss="modal" class="btn" @click="cancel">取消</button>
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
						<div></div>
					</div>
				</div>
				<!-- ibox end -->
			</div>
			<!-- container end-->
		</div>
		<%@include file="/include/admin/footer.jsp"%>

		<!--右侧部分结束-->
	</div>
	<!-- template 应该永远比 script靠前 -->

	<!-- template 应该永远比 script靠前 -->
	<%@include file="/WEB-INF/views/admin/component/breadcrumb.jsp"%>
	<script src="/static/admin/js/mixins.js"></script>
	<script src="/static/admin/js/filters.js"></script>
	<script src="/static/admin/js/components/breadcrumb.js"></script>
	<script src="/static/admin/js/components/tab.js"></script>


	<!-- 主方法，每页均需引用 -->
	<script src="/static/admin/js/main.js"></script>
	<script src="/static/admin/js/sale/workorder/remainder.js"></script>
</body>
</html>