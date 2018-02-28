var order;
var orderList;
var operation;
var time;
+(function (RocoUtils,moment) {
    $('#workOrderList').addClass('active');
    orderList = new Vue({
        validators: {
            laterThanStart: function (val, startTime) {
                try {
                    var end = moment(val);
                    return end.isAfter(startTime);
                } catch (e) {
                    return false;
                }
            }
        },
        el: '#container',
        mixins: [RocoVueMixins.DataTableMixin],
        data: {
            user:_.extend({}, window.RocoUser),
            fUser: null,
            deal:false, //是否显示延期列
            form: {
                status: '',
                keyword: '',
                //发起时间
                createDate: null,
                fenpaiDate: null,
                refuseDate: null,
                refusedagainDate: null,
                expectDate: null,
                faqiDate: null,
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
            },
            //事项分类
            problemCategories: [],
            //投诉原因
            complaintTypes: [],
            //重要程度
            importances: [],
            //部门
            departments: [],
            _$el: null, //自己的 el $对象
            _$dataTable: null, //datatable $对象
            //用于回显页码
            pageNumber: 1
        },
        methods: {
            //返回
            cancel: function(){
                window.history.go(-1);
            },
            addActiveClass: function (type) {
                $('#workOrderList').addClass('active');
                switch (type) {
                    case 'CREATE':
                        $('#create').addClass('active');
                        break;
                    case 'RECEIVED':
                        $('#received').addClass('active');
                        break;
                    case 'PROCESSING':
                        $('#processing').addClass('active');
                        break;
                    case 'REFUSEDAGAIN':
                        $('#refusedagain').addClass('active');
                        break;
                    case 'ASSIGN':
                        $('#assign').addClass('active');
                        break;
                    case 'URGE':
                        $('#urge').addClass('active');
                        break;
                    default:
                        $('#workOrderList').addClass('active');
                        break;
                }
            },
            activeDatePicker: function () {
                var self = this;
                $(this.$els.startDate).datetimepicker({
                    format: 'yyyy-mm-dd',
                    minView: 2,
                    autoSize: true
                });
                $(this.$els.endDate).datetimepicker({
                    format: 'yyyy-mm-dd',
                    minView: 2,
                    autoSize: true
                });
            },
            //导出
            exportWorkOrder: function () {
                var self = this;
                var keyword = self.form.keyword;
                var queryLiableType1 = self.form.queryLiableType1;
                var status = self.form.status;
                var liableId = self.form.liableId;
                var manage = self.form.manage;
                var srcId = self.form.srcId;
                var receptionStartTime = self.form.receptionStartTime;
                var receptionEndTime = self.form.receptionEndTime;
                var personName = self.form.personName;
                window.open('/mdni/workorder/export?keyword=' + keyword + '&queryLiableType1=' +
                    queryLiableType1 + '&status=' + status + '&liableId=' + liableId + '&srcId=' + srcId + '&receptionStartTime=' +
                    receptionStartTime + '&receptionEndTime=' + receptionEndTime + '&personName=' + personName + '&manage=' + manage);
            },
            fetchCompany: function () {
                var self = this;
                this.$http.get('/api/org/findAll').then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                        self.companies = res.data;
                    }
                })
            },
            queryImportantDegree: function () {//获取责任类别1
                var self = this;
                self.$http.get('/api/dict/dic/getByType', {params: {'type': 3}}).then(function (res) {
                    if (res.data.code == 1) {
                        self.queryLiableTypes = res.data.data;
                    }
                }).catch(function () {
                }).finally(function () {
                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
                this.$dataTable.bootstrapTable('refresh', {
                    pageNumber: 1
                })
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/mdni/workorder/workOrderList',
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
                    sortName: 'workOrderCode', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{
                        field: 'id',
                        title: '工单ID',
                        align: 'center',
                        sortable: true,
                        visible: false,
                        order: 'desc'
                    }, {
                        field: 'workOrderCode',
                        title: '工单编号',
                        align: 'center',
                        sortable: true,
                        visible: true
                    }, {
                        field: 'customerId',
                        title: "客户ID",
                        align: 'center',
                        sortable: true,
                        visible: false
                    }, {
                        field: 'customerName',
                        title: "客户姓名",
                        align: 'center',
                        sortable: true,
                        visible: true,
                        formatter: function(value,row){
                            if(self.form.status== 'CREATE') {
                                return value;
                            }
                            if(self.form.status== 'RECEIVED') {

                                return '<a onclick="orderList.storeParams()" href="/workorder/workOrderInfo?workOrderId=' + row.id + '&firstId=workOrderList&secondId=received" >' + value + '</a>';
                            }
                            if(self.form.status== 'PROCESSING') {
                                return '<a onclick="orderList.storeParams()" href="/workorder/workOrderInfo?workOrderId=' + row.id + '&firstId=workOrderList&secondId=processing" >' + value + '</a>';
                            }
                            if(self.form.status== 'REFUSEDAGAIN') {
                                return '<a onclick="orderList.storeParams()" href="/workorder/workOrderInfo?workOrderId=' + row.id + '&firstId=workOrderList&secondId=refusedagain" >' + value + '</a>';
                            }
                            if(self.form.status== 'ASSIGN') {
                                return '<a onclick="orderList.storeParams()" href="/workorder/workOrderInfo?workOrderId=' + row.id + '&firstId=workOrderList&secondId=assign" >' + value + '</a>';
                            }
                            if(self.form.status== 'URGE') {
                                return '<a onclick="orderList.storeParams()" href="/workorder/workOrderInfo?workOrderId=' + row.id + '&firstId=workOrderList&secondId=urge" >' + value + '</a>';
                            }
                        }
                    }, {
                        field: 'customerMobile',
                        title: "电话",
                        align: 'center',
                        sortable: true
                    }, {
                        field: 'customerAddress',
                        title: "地址",
                        align: 'center',
                        sortable: true,
                        formatter: function (val, row, index) {
                            if(val) {
                                if (val.length > 5) {
                                    return val.substring(0, 5) + "...";
                                } else {
                                    return val;
                                }
                            }
                        }
                    }, {
                        field: 'problem',
                        title: "问题描述",
                        align: 'center',
                        sortable: true,
                        formatter: function (val, row, index) {
                            if (val && val.length > 5) {
                                return '<a tabindex="0" class="btn" role="button" data-toggle="popover" data-trigger="focus" title="问题描述详情" data-content="'+val+'">'
                                    + val.substring(0, 5) + "..." +'</a>';
                            } else {
                                return val;
                            }
                        }
                    }, {
                        field: 'customerFeedbackTime',
                        title: '客户要求回电时间',
                        align: 'center',
                        sortable: true,
                        visible: false,
                    }, {
                        field: 'questionType1',
                        title: '事项分类',
                        align: 'center',
                        sortable: true,
                        formatter: function (val, row, index) {
                            if (val) {
                                return val.name;
                            }
                        },
                        visible: true
                    }, {
                        field: 'liableType1',
                        title: "投诉原因",
                        align: 'center',
                        sortable: true,
                        visible: false,
                        formatter: function (val, row, index) {
                            if(val){
                            return val.name;
                            }
                        },
                        visible: true
                    }, {
                        field: 'importantDegree1',
                        title: '重要程度',
                        align: 'center',
                        sortable: true,
                        formatter: function (val, row, index) {
                            if (val) {
                                return val.name;
                            }
                        },
                        visible: true
                    }, {
                        field: 'fenpaiDate',
                        title: '分派时间',
                        align: 'center',
                        sortable: true,
                        visible: self.form.status == 'CREATE' || self.form.status == 'RECEIVED'
                        || self.form.status == 'PROCESSING' || self.form.status == 'URGE'
                    },
                    {
                        field: 'treamentTime',
                        title: '预计完成时间',
                        align: 'center',
                        sortable: true,
                        visible: self.form.status == 'PROCESSING'
                    },{
                        field: 'refusedagainTime',
                        title: '转派时间',
                        align: 'center',
                        sortable: true,
                        visible: self.form.status == 'REFUSEDAGAIN'
                    }, {
                        field: 'refuseTime',
                        title: '申诉时间',
                        align: 'center',
                        sortable: true,
                        visible: self.form.status == 'ASSIGN'
                    }, {
                        field: 'urgeTimes',
                        title: '催单次数',
                        align: 'center',
                        sortable: true,
                        visible: self.form.status == 'URGE',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if (row.read) {
                                fragment += '<a class="text-red" data-handle="order-reply" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                            } else {
                                fragment += '<a data-handle="order-reply" data-index="' + index + '" data-id="' + row.id + '">' + value + '</a>';
                            }
                            return fragment;
                        }
                    }, {
                        field: 'orderStatus',
                        title: "事项状态",
                        align: 'center',
                        sortable: true,
                        visible: self.form.status == 'ORDERMANAGE',
                        formatter: function (value, row, index) {
                            switch (value) {
                                case 'PENDING':
                                    return '<font color="blue">待处理</font>';
                                    break;
                                case 'ASSIGN':
                                    return '<font color="blue">待分配</font>';
                                    break;
                                case 'NREPLY':
                                    return '<font color="orange">待回复</font>';
                                    break;
                                case 'NVISIT':
                                    return '<font color="brown">待回访</font>';
                                    break;
                                case 'UNEXECUTED':
                                    return '<font color="red">回访未执行</font>';
                                    break;
                                case 'COMPLETED':
                                    return '<font color="green">已解决</font>';
                                case 'CALLBACK':
                                    return '<font color="#ff4500">再联系</font>';
                                    break;
                                default:
                                    return value;
                                    break;
                            }
                        }
                    },{
                            field: 'treamentTimeUpdate',
                            title: "申请延期",
                            align: 'center',
                            sortable: true,
                            //visible: self.deal,
                            visible: false,
                            formatter: function (value, row, index) {
                                switch (value) {
                                    case '0':
                                        return '<font color="blue">申请中</font>';
                                        break;
                                    case '1':
                                        return '<font color="green">通过</font>';
                                        break;
                                    case '2':
                                        return '<font color="red">驳回</font>';
                                        break;
                                    default:
                                         return '未申请';
                                        break;
                                }
                            }
                        },
                        {
                        field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        visible: self.form.status != 'URGE',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if (row.orderStatus != "COMPLETED") {//已解决
                                if (row.orderStatus == "CREATE"&&orderList.user.departmentType!='FILIALECUSTOMERSERVICE' ) {//待处理
                                    // fragment += ('<button data-handle="order-operation" data-index="' + index + '" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-primary">处理</button>');
                                    fragment += ('<button data-handle="order-receive" data-index="' + index + '" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-primary" id="receiveId">接收</button>');

                                } else if (row.orderStatus == "RECEIVED" || row.orderStatus == "REFUSEDAGAIN") {//已接收
                                    /*fragment += ('<a href="/workorder/settle?id='
                                     + row.id + '&keyword='
                                     + self.form.keyword +'&questionType1='
                                     + self.form.questionType1 +'&complaintType='
                                     + self.form.complaintType +'&importantDegree1='
                                     + self.form.importantDegree1 +'&fenpaiDate='
                                     + self.form.fenpaiDate +'&customerFeedbackTime='
                                     + self.form.customerFeedbackTime +'&refuseDate='
                                     + self.form.refuseDate +'&refusedagainDate='
                                     + self.form.refusedagainDate +'&expectDate='
                                     + self.form.expectDate +'&faqiDate='
                                     + self.form.faqiDate +'&startDate='
                                     + self.form.startDate +'&endDate='
                                     + self.form.endDate +'&pageNumber='
                                     + pageNumber +'" class="m-r-xs btn btn-xs btn-primary">处理</a>');*/
                                    //fragment += ('<button data-handle="order-operation" data-index="' + index + '" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-primary">处理</button>');
                                    fragment += ('<a onclick="orderList.storeParams()" href="/workorder/settle?id='+ row.id + '" class="m-r-xs btn btn-xs btn-primary">处理</a>');
                                    if (RocoUser.departmentName == "材料部"){
                                        //已接受 仅材料部可见 可以随时转派
                                        fragment += ('<a onclick="orderList.storeParams()" href="/workorder/turnSend?id=' + row.id + '" class="m-r-xs btn btn-xs btn-warning">转派</a>');
                                    }
                                } else if (row.orderStatus == "PROCESSING") {//已接收
                                    // if (RocoUtils.hasPermission('workorder:deal') || RocoUtils.hasRole('超级管理员') || RocoUtils.isLoginUser(row.liablePerson.id)) {
                                    fragment += ('<a onclick="orderList.storeParams()" href="/workorder/followup?id=' + row.id + '" class="m-r-xs btn btn-xs btn-primary">跟进</a>');
                                     if (RocoUtils.hasPermission('workorder:time-update')){
                                        if (row.treamentTimeUpdate != '0') {
                                            fragment += ('<button data-handle="order-time"  data-id="' + row.id + '" data-time="' + row.treamentTime + '" type="button" class="m-r-xs btn btn-xs btn-warning">申请延期</button>');
                                        }
                                        if (row.treamentTimeUpdate == '2') {
                                            fragment += ('<button data-handle="order-back"  data-id="' + row.id + '"  type="button" class="m-r-xs btn btn-xs btn-danger">驳回记录</button>');
                                        }
                                    }
                                    // }

                                } else if (row.orderStatus == "ASSIGN") {//待分配
                                    fragment += ('<a onclick="orderList.storeParams()" href="/workorder/edit?id=' + row.id + '" class="m-r-xs btn btn-xs btn-primary">分配</a>');
                                } else if (row.orderStatus == "NREPLY") {//待回复
                                    fragment += ('<button data-handle="order-reply" data-index="' + index + '" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-primary">回复</button>');
                                } else if (row.orderStatus == "NVISIT" || row.orderStatus == "CALLBACK") {//待回访
                                    // if (moment(moment().format('YYYY-MM-DD HH:mm:ss')).isAfter(row.treamentTime))//当前时间大于处理指定的回访时间
                                    fragment += ('<button data-handle="order-retback" data-index="' + index + '" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-primary">回访</button>');
                                } else if (row.orderStatus == "UNEXECUTED" && row.copyFlag != "Y") {//回访未执行
                                    fragment += ('<button data-handle="order-copy" data-index="' + index + '" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-primary">复制</button>');
                                }
                            }

                            return fragment;
                        }
                    }]
                });

                //点击查看详情tip!
                self.$dataTable.on('click', '[data-toggle="popover"]', function (e) {
                    $(this).popover({'animation':true});
                });
                //详情
                self.$dataTable.on('click', '[data-handle="order-details"]', function (e) {
                    var id = $(this).data('id');
                    var workOrder = $(this).data('order');
                    workOrderDetails(id, workOrder);
                    e.stopPropagation();
                });
                //接收
                self.$dataTable.on('click', '[data-handle="order-receive"]', function (e) {
                    var id = $(this).data('id');
                    $("#receiveId").attr("disabled", true);
                    data = {
                        id: id.toString(),
                        operationType: 'RECEIVE',//接收
                    };
                    self.$http.post('/mdni/workorder/updateWorkOrder', data).then(function (res) {
                        if (res.data.code == 1) {
                            self.$toastr.success('提交成功');
                            orderList.$dataTable.bootstrapTable('selectPage', 1);
                            orderList.$dataTable.bootstrapTable('refresh');
                        }
                    }).catch(function () {
                    }).finally(function () {
                        //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                        self.submitting = false;
                    });
                    e.stopPropagation();
                });
                //申请延期
                self.$dataTable.on('click', '[data-handle="order-time"]', function (e) {
                    var id = $(this).data('id');
                    var time = $(this).data('time');
                    //先查看有没有负责人
                    self.$http.get('/api/users/findHeadList').then(function (res) {
                        if (res.data.code == 1) {
                            //有的话
                            createTime(id,time);
                        }else{
                            self.$toastr.error(res.data.message);
                        }
                    }).catch(function () {
                    }).finally(function () {
                        //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                        self.submitting = false;
                    });
                    e.stopPropagation();
                });
                //驳回记录
                self.$dataTable.on('click', '[data-handle="order-back"]', function (e) {
                    var id = $(this).data('id');
                    back(id);
                });


                //处理
                self.$dataTable.on('click', '[data-handle="order-operation"]', function (e) {
                    /*var id = $(this).data('id');
                    workOrderOperation(id, 1);*/

                    //存储搜索条件
                    orderList.storeParams();
                    //跳转处理页面
                    window.location.href = '/workorder/settle?id=' + $(this).data('id');

                    e.stopPropagation();
                });
                //回复
                self.$dataTable.on('click', '[data-handle="order-reply"]', function (e) {
                    //存储搜索条件
                    orderList.storeParams();
                    var id = $(this).data('id');
                    data = {
                        id: id.toString(),
                        operationType: 'SELECTREMAINDER'//查看催单
                    };
                    self.$http.post('/mdni/workorder/updateWorkOrder', data).then(function (res) {
                        window.location.href = "/workorder/remaindList?id=" + id;
                        if (res.data.code == 1) {

                        }
                    }).catch(function () {
                    }).finally(function () {
                        //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                        self.submitting = false;
                    });
                    e.stopPropagation();
                });

                //回访
                self.$dataTable.on('click', '[data-handle="order-retback"]', function (e) {
                    var id = $(this).data('id');
                    workOrderOperation(id, 3);
                    e.stopPropagation();
                });

                //复制处理
                self.$dataTable.on('click', '[data-handle="order-copy"]', function (e) {
                    var id = $(this).data('id');
                    var index = $(this).data('index');
                    var rowData = self.$dataTable.bootstrapTable('getData');
                    var ss = rowData[index];
                    modal(rowData[index], id);
                    e.stopPropagation();
                });
                //编辑处理
                self.$dataTable.on('click', '[data-handle="order-edit"]', function (e) {
                    var id = $(this).data('id');
                    var index = $(this).data('index');
                    var rowData = self.$dataTable.bootstrapTable('getData');
                    var ss = rowData[index];
                    modal(rowData[index], id, true);
                    e.stopPropagation();
                });

                //备注
                self.$dataTable.on('click', '[data-handle="order-remark"]', function (e) {
                    var id = $(this).data('id');
                    var _$modal = $('#remarkModel').clone();
                    var $modal = _$modal.modal({
                        height: 400,
                        maxHeight: 300,
                        backdrop: 'static',
                        keyboard: false
                    });
                    createRemark($modal, id);
                    e.stopPropagation();
                });
            },
            createWorkOrder: function (e) { //创建工单
                this.showModel();
            },

            // 获取机构选择树的数据
            fetchOrgTree: function (id, type) {
                var self = this;
                this.$http.get('/api/org/fetchDeptById/' + id).then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                        self.orgData = res.data;
                    }
                })
            },
            fetchOrgTree2: function (id, type) {
                var self = this;
                this.$http.get('/api/org/fetchDeptById/' + id).then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                        self.srcOrgData = res.data;
                    }
                })
            },
            //事项分类
            findProblemCategories: function () {
                var self = this;
                self.$http.get('/api/dict/dic/getByType?type=5').then(function (res) {
                    if (res.data.code == 1) {
                        self.problemCategories = res.data.data;
                    }
                });
            },

            //投诉原因
            findComplaintTypes: function () {
                var self = this;
                self.$http.get('/api/dict/dic/getByType?type=8').then(function (res) {
                    if (res.data.code == 1) {
                        self.complaintTypes = res.data.data;
                    }
                });
            },
            //重要程度
            findImportances: function () {
                var self = this;
                self.$http.get('/api/dict/dic/getByType?type=7').then(function (res) {
                    if (res.data.code == 1) {
                        self.importances = res.data.data;
                    }
                });
            },
            //部门
            // findDepartments: function () {
            //     var self = this;
            //     self.$http.get('/api/org/findDepartment/89').then(function (res) {
            //         if (res.data.code == 1) {
            //             self.departments = res.data.data;
            //         }
            //     });
            // },
            // 勾选机构数外部时，隐藏窗口
            clickOut: function () {
                this.showOrgTree = false;

            },
            clickOut2: function () {
                this.showSrcOrgTree = false;
            },
            // 选择机构时回调事件
            selectOrg: function (node) {
                var self = this;
                self.form.liableName = node.name;
                self.form.liableId = node.id;
            },
            // 选择机构时回调事件
            selectSrcOrg: function (node) {
                var self = this;
                self.form.srcName = node.name;
                self.form.srcId = node.id;
            },
            showModel: function () { //创建工单页面
                setTimeShowModel();
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
                if(self.form.fenpaiDate == true || self.form.fenpaiDate == 'true'){
                    sessionStorage.setItem("fenpaiDate",self.form.fenpaiDate );
                }
                if(self.form.customerFeedbackTime == true || self.form.customerFeedbackTime == 'true'){
                    sessionStorage.setItem("customerFeedbackTime",self.form.customerFeedbackTime );
                }
                if(self.form.refuseDate == true || self.form.refuseDate == 'true'){
                    sessionStorage.setItem("refuseDate",self.form.refuseDate );
                }
                if(self.form.refusedagainDate == true || self.form.refusedagainDate == 'true'){
                    sessionStorage.setItem("refusedagainDate",self.form.refusedagainDate );
                }
                if(self.form.expectDate == true || self.form.expectDate == 'true'){
                    sessionStorage.setItem("expectDate",self.form.expectDate );
                }
                if(self.form.faqiDate == true || self.form.faqiDate == 'true'){
                    sessionStorage.setItem("faqiDate",self.form.faqiDate );
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
                self.form.fenpaiDate = sessionStorage.getItem("fenpaiDate");
                self.form.customerFeedbackTime = sessionStorage.getItem("customerFeedbackTime");
                self.form.refuseDate = sessionStorage.getItem("refuseDate");
                self.form.refusedagainDate = sessionStorage.getItem("refusedagainDate");
                self.form.expectDate = sessionStorage.getItem("expectDate");
                self.form.faqiDate = sessionStorage.getItem("faqiDate");
                self.form.startDate = sessionStorage.getItem("startDate") || '';
                self.form.endDate = sessionStorage.getItem("endDate") || '';

                //加载完之后,清空sessionStorage
                sessionStorage.clear();
            }
        },

        created: function () {
            this.fUser = window.RocoUser;
            this.findProblemCategories();
            this.findComplaintTypes();
            this.findImportances();
            this.loadParams();
        },
        ready: function () {
            this.queryImportantDegree();
            var params = RocoUtils.parseQueryString(window.location.search.substr(1));
            if (params) {
                for (var key in params) {
                    var value = params[key];
                    this.form.status = value;
                    this.form.statusFlag = this.form.status;
                    this.form.manage = this.form.statusFlag == 'ORDERMANAGE';

                }
            }
            this.addActiveClass(this.form.status);
            //画表
            var deal = RocoUtils.parseQueryString()['status'];
            if(deal=='PROCESSING'){
                this.deal=true;
            }
            this.drawTable();

            if (this.form.quickCreateOrder == 1) {//首页快速发起工单标识
                setTimeShowModel();
            }
            this.$nextTick(function () {
                this.activeDatePicker();
            });

        },
        watch: {},
        beforeDestroy: function () {
        }
    });




    /**
     * 处理、回复、回访
     * @param id    工单ID
     * @param flag    操作标识
     * @returns
     */
    function workOrderOperation(id, flag) {
        var height = 450;
        if (flag == 1) {
            height = 460;
        }
        var _$modal = $('#orderModel').clone();
        var $modal = _$modal.modal({
            height: height,
            maxHeight: height + 10,
            backdrop: 'static',
            keyboard: false
        });

        // 获取 node
        var el = $modal.get(0);
        // 创建 Vue 对象编译节点
        operation = new Vue({
            el: el,

            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $modal, //模式窗体 jQuery 对象
            data: {
                orderId: id,
                //控制 按钮是否可点击
                disabled: false,
                flag: flag,
                order: '',
                form: {
                    brand: '0',
                    treamentTime: '',
                    customerDemandTime: '',
                    treamentPlan: '',
                    feedbackTime: '',
                    feedbackRmk: '',
                    orderStatus: '',
                    visitResult: ''
                }
            },
            methods: {
                getOrderDetails: function (orderId) {
                    var self = this;
                    self.$http.get('/mdni/workorder/' + orderId + '/get').then(function (res) {
                        if (res.data.code == 1) {
                            self.order = res.data.data;
                        }
                    });
                },
                activeDatePicker: function () {
                    $(this.$els.treamentTime).datetimepicker({
                        format: 'yyyy-mm-dd hh:00',
                        minView: 'day',
                        autoSize: true
                    });

                    $(this.$els.feedbackTime).datetimepicker({
                        format: 'yyyy-mm-dd hh:ii:ss',
                        minView: 'hour',
                        autoSize: true
                    });
                },
                submitClickHandler: function () {
                    var self = this;
                    if (self.submitting) {
                        return false;
                    }
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.submit();
                        }
                    });
                },
                rejectClickHandler: function () {
                    var self = this;
                    var formData = _.cloneDeep(self.form);
                    data = {
                        id: id,
                        // orderStatus: 'ASSIGN',//待分配
                        operationType: 'REJECT',//申诉
                        treamentPlan: formData.treamentPlan,
                        feedbackRmk: formData.feedbackRmk
                    };
                    self.$http.post('/mdni/workorder/updateWorkOrder', data).then(function (res) {
                        if (res.data.code == 1) {
                            self.$toastr.success('提交成功');
                            $modal.modal('hide');
                            orderList.$dataTable.bootstrapTable('selectPage', 1);
                            orderList.$dataTable.bootstrapTable('refresh');
                        }
                    }).catch(function () {
                    }).finally(function () {
                        //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                        self.submitting = false;
                    });
                },
                submit: function () {
                    var self = this;
                    self.submitting = true;
                    var formData = _.cloneDeep(self.form);
                    var data;
                    if (flag == 2) {//回复动作
                        data = {
                            id: id,
                            operationType: 'SELECTREMAINDER'//查看催单
                        };
                    }
                    if (flag == 3) {//回访动作
                        data = {
                            id: id,
                            orderStatus: formData.orderStatus,//工单状态
                            operationType: 'VISIT',//回复
                            visitResult: formData.visitResult
                        };
                    }

                    self.$http.post('/mdni/workorder/updateWorkOrder', data).then(function (res) {
                        if (res.data.code == 1) {
                            window.location.href = "/"
                        }
                    }).catch(function () {
                    }).finally(function () {
                        //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                        self.submitting = false;
                    });
                }

            },
            created: function () {
                // this.form.treamentTime = moment().format('YYYY-MM-DD HH:mm:ss');
                this.form.feedbackTime = moment().format('YYYY-MM-DD HH:mm:ss');
            },
            ready: function () {
                var self = this;
                this.activeDatePicker();
                this.getOrderDetails(self.orderId);
            }
        });
        // 创建的Vue对象应该被返回
        return operation;
    }



//列表操作备注
    function createRemark($el, id) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        remark = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                //控制 按钮是否可点击
                disabled: false,
                orderId: id,
                order: '',
                form: {
                    workOrderId: null,
                    remark: null
                }
            },
            methods: {
                getOrderDetails: function (orderId) {
                    var self = this;
                    self.$http.get('/mdni/workorder/' + orderId + '/get').then(function (res) {
                        if (res.data.code == 1) {
                            self.order = res.data.data;
                        }
                    });
                },
                submitClickHandler: function () {
                    var self = this;
                    if (self.submitting) {
                        return false;
                    }
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.submit();
                        }
                    });
                },
                submit: function () {
                    var self = this;
                    self.submitting = true;
                    var formData = _.cloneDeep(self.form);
                    var data = {
                        workOrderId: id,
                        operationType: 'REMARK',
                        remark: formData.remark
                    };
                    self.$http.post('/mdni/workorder/saveRemark', data).then(function (res) {
                        if (res.data.code == 1) {
                            self.$toastr.success('提交成功');
                            $el.modal('hide');
                        }
                    }).catch(function () {
                    }).finally(function () {
                        //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                        self.submitting = false;
                    });
                }
            },
            created: function () {
            },
            ready: function () {
                var self = this;
                self.getOrderDetails(self.orderId);
            }
        });
        // 创建的Vue对象应该被返回
        return remark;
    }

    //申请延期
    function createTime(id,date) {
        var _$modal = $('#remarkTime').clone();
        var $modal = _$modal.modal({
            height: 600,
            maxHeight: 800,
            backdrop: 'static',
            keyboard: false
        });
        // 获取 node
        var el = $modal.get(0);
        // 创建 Vue 对象编译节点
         time = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $modal, //模式窗体 jQuery 对象
            data: {
               oldTime:date,
               newTime:null,
               remarks:'',
                workorderId:id
            },
            methods: {
                activeDatePicker: function () {
                    $(this.$els.newTime).datetimepicker({
                        format: 'yyyy-mm-dd hh:00',
                        minView:1,
                        autoSize: true
                    });
                },
                submitapproval: function () {
                    var self=this;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            var treamentApproval={
                                workorderId:self.workorderId,
                                newTime:self.newTime,
                                oldTime:self.oldTime,
                                remarks:self.remarks
                            };
                            $modal.modal('hide');
                            self.$http.post('/mdni/worktime/approval', treamentApproval).then(function (res) {
                                if (res.data.code == 1) {
                                    self.$toastr.success('提交成功');
                                    orderList.$dataTable.bootstrapTable('selectPage', 1);
                                    orderList.$dataTable.bootstrapTable('refresh');
                                }
                            }).catch(function () {
                            }).finally(function () {
                                //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                                self.submitting = false;
                            });
                        }
                    });
                }
            },
            created: function () {
            },
            ready: function () {
                this.activeDatePicker();
            }
        });
        // 创建的Vue对象应该被返回
        return time;
    }



    //驳回记录
    function back(id) {
        var _$modal = $('#back').clone();
        var $modal = _$modal.modal({
            height: 600,
            maxHeight: 800,
            backdrop: 'static',
            keyboard: false
        });
        // 获取 node
        var el = $modal.get(0);
        // 创建 Vue 对象编译节点
        var back = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $modal, //模式窗体 jQuery 对象
            data: {
                result:null
            },
            filters:{
                result:function (value) {
                    var label = '';
                    if(value == 1 ){
                        label = '<font color="green">通过</font>';
                        return label;
                    }else {
                        label = '<font color="red">驳回</font>';
                        return label;
                    }
                }
            },
            methods: {
                fetchDetail: function () {
                    var self=this;
                    self.$http.get('/mdni/worktime/back?workorderId='+id).then(function (res) {
                        if (res.data.code == 1) {
                            self.result=res.data.data;
                        }
                    }).catch(function () {
                    }).finally(function () {
                    });
                }
            },
            created: function () {
            },
            ready: function () {
                this.fetchDetail();
            }
        });
        // 创建的Vue对象应该被返回
        return back;
    }

})(RocoUtils,moment);


