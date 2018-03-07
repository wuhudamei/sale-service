<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=0">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<!--[if lt IE 9]>
<meta http-equiv="refresh" content="0;ie.html" />
<![endif]-->
<link rel="shortcut icon" href="/static/admin/img/favicon.ico">
<link rel="stylesheet" href="/static/admin/css/lib.css">
<link rel="stylesheet" href="/static/admin/css/style.css">
<script src="/static/admin/js/lib.js"></script>
<script src="/static/admin/vendor/echarts/echarts.min.js"></script>
<script src="/static/admin/js/utils.js"></script>
<script src="/static/admin/js/directives/clickoutside.js"></script>


<!-- polyfill start-->
<!-- console,Function.bind -->
<!--[if lte IE 9]>
<script type="text/javascript">
(function(e){e||(e=window.console={log:function(e,t,n,r,i){},info:function(e,t,n,r,i){},warn:function(e,t,n,r,i){},error:function(e,t,n,r,i){}});if(!Function.prototype.bind){Function.prototype.bind=function(e){var t=this,n=Array.prototype.slice.call(arguments,1);return function(){return t.apply(e,Array.prototype.concat.apply(n,arguments))}}}if(typeof e.log==="object"){e.log=Function.prototype.call.bind(e.log,e);e.info=Function.prototype.call.bind(e.info,e);e.warn=Function.prototype.call.bind(e.warn,e);e.error=Function.prototype.call.bind(e.error,e)}"groupCollapsed"in e||(e.groupCollapsed=function(t){e.info("\n------------\n"+t+"\n------------")});"group"in e||(e.group=function(t){e.info("\n------------\n"+t+"\n------------")});"groupEnd"in e||(e.groupEnd=function(){});"time"in e||function(){var t={};e.time=function(e){t[e]=(new Date).getTime()};e.timeEnd=function(n){var r=(new Date).getTime(),i=n in t?r-t[n]:0;e.info(n+": "+i+"ms")}}()})(window.console)
</script>
<![endif]-->
<!-- ie10 以下没有 location origin -->
<script type="text/javascript">
    window.location.origin || (window.location.origin = window.location.protocol + "//" + window.location.hostname + (window.location.port ? ":" + window.location.port : ""));
</script>

<!-- polyfill end-->

<!-- shim start-->
<script>
    // 设置为中文
    try {
        $.fn.bootstrapTable.defaults.locale = 'zh-CN';

        var DameiVueMixins = window.DameiVueMixins = {};
        var DameiVueComponents = window.DameiVueComponents = {};

        // 打开 Vue 调试器
        Vue.config.devtools = /localhost|192\./.test(window.location.href);

        // Vue resource设置
        // 模拟表单提交
        // Vue.http.options.headers = {
        //   'Content-Type':'application/x-www-form-urlencoded;charset = UTF-8;'
        // };
        // Vue.http.options.emulateJSON = true;

        // Vue resource 编辑拦截器
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
                if (response.data.code != 1) {
                    if (response.data.code == 9) {
                        window.location.href = "/login";
                    } else if (response.data.code == 0) {
                        Vue.toastr.error(response.data.message);
                    } else {
                        Vue.toastr.error(response.data.message);
                    }
                }
            });
        });
    } catch (e) {
        console.log(e.toString())
    }
</script>
<script id="userScript">
    window.DameiUser = {
        userId: '<shiro:principal property="id"/>',
        account: '<shiro:principal property="orgCode"/>',
        name: '<shiro:principal property="name" />',
        mobile: '<shiro:principal property="mobile" />',
        company: '<shiro:principal property="company" />',
        companyCode: '<shiro:principal property="companyCode" />',
        department: '<shiro:principal property="department"/>',
        departmentCode: '<shiro:principal property="departmentCode"/>',
        departmentType: '<shiro:principal property="depType"/>',
        departmentName: '<shiro:principal property="departmentName"/>',
        roles: '<shiro:principal property="roles" />',
        permissions: '<shiro:principal property="permissions" />'
    }
</script>