<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<!--左侧导航开始-->
<nav id="nav" class="navbar-default navbar-static-side" role="navigation">
    <div class="nav-close">
        <i class="fa fa-times-circle"></i>
    </div>
    <div id="navUser" class="sidebar-collapse">
        <div class="nav-header">
            <div v-cloak v-show="user">
                <div class="dropdown profile-element">
          <span>
          </span>
                    <a data-toggle="dropdown" class="dropdown-toggle" href="#">
                        <span class="clear">
                          <span class="block m-t-xs">
                            <strong v-text="user.name" class="font-bold"></strong>
                          </span>
                        </span>
                    </a>
                </div>
                <div v-text="user.companyNickname" class="logo-element"></div>
            </div>
        </div>
        <!-- 左侧菜单 start-->
        <ul class="nav metismenu" id="sideMenu">
            <shiro:hasPermission name="index:list">
                <li id="mainMenu">
                    <a href="/index">
                        <i class="fa fa-home"></i>
                        <span class="nav-label">主页</span>
                    </a>
                </li>
            </shiro:hasPermission>
            <!-- 客户管理  -->
            <shiro:hasPermission name="customer:menu">
            <li id="customerMenu">
                <a href="#">
                    <i class="fa fa-edit"></i>
                    <span class="nav-label">客户管理</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level sidebar-nav">
                    <shiro:hasPermission name="customer:menu-store">
                        <li id="storeCustomer">
                            <a href="/admin/user/storeCustomer">
                                <i class="fa fa-edit"></i>
                                <span class="nav-label">门店客户库</span>
                            </a>
                        </li>
                     </shiro:hasPermission>
                    <shiro:hasPermission name="customer:menu-group">
                        <li id="groupCustomer">
                            <a href="/admin/user/groupCustomer">
                                <i class="fa fa-edit"></i>
                                <span class="nav-label">集团客户库</span>
                            </a>
                        </li>
                     </shiro:hasPermission>

                    <shiro:hasPermission name="customer:menu-black">
                        <li id="blackCustomer">
                            <a href="/admin/user/blackCustomer">
                                <i class="fa fa-edit"></i>
                                <span class="nav-label">无需回访客户库</span>
                            </a>
                        </li>
                    </shiro:hasPermission>
                </ul>
            </li>
             </shiro:hasPermission>
            <%--发起工单--%>
            <shiro:hasPermission name="workorder:add">
                <li id="workOrderAddMenu">
                    <a href="/workorder/add">
                        <i class="fa fa-edit"></i>
                        <span class="nav-label">发起工单</span>
                    </a>
                </li>
            </shiro:hasPermission>
            <%--工单处理--%>
            <shiro:hasPermission name="workorder:list">
                <li id="workOrderList">
                    <a href="/workorder/list">
                        <i class="glyphicon glyphicon-stats"></i>
                        <span class="nav-label">工单处理</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level sidebar-nav">
                        <shiro:hasPermission name="workorder:menu-create">
                            <li id="create">
                                <a href="/workorder/list?status=CREATE">
                                    <i class="fa fa-edit"></i>
                                    <span class="nav-label">未派单</span>
                                </a>
                            </li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="workorder:menu-received">
                            <li id="received">
                                <a href="/workorder/list?status=RECEIVED">
                                    <i class="fa fa-edit"></i>
                                    <span class="nav-label">已接收</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="workorder:menu-processing">
                            <li id="processing">
                                <a href="/workorder/list?status=PROCESSING">
                                    <i class="fa fa-edit"></i>
                                    <span class="nav-label">处理中</span>
                                </a>
                            </li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="workorder:menu-refusedagain">
                            <li id="refusedagain">
                                <a href="/workorder/list?status=REFUSEDAGAIN">
                                    <i class="fa fa-edit"></i>
                                    <span class="nav-label">申诉无效</span>
                                </a>
                            </li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="workorder:menu-assign">
                            <li id="assign">
                                <a href="/workorder/list?status=ASSIGN">
                                    <i class="fa fa-edit"></i>
                                    <span class="nav-label">待分配</span>
                                </a>
                            </li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="workorder:menu-urge">
                            <li id="urge">
                                <a href="/workorder/list?status=URGE">
                                    <i class="fa fa-edit"></i>
                                    <span class="nav-label">催单</span>
                                </a>
                            </li>
                        </shiro:hasPermission>

                        <shiro:hasPermission name="workorder:treat-time">
                            <li id="treatTime">
                                <a href="/workorder/approvalList">
                                    <i class="fa fa-edit"></i>
                                    <span class="nav-label">待申核</span>
                                </a>
                            </li>
                        </shiro:hasPermission>

                    </ul>
                </li>
            </shiro:hasPermission>
            <!--工单回访 -->
             <shiro:hasPermission name="workreturn:menu">
            <li id="workOrderVisit">
                <a href="#">
                    <i class="glyphicon glyphicon-stats"></i>
                    <span class="nav-label">工单回访</span>

                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level sidebar-nav">

                    <shiro:hasPermission name="workreturn:menu-completed">
                    <li id="completedWordOrder">
                        <a href="/workorder/workOrderListWithComplete">
                            <i class="fa fa-edit"></i>
                            <span class="nav-label">已完成</span>
                        </a>
                    </li>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="workreturn:menu-invalid">
                    <li id="invalidReturnVisit">
                        <a href="/workorder/workOrderListWithInvalid">
                            <i class="fa fa-edit"></i>
                            <span class="nav-label">暂无评价</span>
                        </a>
                    </li>
                    </shiro:hasPermission>

                     <shiro:hasPermission name="workreturn:menu-unsuccessful">
                    <li id="unsuccessful">
                        <a href="/workorder/workOrderListWithUnsuccessful">
                            <i class="fa fa-edit"></i>
                            <span class="nav-label">不再回访</span>
                        </a>
                    </li>
                    </shiro:hasPermission>
                </ul>
            </li>
             </shiro:hasPermission>
            <%--工单库--%>
            <shiro:hasPermission name="workorder:menu-ordermanage">
                <li id="ordermanage">
                    <a href="#">
                        <i class="glyphicon glyphicon-stats"></i>
                        <span class="nav-label">工单库</span>

                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level sidebar-nav">
                            <shiro:hasPermission name="workorder:menu-store">
                        <li id="storeWorkOrder">
                            <a href="/workorder/storeWorkOrderList">
                                <i class="fa fa-edit"></i>
                                <span class="nav-label">门店库</span>
                            </a>
                        </li>
                             </shiro:hasPermission>
                            <shiro:hasPermission name="workorder:menu-group">
                        <li id="groupWorkOrder">
                            <a href="/workorder/groupWorkOrderList">
                                <i class="fa fa-edit"></i>
                                <span class="nav-label">集团库</span>
                            </a>
                        </li>
                             </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>

             <shiro:hasPermission name="reportCenter:menu">
            <li id="reportCenter">
                <a href="#">
                    <i class="glyphicon glyphicon-stats"></i>
                    <span class="nav-label">报表中心</span>
                    <span class="fa arrow"></span>
                </a>
                <ul class="nav nav-second-level sidebar-nav">
                    <shiro:hasPermission name="reportCenter:menu-groupDeal">
                    <li id="groupProcessingReport">
                        <a href="/reportCenter/workDealGroup">
                            <i class="fa fa-edit"></i>
                            <span class="nav-label">集团工单处理报表</span>
                        </a>
                    </li>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="reportCenter:menu-groupReturn">
                    <li id="groupReturnReport">
                        <a href="/reportCenter/workReturnGroup">
                            <i class="fa fa-edit"></i>
                            <span class="nav-label">集团工单回访报表</span>
                        </a>
                    </li>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="reportCenter:menu-deal">
                    <li id="processingReport">
                        <a href="/reportCenter/workDeal">
                            <i class="fa fa-edit"></i>
                            <span class="nav-label">工单处理报表</span>
                        </a>
                    </li>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="reportCenter:menu-return">
                    <li id="returnReport">
                        <a href="/reportCenter/workReturn">
                            <i class="fa fa-edit"></i>
                            <span class="nav-label">工单回访报表</span>
                        </a>
                    </li>
                    </shiro:hasPermission>

                    <shiro:hasPermission name="workorder:menu-completed">
                    <li id="personalReport">
                        <a href="#">
                            <i class="fa fa-edit"></i>
                            <span class="nav-label">个人报表</span>
                        </a>
                    </li>
                    </shiro:hasPermission>

                </ul>
            </li>
             </shiro:hasPermission>

            <shiro:hasPermission name="personalPerformance:menu">
            <li id="personalPerformance">
                <a href="/reportCenter/personalWork">
                    <i class="glyphicon glyphicon-stats"></i>
                    <span class="nav-label">个人业绩</span>
                </a>
            </li>
             </shiro:hasPermission>
            <shiro:hasPermission name="admin:setting">
                <li id="setting">
                    <a href="">
                        <i class="glyphicon glyphicon-stats"></i>
                        <span class="nav-label">系统设置</span>
                        <span class="fa arrow"></span>
                    </a>
                    <ul class="nav nav-second-level sidebar-nav">

                        <shiro:hasPermission name="admin:setting-timeLimit">
                      		<li id="timeLimit">
                                <a href="/admin/timeLimit">
                                    <i class="fa fa-edit"></i>
                                    <span class="nav-label">处理时限</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="admin:setting-synFail">
                        <li id="synList">
                            <a href="/workorder/synList">
                                <i class="fa fa-edit"></i>
                                <span class="nav-label">同步失败</span>
                            </a>
                        </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="admin:setting-user">
                            <li id="userManager">
                                <a href="/admin/users">
                                    <i class="glyphicon glyphicon-stats"></i>
                                    <span class="nav-label">用户管理</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="admin:setting-role">
                            <li id="roleManager">
                                <a href="/admin/roles">
                                    <i class="glyphicon glyphicon-stats"></i>
                                    <span class="nav-label">角色管理</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="admin:setting-organization">
                            <li id="organizationMenu">
                                <a href="/organization/list">
                                    <i class="fa fa-edit"></i>
                                    <span class="nav-label">组织架构</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="admin:setting-question">
                            <li id="depQuestion">
                                <a href="/organization/question">
                                    <i class="glyphicon glyphicon-stats"></i>
                                    <span class="nav-label">部门问题</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="admin:setting-dictionary">
                            <li id="dictionaryMenu">
                                <a href="/dictionary/list">
                                    <i class="fa fa-edit"></i>
                                    <span class="nav-label">数据字典</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                        <shiro:hasPermission name="admin:setting-brand">
                            <li id="brandMenu">
                                <a href="/brand/list">
                                    <i class="fa fa-edit"></i>
                                    <span class="nav-label">品牌字典</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                        
                        <shiro:hasPermission name="admin:setting-proCatBrand">
                            <li id="proCatBrandMenu">
                                <a href="/proCatBrand/list">
                                    <i class="fa fa-edit"></i>
                                    <span class="nav-label">品牌事项分类</span>
                                </a>
                            </li>
                        </shiro:hasPermission>
                    </ul>
                </li>
            </shiro:hasPermission>


            <%--<shiro:hasPermission name="workorder:statistics">--%>
            <%--<li id="orderStatistics">--%>
            <%--<a href="/workorder/statistics">--%>
            <%--<i class="glyphicon glyphicon-stats"></i>--%>
            <%--<span class="nav-label">报表统计</span>--%>
            <%--</a>--%>
            <%--</li>--%>
            <%--</shiro:hasPermission>--%>

             <li id="resetPassword">
                 <a href="/mdni/updPwd">
                    <i class="glyphicon glyphicon-stats"></i>
                     <span class="nav-label">修改密码</span>
                 </a>
           </li>
        </ul>
        <!-- 左侧菜单 end-->
    </div>
</nav>
<!--左侧导航结束-->