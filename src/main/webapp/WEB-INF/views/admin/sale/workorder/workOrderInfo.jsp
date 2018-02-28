<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>工单详细</title>
<meta name="keywords" content="">
<meta name="description" content="">
<%@include file="/include/admin/head.jsp"%>
<link rel="stylesheet" href="/static/css/tab.css">
<link rel="stylesheet"
	href="/static/hplus/css/plugins/jsTree/themes/default/style.min.css" />
<link href="/static/hplus/js/plugins/layer/skin/layer.css"
	rel="stylesheet">
<%@include file="/include/admin/head.jsp"%>

	<style>
		.custInfo{
			margin-right: -15px;
			margin-left: -15px;
			box-sizing: border-box;
			text-align: center;
			font-size: 14px;
		}

		.control-label{
			text-align: left;
		}

	</style>
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
				<div id="container" class="wrapper wrapper-content">
				<!-- ibox start -->
				<div class="ibox">
					<div class="ibox-content" v-clock>
						<div>
							<Tabs type="card" >
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
							<div class="m-t-lg " style="text-align: center">
								<button  type="button" class="btn btn-primary"
									data-dismiss="modal" @click="cancel">返回
								</button>
							</div>
						</div>
					</div>
				</div>
				<!-- ibox end -->
			</div>
			<!-- container end-->
		</div>

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
	<script src="/static/admin/js/sale/workorder/workOrderInfo.js"></script>
</body>
</html>