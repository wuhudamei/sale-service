<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>美得你售后管理系统</title>
    <meta name="keywords" content="">
    <meta name="description" content="">
    <%@include file="/include/admin/head.jsp" %>
    <link rel="stylesheet" href="/static/admin/vendor/fullcalendar/fullcalendar.css">
    <script src="/static/admin/vendor/fullcalendar/fullcalendar.js"></script>
    <script src="/static/admin/vendor/fullcalendar/locale/zh-cn.js"></script>
    <style>
        .text-undo {
            color: #ed5565;
        }

        .text-done {
            color: #1ab394;
        }

        .ellipsis {
            white-space: nowrap;
            text-overflow: ellipsis;
            overflow: hidden;
        }

        [v-cloak] {
            display: none;
        }
    </style>
</head>
<body id="app" class="fixed-sidebar full-height-layout gray-bg">
<div id="wrapper">

    <%@include file="/include/admin/nav.jsp" %>

    <%@include file="/include/admin/header.jsp" %>
    <!--右侧部分开始-->
    <div id="page-wrapper" class="gray-bg dashbard-1">
        <!-- 顶部 -->
        <!-- container -->
        <!-- 内容部分 start-->
        <!-- 面包屑 -->
        <div id="container" class="wrapper wrapper-content" v-cloak>
            <!-- ibox start -->
            <!-- <div class="row"> -->
            <!-- 工作台 -->
            <div class="row">
                <div class="col-sm-12">
                    <div class="ibox" style="margin-bottom:10px;">
                        <div class="ibox-title">
                            <h5>工作台</h5>
                        </div>
                        <div class="ibox-content">
                            <div class="row" style="bottom:20px">
                                <%--待处理--%>

                                <div v-if="user.departmentType=='FILIALECUSTOMERSERVICE' || user.departmentType=='LIABLEDEPARTMENT'" class="col-sm-2">
                                    <a href="/workorder/list?status=CREATE"
                                       class="btn btn-primary btn-block-query"
                                       style="margin-bottom: 10px;">未派单
                                        <span class="label label-warning">{{warnings.createNum}}</span>
                                    </a>
                                </div>

                                <%--待回复--%>

                                <div v-if="user.departmentType=='LIABLEDEPARTMENT'" class="col-sm-2">
                                    <a href="/workorder/list?status=RECEIVED"
                                       class="btn btn-primary btn-block-query"
                                       style="margin-bottom: 10px;">已接收
                                        <span class="label label-warning">{{warnings.receiveNum}}</span>
                                    </a>
                                </div>

                                <%--待回访--%>

                                <div v-if="user.departmentType=='LIABLEDEPARTMENT'" class="col-sm-2">
                                    <a href="/workorder/list?status=PROCESSING"
                                       class="btn btn-primary btn-block-query"
                                       style="margin-bottom: 10px;">处理中
                                        <span class="label label-warning">{{warnings.processingNum}}</span>
                                    </a>
                                </div>

                                <%--申诉无效--%>
                                <div v-if="user.departmentType=='LIABLEDEPARTMENT'" class="col-sm-2">
                                    <a href="/workorder/list?status=REFUSEDAGAIN"
                                       class="btn btn-primary btn-block-query"
                                       style="margin-bottom: 10px;">申诉无效
                                        <span class="label label-warning">{{warnings.refusedagainNum}}</span>
                                    </a>
                                </div>

                                <%--待分配--%>

                                <div v-if="user.departmentType=='FILIALECUSTOMERSERVICE'" class="col-sm-2">
                                    <a href="/workorder/list?status=ASSIGN"
                                       class="btn btn-primary btn-block-query"
                                       style="margin-bottom: 10px;">待分配
                                        <span class="label label-warning">{{warnings.assignNum}}</span>
                                    </a>
                                </div>

                            </div>
                            <div class="row" style="margin-top:20px;">
                                <%--回访未执行--%>
                                    <div v-if="user.departmentType=='GROUPCUSTOMERSERVICE'" class="col-sm-2">
                                        <a href="/workorder/workOrderListWithComplete"
                                           class="btn btn-primary btn-block-query"
                                           style="margin-bottom: 10px;">已完成
                                            <span class="label label-warning">{{warnings.completedNum}}</span>
                                        </a>
                                    </div>
                                <%--回访未执行--%>

                                <div v-if="user.departmentType=='GROUPCUSTOMERSERVICE'" class="col-sm-2">
                                    <a href="/workorder/workOrderListWithInvalid"
                                       class="btn btn-primary btn-block-query"
                                       style="margin-bottom: 10px;">暂无评价
                                        <span class="label label-warning">{{warnings.invalidNum}}</span>
                                    </a>
                                </div>

                                <%--暂无评价--%>

                                <div v-if="user.departmentType=='GROUPCUSTOMERSERVICE'" class="col-sm-2">
                                    <a href="/workorder/workOrderListWithUnsuccessful"
                                       class="btn btn-primary btn-block-query"
                                       style="margin-bottom: 10px;">不再回访
                                        <span class="label label-warning">{{warnings.failvisitNum}}</span>
                                    </a>
                                </div>


                                <%--<div class="col-sm-12 text-right">--%>

                                <%--<a href="/workorder/add"--%>
                                <%--class="btn btn-primary btn-block-query"--%>
                                <%--style="margin-bottom: 10px;">快速发起工单--%>
                                <%--</a>--%>
                                <%--</div>--%>

                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%--该员工工作计划--%>
            <div class="row">
                <div class="col-sm-12">
                    <div class="ibox">
                        <div class="ibox-title">
                            <h5>工作日历</h5>
                            <shiro:hasPermission name="index:createPlan">
                                <div class="text-right">
                                    <button @click="createBtnClickHandler" id="createBtn" type="button"
                                            class="btn btn-outline btn-primary">新增
                                    </button>
                                </div>
                            </shiro:hasPermission>
                        </div>
                        <div class="ibox-content">
                            <div v-el:calendar id="calendar"></div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 弹窗显示工作计划内容-->
            <div id="contentModal" class="modal fade" tabindex="-1" data-width="360" data-height="360">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span><span
                            class="sr-only">Close</span></button>
                    <h4 class="modal-title">工作计划内容</h4>
                </div>
                <div class="modal-body">
                    <div>{{contentTip}}</div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
        <!-- ibox end -->
        <!-- </div> -->
        <!-- 内容部分 end-->
        <!-- container end-->

        <!-- 编辑工作计划的弹窗 -->
        <!-- 编辑/编辑的modal-->
        <div id="modal" class="modal fade" tabindex="-1" data-width="760">
            <validator name="validation">
                <form name="createMirror" novalidate class="form-horizontal" role="form">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                        <h3>编辑工作计划</h3>
                    </div>
                    <div class="modal-body">
                        <div class="form-group" :class="{'has-error':$validation.content.invalid && submitBtnClick}">
                            <label for="content" class="col-sm-2 control-label">内容</label>
                            <div class="col-sm-8">
                                <textarea v-model="plan.content"
                                          v-validate:content="{
                                               required:{rule:true,message:'请输入计划内容'}
                                          }"
                                          data-tabindex="1"
                                          id="content" name="content" class="form-control" placeholder="计划内容">

                                </textarea>
                                <span v-cloak v-if="$validation.content.invalid && submitBtnClick"
                                      class="help-absolute">
                            <span v-for="error in $validation.content.errors">
                              {{error.message}} {{($index !== ($validation.content.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                            </div>
                        </div>

                        <div class="form-group"
                             :class="{'has-error':$validation.starttime.invalid && submitBtnClick}">
                            <label for="starttime" class="col-sm-2 control-label">开始时间</label>
                            <div class="col-sm-8">
                                <input v-model="plan.startTime"
                                       v-validate:starttime="{
                                    required:{rule:true,message:'请选择开始日期'}
                                }"
                                       v-el:start-date
                                       readonly
                                       maxlength="20"
                                       data-tabindex="1"
                                       id="starttime" name="starttime" type="text" class="form-control"
                                       placeholder="开始日期">
                                <span v-cloak v-if="$validation.starttime.invalid && submitBtnClick"
                                      class="help-absolute">
                            <span v-for="error in $validation.starttime.errors">
                              {{error.message}} {{($index !== ($validation.starttime.errors.length -1)) ? ',':''}}
                            </span>
                        </span>
                            </div>
                        </div>

                        <div class="form-group"
                             :class="{'has-error':$validation.endtime.invalid && submitBtnClick}">
                            <label for="endTime" class="col-sm-2 control-label">结束日期</label>
                            <div class="col-sm-8">
                                <input v-model="plan.endTime"
                                       v-validate:endtime="{
                                    required:{rule:true,message:'请选择开始日期'}
                                }"
                                       v-el:end-date
                                       readonly
                                       maxlength="20"
                                       data-tabindex="1"
                                       id="endTime" name="endTime" type="text" class="form-control"
                                       placeholder="结束日期">
                                <span v-cloak v-if="$validation.endtime.invalid && submitBtnClick"
                                      class="help-absolute">
                            <span v-for="error in $validation.endtime.errors">
                              {{error.message}} {{($index !== ($validation.endtime.errors.length -1)) ? ',':''}}
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
    </div>
    <%@include file="/include/admin/footer.jsp" %>
    <!--右侧部分结束-->
</div>
<!-- template 应该永远比 script靠前 -->
<script src="/static/admin/js/mixins.js"></script>
<script src="/static/admin/js/filters.js"></script>
<script src="/static/admin/js/components/breadcrumb.js"></script>
<script src="/static/admin/js/utils.js"></script>
<!-- 激活侧边栏 -->
<script>
    $('#mainMenu').addClass('active');
</script>
<!-- 主方法，每页均需引用 -->
<script src="/static/admin/js/main.js"></script>
<script src="/static/admin/js/index.js"></script>
</body>
</html>