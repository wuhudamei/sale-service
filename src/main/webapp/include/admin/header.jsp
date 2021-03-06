<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="header fixed row border-bottom">
  <header class="navbar navbar-static-top"
    role="navigation" style="margin-bottom: 0">
    <div class="navbar-header">
      <div class="navbar-brand">
        <img src="/static/img/logo.png" style="width:200px" alt="大美">
      </div>

      <a id="toggleMenuMD"
        class="hidden-xs navbar-minimalize minimalize-styl-2 btn btn-primary" href="javascript:;">
        <i class="fa fa-bars"></i>
      </a>
      <a
        id="toggleMenuXS"
        href="javascript:;"
        data-clicked="false"
        class="hidden-sm hidden-lg hidden-md navbar-minimalize minimalize-styl-2 btn btn-primary pull-right">
        <i class="fa fa-bars"></i>
      </a>

      <ul class="nav navbar-top-links navbar-right">
        <li class="dropdown hidden-xs">
          <a class="right-sidebar-toggle" aria-expanded="false" href="${ctx }/damei/logout">
            <i class="fa fa fa-sign-out"></i> 退出
          </a>
        </li>
      </ul>
    </div>
  </header>
</div>