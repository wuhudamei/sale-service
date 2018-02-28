var vueIndex = null;
+(function (RocoUtils) {
    $('#reportCenter').addClass('active');
    $('#processingReport').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            $dataTable: null,
            form: {
                departmentId: null,
                beginDate:'',
                endDate:''
            },
            orgs: {},
            orgQuestion: {
                dicId: null,
                orgId: null
            }
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/reportCenter/workDeal',
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: false, //是否分页
                    sidePagination: 'server', //服务端分页
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', //空数据的默认显示字符
                    striped: true, //斑马线
                    sortName: 'id', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{
                        field: '',
                        title: '序号',
                        align: 'center',
                        formatter:function (value, row, index) {
                            return index+1;
                        }
                    }, {
                        field: 'orgName',
                        title: '部门',
                        align: 'center'
                    }, {
                        field: 'workCount',
                        title: '工单数量',
                        align: 'center'
                    }, {
                        field: 'receiveCount',
                        title: '接收数量',
                        align: 'center',
                    },  {
                        field: 'rejectCount',
                        title: '申诉数量',
                        align: 'center',
                    }, {
                        field: 'rejectagain',
                        title: '转派数量',
                        align: 'center',
                    }, {
                        field: 'noFeedback',
                        title: '方案未反馈数量',
                        align: 'center',
                    }, {
                        field: 'excessiveFeedback',
                        title: '超期未反馈',
                        align: 'center',
                    }, {
                        field: 'finiShorder',
                        title: '已完成',
                        align: 'center',
                    }, {
                        field: 'notOnSchedule',
                        title: '超期未完成',
                        align: 'center',
                    }, {
                        field: 'onSchedule',
                        title: '按期解决',
                        align: 'center',
                    }, {
                        field: 'resolutionRate',
                        title: '解决率',
                        align: 'center',
                    },{
                        field: 'scheduledRate',
                        title: '按期解决率',
                        align: 'center',
                    },{
                        field: 'responsibilityDepartmentDismissed',
                        title: '责任部门转派率',
                        align: 'center',
                    },{
                        field: 'receiptRate',
                        title: '接收率',
                        align: 'center',
                    },{
                        field: 'feedbackRate',
                        title: '反馈及时率',
                        align: 'center',
                    },]
                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
                this.$dataTable.bootstrapTable('refresh');
            }, // 日历初始化
            activeDatepicker: function () {
                var self = this;
                $('#beginDate', self._$el).datetimepicker({});
                $('#endDate', self._$el).datetimepicker({});
                var date=new Date();
                $('#endDate').datetimepicker( 'setDate' , date );
                $('#beginDate').datetimepicker( 'setDate', date );
                self.form.beginDate=$('#endDate').val();
                self.form.endDate=$('#beginDate').val();
            },
            queryOrg: function () {
                var self = this;
                var company = RocoUser.company;
                if(company==''||company==null||company==undefined||company=='undefined'){
                    self.form.orgId = '';
                    self.$toastr.error("请为当前操作人设置公司")
                }else{
                self.$http.get('/api/org/findDepartment/'+company).then(function (res) {
                    if (res.data.code == 1) {
                        self.orgs = res.data.data;
                        if (res.data.data == null) {
                            self.form.orgId = '';
                        }
                    }
                }).catch(function () {

                }).finally(function () {
                });
                }

            }
        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.form.departmentId='';
            this.queryOrg();
            this.activeDatepicker();
            this.drawTable();
        }
    });


})
(this.RocoUtils);