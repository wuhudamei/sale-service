<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<div class="form-horizontal">
	<div class="row custInfo">
			<label class="col-sm-2 col-xs-3 control-label">姓名</label>
			<div  class="col-sm-3 col-xs-9 ">
				<p class="control-label" style="text-align: left">{{order.customerName}}</p>
			</div>
			<label class="col-sm-2 col-xs-3 control-label">电话</label>
			<div class="col-sm-3 col-xs-9 ">
				<p class="control-label" style="text-align: left">{{order.customerMobile}}</p>
			</div>
	</div>

	<div class="row custInfo">
		<label class="col-sm-2 col-xs-3 control-label">地址</label>
		<div class="col-sm-8 col-xs-9 ">
			<p class="control-label" style="text-align: left">{{order.customerAddress}}</p>
		</div>
	</div>

	<div class="row custInfo">
		<label class="col-sm-2 col-xs-8 control-label">合同开始/竣工时间</label>
		<div class="col-sm-3 col-xs-12">
			<p class="control-label" style="text-align: left">
				{{order.contractStartTime}}
			</p>
		</div>

		<label class="col-sm-2 col-xs-8 control-label">实际开始/竣工时间</label>
		<div class="col-sm-3 col-xs-12">
			<p class="control-label" style="text-align: left">
				{{order.actualStartTime.substring(0,10)||' - '}}/{{order.actualCompletionTime.substring(0,10)||' - '}}
			</p>
		</div>

	</div>
	<div class="row custInfo">
		<label class="col-sm-2 col-xs-9 control-label">设计师/项目经理/监理</label>
		<div class="col-sm-3 col-xs-12">
			<p class="control-label" style="text-align: left">
				{{order.styListName}}
			</p>
		</div>

		<label for="source" class="col-sm-2 col-xs-5 control-label">工单来源</label>
		<div class="col-sm-3 col-xs-7">
			<p class="control-label" style="text-align: left">{{order.source.name}}</p>
		</div>
	</div>

	<div class="row custInfo">
			<label class="col-sm-2 col-xs-5 control-label">事项分类</label>
			<div class="col-sm-3 col-xs-7">
				<p class="control-label" style="text-align: left">{{order.questionType1.name}}</p>
			</div>
			<label class="col-sm-2 col-xs-5 control-label">问题类型</label>
			<div class="col-sm-3 col-xs-7">
				<p class="control-label" style="text-align: left">{{order.questionType2.name}}</p>
			</div>

	</div>

	<div class="row custInfo">
		<label class="col-sm-2 col-xs-5 control-label">工单分类</label>
		<div class="col-sm-3 col-xs-7">
			<p class="control-label" style="text-align: left">{{order.workTypeName}}</p>
		</div>
		<label class="col-sm-2 col-xs-5 control-label">责任部门</label>
		<div class="col-sm-3 col-xs-7">
			<p class="control-label" style="text-align: left">{{order.liableDepartment.orgName}}</p>
		</div>
	</div>

	<div class="row custInfo">
		<label class="col-sm-2 col-xs-5 control-label">投诉原因</label>
		<div class="col-sm-3 col-xs-7">
			<p class="control-label" style="text-align: left">{{order.complaintType.name}}</p>
		</div>
		<label for="customerFeedbackTime" class="col-sm-2 col-xs-12 control-label">客户要求回电时间</label>
		<div class="col-sm-3 col-xs-12">
			<p class="control-label" style="text-align: left">{{order.customerFeedbackTime}}</p>
		</div>
	</div>

	<div class="row custInfo">
		<label for="treamentTime" class="col-sm-2 col-xs-6 control-label">预计完成时间</label>
		<div class="col-sm-3 col-xs-12">
			<p class="control-label" style="text-align: left">{{order.treamentTime}}</p>
		</div>
		<label for="brand" class="col-sm-2 col-xs-3 control-label">品牌</label>
		<div class="col-sm-3 col-xs-7">
			<p class="control-label" style="text-align: left">{{order.brand.name}}</p>
		</div>
	</div>

	<div class="row custInfo">
		<label for="importantDegree1" class="col-sm-2 col-xs-5 control-label">重要程度</label>
		<div class="col-sm-3 col-xs-7">
			<p class="control-label" style="text-align: left">{{order.importantDegree1.name}}</p>
		</div>

		<label for="createUserName" class="col-sm-2 col-xs-5 control-label">工单发起人</label>
		<div class="col-sm-3 col-xs-7">
			<p class="control-label" style="text-align: left">{{order.createUserName}}</p>
		</div>
	</div>

	<div class="row custInfo">
		<label class="col-sm-2  col-xs-5 control-label">问题描述</label>
		<div class="col-sm-8 col-xs-12">
			<p class="control-label" style="text-align: left" id="problemId">
				{{{order.problem}}}
			</p>
		</div>
	</div>
	<div class="row custInfo">
		<label class="col-sm-2  col-xs-5 control-label">分配意见</label>
		<div class="col-sm-8 col-xs-12">
			<p class="control-label" style="text-align: left" id="suggestion">
				{{{order.suggestion}}}
			</p>
		</div>
	</div>

	<div class="row custInfo">
		<label for="treamentPlan" class="col-sm-2 col-xs-5 control-label">处理方案</label>
		<div class="col-sm-8 col-xs-12 ">
			<p class="control-label" style="text-align: left">{{{order.treamentPlan}}}</p>
		</div>
	</div>
</div>