//门店工单库
var vueIndex = null;
+(function (DameiUtils) {
    $('#ordermanage').addClass('active');
    $('#storeWorkOrder').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
        	// 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '',
                name: '工单库'
            }, {
                path: '/',
                name: '门店库',
                active: true // 激活面包屑的
            }],
            $dataTable: null,
            fUser: null,
            form: {
                keyword: '',
                //发起时间
                createDate: null,
                //客户要求回电时间
                customerFeedbackTime: null,
                //完成时间
                treamentTime: null,
                startDate: '',
                endDate: '',
                departmentId: '',
                //事项分类
                questionType1: '',
                complaintType: '',
                importantDegree1: '',
                //工单发起人
                createUserName:null,
                //查询并显示黑名单用户信息
                blackFlag: true
            },
            //事项分类
            problemCategories: [],
            //投诉原因
            complaintTypes: [], 
            //重要程度
            importances: [],
            //部门
            departments: [],
        	 //搜索条件
            params: '',
            //用于回显页码
            pageNumber: 1
        },
        methods: {
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/damei/workorder/storeWorkOrderList',
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: true, //是否分页
                    sidePagination: 'server', //服务端分页
                    pageNumber: self.pageNumber,
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', //空数据的默认显示字符
                    striped: true, //斑马线
                    maintainSelected: true, //维护checkbox选项
                    sortName: 'id', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{
                        field: 'workOrderCode',
                        title: '工单编号',
                        align: 'center',
                    	formatter: function(value){
                    		if(value && value.length > 8){
                    			var n = value.length/2;
                    			return value.substring(0,n) + "<br>" + value.substring(n);
                    		}
                    		return value;
                    	}
                    }, {
                        field: 'customerName',
                        title: '客户姓名',
                        align: 'center',
                        formatter: function(value,row){
                        	return '<a onclick="vueIndex.storeParams()" href="/workorder/workOrderInfo?workOrderId='+ row.id
                        		+'&firstId=ordermanage&secondId=storeWorkOrder" >' + value + '</a>';
                        	
                        }
                    }, {
                        field: 'customerMobile',
                        title: '客户电话',
                        align: 'center'
                    }, {
                        field: 'customerAddress',
                        title: '客户地址',
                        align: 'center',
                        width: '10%'
                    }, {
                        field: 'problem',
                        title:'问题描述',
                        align: 'center',
                        formatter: function(value){
                        	if(value && value.length > 10){
                                return '<a tabindex="0" class="btn" role="button" data-toggle="popover" data-trigger="focus" title="问题描述详情" data-content="'
                                    + value +'">' + value.substring(0, 10) + "..." +'</a>';
                        	}else{
                        		return value;
                        	}
                        }
                    }, {
                        field: 'customerFeedbackTime',
                        title:'客户要求回电时间',
                        align: 'center'
                    },  {
                        field: 'orderStatus',
                        title:'工单状态',
                        align: 'center',
                        formatter: function(value){
                        	switch (value) {
								case 'CREATE':
									return "未派单";
								case 'RECEIVED':
									return "已接收";
								case 'PROCESSING':
									return "处理中";
								case 'REFUSED':
									return "申诉";
								case 'REFUSEDAGAIN':
									return "申诉无效";
								case 'PENDING':
									return "待处理";
								case 'SATISFIED':
									return "回访满意";
								case 'COMMONLY':
									return "一般满意";
								case 'UNSATISFIED':
									return "回访不满意";
								case 'INVALIDVISIT':
									return "暂无评价";
								case 'FAILUREVISIT':
									return "失败回访";
								case 'ASSIGN':
									return "待分配";
								case 'URGE':
									return "催单";
								case 'NREPLY':
									return "待回复";
								case 'NVISIT':
									return "待回访";
								case 'CONSULTOVER':
									return "咨询完毕";
								case 'UNEXECUTED':
									return "回访未执行";
								case 'COMPLETED':
									return "已完成";
								default:
									return "";
							}
                        }
                    }, {
                        field: 'questionType1',
                        title:'事项分类',
                        align: 'center',
                        formatter: function(value){
                        	if(value){
                        		return value.name;
                        	}else{
                        		return "";
                        	}
                        }
                    }, {
                        field: 'complaintType',
                        title:'投诉原因',
                        align: 'center',
                        formatter: function(value){
                        	if(value){
                        		return value.name;
                        	}else{
                        		return "";
                        	}
                        }
                    }, {
                        field: 'importantDegree1',
                        title:'重要程度',
                        align: 'center',
                        formatter: function(value){
                        	if(value){
                        		return value.name;
                        	}else{
                        		return "";
                        	}
                        }
                    }, {
                        field: 'operationDateFromRmk',
                        title:'完成时间',
                        align: 'center'
                    }, {
                        field: 'blackFlag',
                        title:'无需回访',
                        align: 'center',
                        width: '3%',
                        formatter: function(value){
                            if(value){
                                return "是"
                            }else{
                                return "否"
                            }
                        }
                    }, {
                        field: '',
                        title:'操作',
                        align: 'center',
                        formatter: function(value,row){
                            var fragment = '';
                        	if(row.orderStatus != "SATISFIED" && row.orderStatus != "COMMONLY" && row.orderStatus != "UNSATISFIED"
                        		&& row.orderStatus != "INVALIDVISIT" && row.orderStatus != "FAILUREVISIT" 
                        		&& row.orderStatus != "COMPLETED" && row.orderStatus != "INVALIDITY"){
                        		fragment += '<a onclick="vueIndex.storeParams()" href="/workorder/reminder?source=store&workOrderId='+ row.id + '" class="m-r-xs btn btn-xs btn-primary">催单</a>';
                        	}
                            if(DameiUtils.hasPermission('workorder:btn-invalid') && row.orderStatus != "INVALIDITY"){
                                fragment += '<button class="m-r-xs btn btn-xs btn-danger" data-handle="order-invalid" data-id="' + row.id + '">无效</button>';
                            }
                            return fragment;
                        }
                    }]
                });

                //点击查看详情tip!
                self.$dataTable.on('click', '[data-toggle="popover"]', function (e) {
                    $(this).popover({'animation':true});
                });

                //无效工单
                self.$dataTable.on('click', '[data-handle="order-invalid"]', function (e) {
                    var id = $(this).data('id') + '';
                        swal({
                            title: '无效工单',
                            text: '确定将该工单置为无效？',
                            type: 'info',
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            showCancelButton: true,
                            showConfirmButton: true,
                            showLoaderOnConfirm: true,
                            confirmButtonColor: '#ed5565',
                            closeOnConfirm: false
                        }, function () {
                            var data = {
                                id: id,
                                operationType: 'INVALID',
                                orderStatus: 'INVALIDITY'
                            };
                            self.$http.post('/damei/workorder/updateWorkOrder',data).then(function (res) {
                                if (res.data.code == 1) {
                                    self.$toastr.success('操作成功!');
                                    //刷新页面
                                    vueIndex.$dataTable.bootstrapTable('refresh');
                                }
                            }).catch(function () {

                            }).finally(function () {
                                swal.close();
                            });
                        });
                });

            },
            query: function () {
            	this.$dataTable.bootstrapTable('selectPage',1);
                this.$dataTable.bootstrapTable('refresh');
            },
            // 日历初始化
            activeDatepicker: function () {
              var self = this;
              $('#startDate', self._$el).datetimepicker({});
              $('#endDate', self._$el).datetimepicker({});
            },
            //事项分类
            findProblemCategories: function(){
            	var self = this;
            	self.$http.get('/api/dict/dic/getByType?type=5').then(function (res) {
                    if (res.data.code == 1) {
                    	self.problemCategories = res.data.data;
                    }
                });
            },
            //投诉原因
            findComplaintTypes: function(){
            	var self = this;
            	self.$http.get('/api/dict/dic/getByType?type=8').then(function (res) {
                    if (res.data.code == 1) {
                    	self.complaintTypes = res.data.data;
                    }
                });
            },
            //重要程度
            findImportances: function(){
            	var self = this;
            	self.$http.get('/api/dict/dic/getByType?type=7').then(function (res) {
                    if (res.data.code == 1) {
                    	self.importances = res.data.data;
                    }
                });
            },
            //部门
            findDepartments: function(){
            	var self = this;
            	if(DameiUser.company){
	            	self.$http.get("/api/org/findDepartment/" + DameiUser.company).then(function (res) {
	                    if (res.data.code == 1) {
	                    	self.departments = res.data.data;
	                    }
	                });
            	}
            },
            //导出
            exportData: function () {
                var self = this;
                var count = -1;
                //查询本次导出的总数据
                self.$http.get('/damei/workorder/countWorkOrderByQuery?keyword=' + self.form.keyword
                    + '&createDate=' + self.form.createDate
                    + '&customerFeedbackTime=' + self.form.customerFeedbackTime
                    + '&orderStatus=' + self.form.orderStatus
                    + '&treamentTime=' + self.form.treamentTime
                    + '&startDate=' + self.form.startDate
                    + '&endDate=' + self.form.endDate
                    + '&companyId=' + self.fUser.company
                    + '&departmentId=' + self.form.departmentId
                    + '&questionType1=' + self.form.questionType1
                    + '&complaintType=' + self.form.complaintType
                    + '&importantDegree1=' + self.form.importantDegree1).then(function (res) {
                    if (res.data.code == 1) {
                        count = res.data.data;
                        if(count <= 0){
                            self.$toastr.error("暂无数据!");
                            return;
                        }else if(count > 1000){
                            self.$toastr.error("导出的数据量超过了1000条!");
                            return;
                        }else{
                            window.location.href ='/damei/workorder/exportWorkorders?keyword=' + self.form.keyword
                                + '&createDate=' + self.form.createDate
                                + '&customerFeedbackTime=' + self.form.customerFeedbackTime
                                + '&orderStatus=' + self.form.orderStatus
                                + '&treamentTime=' + self.form.treamentTime
                                + '&startDate=' + self.form.startDate
                                + '&endDate=' + self.form.endDate
                                + '&companyId=' + self.fUser.company
                                + '&departmentId=' + self.form.departmentId
                                + '&questionType1=' + self.form.questionType1
                                + '&complaintType=' + self.form.complaintType
                                + '&importantDegree1=' + self.form.importantDegree1;
                        }
                    }else{
                        self.$toastr.error("系统异常!");
                        return;
                    }
                });
            },
            //存储搜索条件
            storeParams: function () {
                var self = this;
                //将搜索条件都存储到sessionStorage中
                sessionStorage.setItem("pageNumber",self.$dataTable.bootstrapTable("getOptions").pageNumber);
                sessionStorage.setItem("keyword",self.form.keyword );
                sessionStorage.setItem("createUserName",self.form.createUserName );
                sessionStorage.setItem("questionType1",self.form.questionType1 );
                sessionStorage.setItem("complaintType",self.form.complaintType );
                sessionStorage.setItem("importantDegree1",self.form.importantDegree1 );
                sessionStorage.setItem("departmentId",self.form.departmentId );
                sessionStorage.setItem("orderStatus",self.form.orderStatus );
                if(self.form.createDate == true || self.form.createDate == 'true'){
                    sessionStorage.setItem("createDate",self.form.createDate );
                }
                if(self.form.customerFeedbackTime == true || self.form.customerFeedbackTime == 'true'){
                    sessionStorage.setItem("customerFeedbackTime",self.form.customerFeedbackTime );
                }
                if(self.form.treamentTime == true || self.form.treamentTime == 'true'){
                    sessionStorage.setItem("treamentTime",self.form.treamentTime );
                }
                sessionStorage.setItem("startDate",self.form.startDate );
                sessionStorage.setItem("endDate",self.form.endDate );
            },
            //获取存储的数据
            loadParams: function () {
                var self = this;
                self.pageNumber = parseInt(sessionStorage.getItem("pageNumber") || 1);
                self.form.keyword = sessionStorage.getItem("keyword") || '';
                self.form.createUserName = sessionStorage.getItem("createUserName") || '';
                self.form.questionType1 = sessionStorage.getItem("questionType1") || '';
                self.form.complaintType = sessionStorage.getItem("complaintType") || '';
                self.form.importantDegree1 = sessionStorage.getItem("importantDegree1") || '';
                self.form.departmentId = sessionStorage.getItem("departmentId") || '';
                self.form.orderStatus = sessionStorage.getItem("orderStatus") || '';
                self.form.createDate = sessionStorage.getItem("createDate");
                self.form.customerFeedbackTime = sessionStorage.getItem("customerFeedbackTime");
                self.form.treamentTime = sessionStorage.getItem("treamentTime");
                self.form.startDate = sessionStorage.getItem("startDate") || '';
                self.form.endDate = sessionStorage.getItem("endDate") || '';

                //加载完之后,清空sessionStorage
                sessionStorage.clear();
            }

        },
        created: function () {
            this.fUser = window.DameiUser;
            this.findProblemCategories();
            this.findComplaintTypes();
            this.findImportances();
            //加载部门
            this.findDepartments();
            this.loadParams();
        },
        ready: function () {
            this.drawTable();
            this.activeDatepicker();
        }
    });

})
(this.DameiUtils);