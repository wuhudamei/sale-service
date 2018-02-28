<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="keywords" content="">
  <meta name="description" content="">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
  <meta name="renderer" content="webkit">
  <meta http-equiv="Cache-Control" content="no-siteapp"/>
  <title>报表系统</title>
  <!-- style -->
  <link rel="shortcut icon" href="/static/admin/img/favicon.ico">
  <link rel="stylesheet" href="/static/admin/css/lib.css">
  <%--<link rel="stylesheet" href="/static/admin/css/style.css">--%>
  <script src="/static/admin/js/lib.js"></script>
  <script src="${ctx}/static/admin/vendor/echarts/echarts.common.min.js"></script>

  <script>
      // 设置为中文
      try {
          $.fn.bootstrapTable.defaults.locale = 'zh-CN';

          var RocoVueMixins = window.RocoVueMixins = {};
          var RocoVueComponents = window.RocoVueComponents = {};

          // 打开 Vue 调试器
          Vue.config.devtools = /localhost|192\./.test(window.location.href);

          // Vue resource设置
          // 模拟表单提交
//    Vue.http.options.headers = {
//      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8;'
//    };
//    Vue.http.options.emulateJSON = true;

          // Vue resource 添加拦截器
          Vue.http.interceptors.push(function (request, next) {
              // get 请求加时间戳参数去缓存
              if (request.method.toLowerCase() == 'get') {
                  request.params._ = Date.now();
              }

              //响应处理
              next(function (response) {
                  //安卓下框架不会自动json化
                  try {
                      if (!Vue.util.isObject(response.data)) {
                          response.data = JSON.parse(response.body);

                      }
                  } catch (e) {
                      response.data = null;
                      console.log(e.toString());
                  }

                  if (response.data.statusCode == 0) {
                      Vue.toastr.error(response.data.message);
                  }
                  if (response.data.statusCode == -1) {
                      var from = window.location.href;
                      window.location.href = ctx + '/login?from=' + encodeURIComponent(from);
                  }
                  // 约定公共 code 处理
                  // if(response.data.errorcode == 88) {
                  //   window.location.href = ctx + '/login?path=' + window.location.pathname;
                  // }else if(response.data.errorcode != 0){
                  //   alert(response.data.message);
                  // }
              });
          });
      } catch (e) {
          console.log(e.toString())
      }
  </script>
  <script src="${ctx}/static/admin/js/utils.js"></script>
  <script>
    var ctx = '${ctx}';
  </script>
  <style>
    .week-month {
      display: inline-block;
      width: 49%;
    }
    .panel-body {
      padding: 10px;
    }

    .left {
      float: left;
    }
    .right {
      float: right;
    }
    .clearFix {
      overflow: hidden;
    }
    dl {
      margin-top: 10px;
      margin-bottom: 10px;
    }
    .pie-top {
      margin-top: 10px;
    }

    .big-header {
      background-color:#1ab394 !important;
    }

    .num-stl {
      font-weight: bold;
    }

    [v-cloak] {
      display: none;
    }
  </style>
</head>
<body>
<div class="wrapper wrapper-content" id="container" v-cloak>
  <!--转单状况统计-->
  <div class="panel panel-default">
    <div class="panel-heading big-header">转单状况统计</div>
    <div class="panel-body">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">本日({{currDay}})</h3>
        </div>
        <div class="panel-body clearFix">
          <dl class="week-month left">
            <dd>新大定数:&nbsp; <span class="num-stl">{{change.dayData.BigCount}}</span></dd>
            <dd>新转设计数:&nbsp; <span class="num-stl">{{change.dayData.ToDesign}}</span></dd>
            <dd>新转单数:&nbsp; <span class="num-stl">{{change.dayData.ToConstruct}}</span></dd>
            <dd>新竣工数:&nbsp; <span class="num-stl">{{change.dayData.ToComplete}}</span></dd>
            <dd>新退单数:&nbsp; <span class="num-stl">{{change.dayData.ToQuit}}</span></dd>
          </dl>
          <div class="week-month right pie-top">
            <div id="firstOne" style="height: 100px;"></div>
          </div>
        </div>
      </div>

      <div class="panel panel-default week-month">
        <div class="panel-heading">
          <h3 class="panel-title">本周({{currWeek}})</h3>
        </div>
        <div class="panel-body">
          <dl class="">
            <dd>新大定数:&nbsp; <span class="num-stl">{{change.weekData.BigCount}}</span></dd>
            <dd>新转设计数:&nbsp; <span class="num-stl">{{change.weekData.ToDesign}}</span></dd>
            <dd>新转单数:&nbsp; <span class="num-stl">{{change.weekData.ToConstruct}}</span></dd>
            <dd>新竣工数:&nbsp; <span class="num-stl">{{change.weekData.ToComplete}}</span></dd>
            <dd>新退单数:&nbsp; <span class="num-stl">{{change.weekData.ToQuit}}</span></dd>
          </dl>
        </div>
      </div>
      <div class="panel panel-default week-month">
        <div class="panel-heading">
          <h3 class="panel-title">本月({{currMonth}})</h3>
        </div>
        <div class="panel-body">
          <dl class="">
            <dd>新大定数:&nbsp; <span class="num-stl">{{change.monthData.BigCount}}</span></dd>
            <dd>新转设计数:&nbsp; <span class="num-stl">{{change.monthData.ToDesign}}</span></dd>
            <dd>新转单数:&nbsp; <span class="num-stl">{{change.monthData.ToConstruct}}</span></dd>
            <dd>新竣工数:&nbsp; <span class="num-stl">{{change.monthData.ToComplete}}</span></dd>
            <dd>新退单数:&nbsp; <span class="num-stl">{{change.monthData.ToQuit}}</span></dd>
          </dl>
        </div>
      </div>

      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">近三十日趋势图({{nearThirty}})</h3>
        </div>
        <div class="panel-body">
          <div id="firstFour" style="height: 250px;"></div>
        </div>
      </div>
    </div>
  </div>
  <!--财务应收状况统计-->
  <div class="panel panel-default">
    <div class="panel-heading big-header">财务应收状况统计</div>
    <div class="panel-body">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">本日应收({{currDay}})</h3>
        </div>
        <div class="panel-body clearFix">
          <dl class="week-month left">
            <dd>首期款:&nbsp; <span class="num-stl">{{receivable.dayData.InitialCharge}} </span>万元</dd>
            <dd>拆改费:&nbsp; <span class="num-stl">{{receivable.dayData.RemoveCharge}} </span>万元</dd>
            <dd>中期款:&nbsp; <span class="num-stl">{{receivable.dayData.MiddleCharge}} </span>万元</dd>
            <dd>尾款:&nbsp; <span class="num-stl">{{receivable.dayData.LastCharge}} </span>万元</dd>
            <dd class="num-stl">合计:&nbsp; <span class="num-stl">{{receivable.dayData.TotalCharge}} </span>万元</dd>
          </dl>
          <div class="week-month right pie-top">
            <div id="secondOne" style="height: 100px;"></div>
          </div>
        </div>
      </div>
      <div class="panel panel-default week-month">
        <div class="panel-heading">
          <h3 class="panel-title">本周应收({{currWeek}})</h3>
        </div>
        <div class="panel-body">
          <dl class="">
            <dd>首期款:&nbsp; <span class="num-stl">{{receivable.weekData.InitialCharge}} </span>万元</dd>
            <dd>拆改费:&nbsp; <span class="num-stl">{{receivable.weekData.RemoveCharge}} </span>万元</dd>
            <dd>中期款:&nbsp; <span class="num-stl">{{receivable.weekData.MiddleCharge}} </span>万元</dd>
            <dd>尾款:&nbsp; <span class="num-stl">{{receivable.weekData.LastCharge}} </span>万元</dd>
            <dd class="num-stl">合计:&nbsp; <span class="num-stl">{{receivable.weekData.TotalCharge}} </span>万元</dd>
          </dl>
        </div>
      </div>
      <div class="panel panel-default week-month">
        <div class="panel-heading">
          <h3 class="panel-title">本月应收({{currMonth}})</h3>
        </div>
        <div class="panel-body">
          <dl class="">
            <dd>首期款:&nbsp; <span class="num-stl">{{receivable.monthData.InitialCharge}} </span>万元</dd>
            <dd>拆改费:&nbsp; <span class="num-stl">{{receivable.monthData.RemoveCharge}} </span>万元</dd>
            <dd>中期款:&nbsp; <span class="num-stl">{{receivable.monthData.MiddleCharge}} </span>万元</dd>
            <dd>尾款:&nbsp; <span class="num-stl">{{receivable.monthData.LastCharge}} </span>万元</dd>
            <dd class="num-stl">合计:&nbsp; <span class="num-stl">{{receivable.monthData.TotalCharge}} </span>万元</dd>
          </dl>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">近三十日趋势图({{nearThirty}})</h3>
        </div>
        <div class="panel-body">
          <div id="secondFour" style="height: 250px;"></div>
        </div>
      </div>
    </div>
  </div>
  <!--财务现金收入流水状况统计-->
  <div class="panel panel-default">
    <div class="panel-heading big-header">财务现金收入流水状况统计</div>
    <div class="panel-body">
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">本日({{currDay}})</h3>
        </div>
        <div class="panel-body clearFix">
          <dl class="week-month left">
            <dd>预付款:&nbsp; <span class="num-stl">{{statement.dayInfo.AdvanceCharge}} </span>万元</dd>
            <dd>首期款:&nbsp; <span class="num-stl">{{statement.dayInfo.InitialCharge}} </span>万元</dd>
            <dd>拆改费:&nbsp; <span class="num-stl">{{statement.dayInfo.RemoveCharge}} </span>万元</dd>
            <dd>中期款:&nbsp; <span class="num-stl">{{statement.dayInfo.MiddleCharge}} </span>万元</dd>
            <dd>尾款:&nbsp; <span class="num-stl">{{statement.dayInfo.LastCharge}} </span>万元</dd>
            <dd>尾款后变更款:&nbsp; <span class="num-stl">{{statement.dayInfo.LastChangeCharge}} </span>万元</dd>
            <dd class="num-stl">总收入:&nbsp; <span class="num-stl">{{statement.dayInfo.TotalCharge}} </span>万元</dd>
          </dl>
          <div class="week-month right pie-top">
            <div id="threeOne" style="height: 130px;"></div>
          </div>

        </div>
      </div>
      <div class="panel panel-default week-month">
        <div class="panel-heading">
          <h3 class="panel-title">本周({{currWeek}})</h3>
        </div>
        <div class="panel-body">
          <dl class="">
            <dd>预付款:&nbsp; <span class="num-stl">{{statement.weekInfo.AdvanceCharge}} </span>万元</dd>
            <dd>首期款:&nbsp; <span class="num-stl">{{statement.weekInfo.InitialCharge}} </span>万元</dd>
            <dd>拆改费:&nbsp; <span class="num-stl">{{statement.weekInfo.RemoveCharge}} </span>万元</dd>
            <dd>中期款:&nbsp; <span class="num-stl">{{statement.weekInfo.MiddleCharge}} </span>万元</dd>
            <dd>尾款:&nbsp; <span class="num-stl">{{statement.weekInfo.LastCharge}} </span>万元</dd>
            <dd>尾款后变更款:&nbsp; <span class="num-stl">{{statement.weekInfo.LastChangeCharge}} </span>万元</dd>
            <dd class="num-stl">总收入:&nbsp; <span class="num-stl">{{statement.weekInfo.TotalCharge}} </span>万元</dd>
          </dl>
        </div>
      </div>
      <div class="panel panel-default week-month">
        <div class="panel-heading">
          <h3 class="panel-title">本月({{currMonth}})</h3>
        </div>
        <div class="panel-body">
          <dl class="">
            <dd>预付款:&nbsp; <span class="num-stl">{{statement.monthInfo.AdvanceCharge}} </span>万元</dd>
            <dd>首期款:&nbsp; <span class="num-stl">{{statement.monthInfo.InitialCharge}} </span>万元</dd>
            <dd>拆改费:&nbsp; <span class="num-stl">{{statement.monthInfo.RemoveCharge}} </span>万元</dd>
            <dd>中期款:&nbsp; <span class="num-stl">{{statement.monthInfo.MiddleCharge}} </span>万元</dd>
            <dd>尾款:&nbsp; <span class="num-stl">{{statement.monthInfo.LastCharge}} </span>万元</dd>
            <dd>尾款后变更款:&nbsp; <span class="num-stl">{{statement.monthInfo.LastChangeCharge}} </span>万元</dd>
            <dd class="num-stl">总收入:&nbsp; <span class="num-stl">{{statement.monthInfo.TotalCharge}} </span>万元</dd>
          </dl>
        </div>
      </div>
      <div class="panel panel-default">
        <div class="panel-heading">
          <h3 class="panel-title">近三十日趋势图({{nearThirty}})</h3>
        </div>
        <div class="panel-body">
          <div id="threeFour" style="height: 250px;"></div>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="${ctx}/static/js/vendor/md5/md5.min.js"></script>
<script src="${ctx}/static/admin/js/report/option.js?v=123"></script>
<script src="${ctx}/static/admin/js/report/report.js?v=123"></script>
</body>
</html>