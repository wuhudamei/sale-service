var vueIndex = null;
+(function (DameiUtils) {
    $('#workOrderVisit').addClass('active');
    $('#completedWordOrder').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
        	// 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '',
                name: '工单回访'
            }, {
                path: '/',
                name: '已完成',
                active: true // 激活面包屑的
            }],
            $dataTable: null,
            fUser: null,
            form: {
                keyword: '',
                //客户要求回电时间
                customerFeedbackTime: null,
                //完成时间
                treamentTime: null,
                startDate: '',
                endDate: '',
                //事项分类
                questionType1: '',
                complaintType: '',
                importantDegree1: '',
                companyId: DameiUser.company,
                departmentId: '',
                //过滤掉黑名单用户
                blackFlag: false
            },
            //事项分类
            problemCategories: [],
            //投诉原因
            complaintTypes: [],
            //公司
            organizations: [],
            //重要程度
            importances: [],
            //部门
            departments: [],
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
                        return _.extend({'orderStatus': 'COMPLETED'}, params, self.form);
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
                        align: 'center'
                    }, {
                        field: 'customerName',
                        title: '客户姓名',
                        align: 'center',
                        formatter: function(value,row){
                        	return '<a onclick="vueIndex.storeParams()" href="/workorder/workOrderInfo?workOrderId='+ row.id
                        		+'&firstId=workOrderVisit&secondId=completedWordOrder" >' + value + '</a>';
                        	
                        }
                    }, {
                        field: 'customerMobile',
                        title: '客户电话',
                        align: 'center'
                    }, {
                        field: 'customerAddress',
                        title: '地址',
                        align: 'center'
                    }, {
                        field: 'problem',
                        title:'问题描述',
                        align: 'center',
                        formatter: function(value){
                        	if(value.length > 10){
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
                        field: '',
                        title:'操作',
                        align: 'center',
                        formatter: function(value,row){
                        	return '<a onclick="vueIndex.storeParams()" href="/workorder/visit?source=completed&orderStatus='+ row.orderStatus +'&workOrderId='+ row.id
                                + '" class="m-r-xs btn btn-xs btn-primary">回访</a>';
                        }
                    }]
                });

                //点击查看详情!
                self.$dataTable.on('click', '[data-toggle="popover"]', function (e) {
                    $(this).popover({'animation':true});
                });

            },

            // 查询门店
            queryOrganization: function () {
                var self = this;
                self.$http.get('/api/org/findAll').then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.organizations = res.data;
                    }
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
            //查询部门
            findOrganizations: function(flag){
            	var self = this;
            	var url = "/api/org/findDepartment/" + self.form.companyId;
            	if(flag){
                    //当标记为true时,清空选中的
                    self.form.departmentId = '';
                }

            	if(self.form.companyId){
            		self.$http.get(url).then(function (res) {
            			if (res.data.code == 1) {
            				self.departments = res.data.data;
            			}
            		});
            	}
            },
            //存储搜索条件
            storeParams: function () {
                var self = this;
                //将搜索条件都存储到sessionStorage中
                sessionStorage.setItem("pageNumber",self.$dataTable.bootstrapTable("getOptions").pageNumber);
                sessionStorage.setItem("keyword",self.form.keyword );
                sessionStorage.setItem("questionType1",self.form.questionType1 );
                sessionStorage.setItem("complaintType",self.form.complaintType );
                sessionStorage.setItem("importantDegree1",self.form.importantDegree1 );
                sessionStorage.setItem("companyId",self.form.companyId );
                sessionStorage.setItem("departmentId",self.form.departmentId );
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
                self.form.questionType1 = sessionStorage.getItem("questionType1") || '';
                self.form.complaintType = sessionStorage.getItem("complaintType") || '';
                self.form.importantDegree1 = sessionStorage.getItem("importantDegree1") || '';
                //如果获取不到companyId,那就给默认值:当前用户的company
                self.form.companyId = sessionStorage.getItem("companyId") || DameiUser.company;
                self.form.departmentId = sessionStorage.getItem("departmentId") || '';
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
            this.queryOrganization();
            //加载搜索条件
            this.loadParams();
        },
        ready: function () {
            this.drawTable();
            this.activeDatepicker();
            //加载部门--参数:表示不清空departmentId
            this.findOrganizations(false);
        }
    });

})
(this.DameiUtils);