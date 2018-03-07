var vueIndex = null;
+(function (DameiUtils) {
    $('#reportCenter').addClass('active');
    $('#groupReturnReport').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            $dataTable: null,
            form: {
                departmentId: null,
                isGroup:true,
                companyId:null,
                beginDate: '',
                endDate: '',
            },
            orgs: {},
            orgQuestion: {
                dicId: null,
                orgId: null
            },
            companys:null,
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/reportCenter/workReturn',
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
                    },{
                        field: 'companyName',
                        title: '分公司',
                        align: 'center'
                    }, {
                        field: 'orgName',
                        title: '部门',
                        align: 'center'
                    }, {
                        field: 'shouldReturnVisitCount',
                        title: '应回访量',
                        align: 'center'
                    }, {
                        field: 'haveReturned',
                        title: '已回访量',
                        align: 'center',
                    },  {
                        field: 'effectiveReturnVisit',
                        title: '有效回访量',
                        align: 'center',
                    }, {
                        field: 'satisfactionCount',
                        title: '满意数量',
                        align: 'center',
                    }, {
                        field: 'notSatisfiedCountN',
                        title: '不满意不派单',
                        align: 'center',
                    }, {
                        field: 'notSatisfiedCountY',
                        title: '不满意需派单',
                        align: 'center',
                    }, {
                        field: 'unsuccessCount',
                        title: '不再回访',
                        align: 'center',
                    }, {
                        field: 'returnVisitCompletionRate',
                        title: '回访完成率',
                        align: 'center',
                    }, {
                        field: 'satisfactionRate',
                        title: '满意率',
                        align: 'center',
                    }, {
                        field: 'returnRate',
                        title: '回访率',
                        align: 'center',
                    },]
                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
                this.$dataTable.bootstrapTable('refresh');
            },
            //查询部门
            queryOrg: function (val) {
                var self = this;
                if(val) {
                    self.$http.get('/api/org/findDepartment/' + val).then(function (res) {
                        if (res.data.code == 1) {
                            self.orgs = res.data.data;
                            if (res.data.data == null) {
                                self.form.orgId = '';
                            }
                            this.form.departmentId = '';
                        }
                    }).catch(function () {

                    }).finally(function () {
                    });
                }

            },
            // 日历初始化
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
            // 查询组织架构
            queryOrganization: function () {
                var self = this;
                self.$http.get('/api/org/findAll').then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.companys = res.data;
                        if (res.data == null) {
                            self.form.orgId = '';
                        }
                    }
                });
            }
        },
        created: function () {
            this.fUser = window.DameiUser;
        },
        ready: function () {
            this.form.departmentId='';
            this.form.companyId='';
            this.queryOrganization();
            this.activeDatepicker();
            this.drawTable();
        },
        watch:{
            "form.companyId": function (val) {
                this.queryOrg(val);
            },
        }
    });


})
(this.DameiUtils);