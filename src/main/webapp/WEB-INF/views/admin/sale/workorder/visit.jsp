<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="UTF-8">
<title>回访</title>
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
					<div class="ibox-content" v-clock>
						<div>
							<Tabs type="card"> 
								<Tab-pane label="回访">
							<div class="row">
								<validator name="validation">
								<form id="searchForm" class="form-horizontal"
									@submit.prevent="query">

									<!-- 回访结果-->
									<div class="col-sm-6 m-t-lg" :class="{'has-error':($validation.orderStatus.invalid && $validation.touched)}">
										<label for="orderStatus" class="col-sm-3 control-label">
											<font color="red">*</font>回访结果: 
										</label>
										<div class="col-sm-8">
											<select v-model="form.orderStatus" id="orderStatus" 
												v-validate:order-Status="{  required:true }"
												name="orderStatus" class="form-control" @change="chooseUnsatisfied"
												placeholder="--请选择投诉原因--" >
												<option value="" >--请选择回访结果--</option>
												<option value="SATISFIED">满意</option>
												<option value="COMMONLY">一般</option>
												<option value="UNSATISFIED">不满意</option>
												<option value="INVALIDVISIT">暂无评价</option>
												<option value="FAILUREVISIT">不再回访</option>
											</select>
				                          	<div v-if="$validation.orderStatus.invalid && $validation.touched"
						                               class="help-absolute">
					                            <span v-if="$validation.orderStatus.invalid">请选择回访结果</span>
				                            </div>
			                            </div>
									</div>
									
									<!-- 不满意原因 -->
									<div class="col-sm-6 m-t-lg" :class="{'has-error':($validation.complaintType.invalid && $validation.touched 
										&& unsatisfied)}">
										<label for="complaintType" class="col-sm-3 control-label">
											<span v-if="unsatisfied">
					                            <font color="red">*</font>
				                            </span>
											不满意原因: 
										</label>
										<div class="col-sm-8">
											<select v-model="form.complaintType" id="complaintType"
												name="complaintType" class="form-control" 
												placeholder="--不满意原因--"  v-validate:complaint-Type="{required:true}">
												<option value="" >--请选择不满意原因--</option>
                                        		<option v-for="complaintType in complaintTypes" :value="complaintType.id" >{{complaintType.name}}</option>
											</select>
											<div v-if="$validation.complaintType.invalid && $validation.touched && unsatisfied"
					                               class="help-absolute">
					                            <span v-if="$validation.complaintType.invalid">请输入不满意原因</span>
				                            </div>
			                          </div>
									</div>

									<!-- 回访备注-->
									<div class="col-sm-9 m-t-lg" :class="{'has-error':($validation.remark.invalid && $validation.touched 
										&& form.isNew == 'true')}">
										<label for="remark" class="col-sm-2 control-label">
											<span v-if="form.isNew == 'true'">
					                            <font color="red">*</font>
				                            </span>
											 回访备注:
										</label>
										<div class="col-sm-10">
											<textarea v-validate:remark="{  required:true }"
												v-model="form.remark" id="remark"
												name="remark" maxlength="1000" class="form-control" 
												placeholder="回访备注">
											</textarea>
											<div v-if="$validation.remark.invalid && $validation.touched && form.isNew == 'true'"
					                               class="help-absolute">
					                            <span v-if="$validation.remark.invalid">请输入回访备注</span>
				                            </div>
			                          </div>
									</div>
									
									<!-- 生成新工单 -->
									<div class="col-sm-6 m-t-lg" >
										<label for="isNew" class="col-sm-3 control-label">
											<font color="red">*</font> 生成新工单: 
										</label>
										<div class="col-sm-8 control-label">
											<div class="col-sm-1 ">
												<input type="radio" v-model="form.isNew" value="true" />
											</div>
											<div class="col-sm-1">是</div>
											
											<div class="col-sm-1">
												<input type="radio" v-model="form.isNew" value="false"  checked="checked" />
											</div>
											<div class="col-sm-1">否</div>
			                          </div>
									</div>
									
									<!-- 回电时间 -->
									<div class="col-sm-6 m-t-lg" :class="{'has-error':($validation.customerFeedbackTime.invalid && $validation.touched
										&& form.isNew == 'true')}">
										<label for="customerFeedbackTime" class="col-sm-3 control-label">
											<span v-if="form.isNew == 'true'">
					                            <font color="red">*</font>
				                            </span>
											客户要求回电时间: 
										</label>
										<div class="col-sm-8">
		                                    <input v-model="form.customerFeedbackTime" v-validate:customer-Feedback-Time="{ required:true }"
		                                           id="customerFeedbackTime" name="customerFeedbackTime"
		                                            type="text" class="datepicker form-control"
		                                            placeholder="客户要求回电时间" readonly="readonly"/>
		                          			 <div v-if="$validation.customerFeedbackTime.invalid && $validation.touched && form.isNew == 'true'"
					                               class="help-absolute">
					                            <span v-if="$validation.customerFeedbackTime.invalid">请选择客户要求回电时间</span>
				                            </div>
			                          </div>
									</div>
									
									<!-- 重要程度 -->
									<div class="col-sm-6 m-t-lg" >
										<label for="importantDegree1" class="col-sm-3 control-label">
											 重要程度:
										</label>
										<div class="col-sm-8">
		                                     <select v-model="form.importantDegree1"
		                                            id="importantDegree1" name="importantDegree1"
		                                            placeholder="选择重要程度" class="form-control">
		                                        <option value="" >重要程度</option>
		                                        <option v-for="importantDegree1 in importances" :value="importantDegree1.id" >{{importantDegree1.name}}</option>
		                                    </select>
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
	<script src="/static/admin/js/sale/workorder/visit.js"></script>
</body>
</html>