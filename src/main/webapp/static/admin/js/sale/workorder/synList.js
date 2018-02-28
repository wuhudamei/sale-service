+(function (RocoUtils, moment) {
    $('#setting').addClass('active');
    $('#synList').addClass('active');
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
            user: _.extend({}, window.RocoUser),
            fUser: null,
            deal: false, //是否显示延期列
            form: {
                keyword: '',
                companyId: '',
                departmentId: '',
                startDate: '',
                endDate: ''
            },
            departments: [],
            companies: [],
            _$el: null, //自己的 el $对象
            _$dataTable: null, //datatable $对象
            //用于回显页码
            pageNumber: 1
        },
        methods: {
            //返回
            cancel: function () {
                window.history.go(-1);
            },
            activeDatePicker: function () {
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
            //公司
            fetchCompany: function () {
                var self = this;
                this.$http.get('/api/org/findAll').then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                        self.companies = res.data;
                    }
                })
            },
            //部门
            findOrganizations: function () {
                var self = this;
                if (self.form.companyId == '') {
                    self.departments = [];
                } else {
                    var url = "/api/org/findDepartment/" + self.form.companyId;
                    //清空选中的
                    self.form.departmentId = '';
                    if (self.form.companyId) {
                        self.$http.get(url).then(function (res) {
                            if (res.data.code == 1) {
                                self.departments = res.data.data;
                            }
                        });
                    }
                }
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
                //this.$dataTable.bootstrapTable('refresh', {pageNumber: 1})
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/mdni/workorder/workOrderFailList',
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
                        visible: true
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
                            if (val) {
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
                            if (val.length > 5) {
                                return val.substring(0, 5) + "...";
                            } else {
                                return val;
                            }
                        }
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
                            if (val) {
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
                        field: 'pushType',
                        title: '失败类型',
                        align: 'center',
                        sortable: true,
                        formatter: function (val, row, index) {
                            if (val == 'PUSH') {
                                return '推送失败';
                            } else if (val == 'TURNDOWN')
                                return '驳回失败'
                        },
                        visible: true
                    }, {
                        field: 'pushNumber',
                        title: '失败次数',
                        align: 'center',
                        sortable: true,
                        visible: true
                    },
                        {
                            field: 'createDate',
                            title: '创建时间',
                            align: 'center',
                            sortable: true,
                            visible: true
                        },
                        {
                            field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                            title: "操作",
                            align: 'center',
                            formatter: function (value, row, index) {
                                var fragment = '';
                                fragment += ('<button data-handle="order-syn"  data-id="' + row.id + '" data-type="' + row.pushType + '" type="button" class="m-r-xs btn btn-xs btn-primary">同步</button>');
                                return fragment;
                            }
                        }]
                });
                //同步
                self.$dataTable.on('click', '[data-handle="order-syn"]', function () {
                    var id = $(this).data('id');
                    var type = $(this).data('type');
                    var url = '/mdni/workorder/workOrderSyn?id=' + id + "&pushType=" + type;
                    self.$http.get(url).then(function (res) {
                        if (res.data.code == 1) {
                            self.$toastr.success(res.data.message);
                            //orderList.$dataTable.bootstrapTable('selectPage', 1);
                            orderList.$dataTable.bootstrapTable('refresh');
                        } else {
                            self.$toastr.error(res.data.message);
                        }
                    });
                });
            },

        },
        created: function () {
        },
        ready: function () {
            this.activeDatePicker();
            this.fetchCompany();
            this.drawTable();
        },
        watch: {},
        beforeDestroy: function () {
        }
    });
})(RocoUtils, moment);


