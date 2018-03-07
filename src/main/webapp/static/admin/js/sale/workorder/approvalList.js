+(function (DameiUtils, moment) {
    $('#workOrderList').addClass('active');
    $('#treatTime').addClass('active');
    var orderList = new Vue({
        validators: {},
        el: '#container',
        mixins: [DameiVueMixins.DataTableMixin],
        data: {
            user: _.extend({}, window.DameiUser),
            fUser: null,
            deal: false, //是否显示延期列
            form: {
                keyword:''
            },
            _$el: null, //自己的 el $对象
            _$dataTable: null, //datatable $对象
            //用于回显页码
            pageNumber: 1,
        },
        methods: {
            //返回
            cancel: function () {
                window.history.go(-1);
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
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
                this.$dataTable.bootstrapTable('refresh', {
                    pageNumber: 1
                })
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/damei/worktime/approvalList',
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
                    columns: [{
                        field: 'id',
                        title: 'id',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'oldTime',
                        title: '原预计时间',
                        align: 'center',
                    }, {
                        field: 'newTime',
                        title: '申请延期时间',
                        align: 'center',
                    }, {
                        field: 'remarks',
                        title: '延期说明',
                        align: 'center',
                    }, {
                        field: 'createDate',
                        title: '请求时间',
                        align: 'center',
                    }, {
                        field: 'workorderId',
                        title: '工单号',
                        align: 'center',
                        visible: false
                    }, {
                        field: 'name',
                        title: '请求人',
                        align: 'center',
                    }, {
                        field: 'workorderStatus',
                        title: '状态',
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            if(value=='PROCESSING'){
                                fragment+='未过期';
                            }else {
                                fragment+='已过期';
                            }
                            return fragment;
                        }
                    },
                        {
                            field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                            title: "操作",
                            align: 'center',
                            formatter: function (value, row, index) {
                                var fragment = '';
                                if(row.workorderStatus=='PROCESSING'){
                                fragment += ('<button data-handle="order-time"  data-id="' + row.id + '" data-work="' + row.workorderId + '" type="button" class="m-r-xs btn btn-xs btn-primary">延期审核</button>');
                                }
                                return fragment;
                            }
                        }]
                });
                //审核
                self.$dataTable.on('click', '[data-handle="order-time"]', function (e) {
                    var id = $(this).data('id');
                    var work = $(this).data('work');
                    window.location.href = "/workorder/approval?id=" + work + "&ids=" + id;

                });
            },

        },

        created: function () {

        },
        ready: function () {
            this.drawTable();
        },
        watch: {},
        beforeDestroy: function () {
        }
    });

})(DameiUtils, moment);


