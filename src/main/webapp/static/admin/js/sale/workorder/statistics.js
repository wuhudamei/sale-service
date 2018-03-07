var order;
var orderList;
var operation;
+(function (DameiUtils) {
    $('#orderStatistics').addClass('active');
    orderList = new Vue({
        el: '#container',
        mixins: [DameiVueMixins.DataTableMixin],
        http: {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        },
        data: {
            form: {
                liable1: '',
                startDate: '',
                endDate: ''
            },
            queryLiableTypes: null,
            customTableTitle: '投诉窗帘共255户 403个事项',
            liableTypeName: '',
            orderTotal: '',
            customs: {},
            isnull: false,
            customerNum: '',
            liable2s: {}
        },
        methods: {
            exportWorkOrder: function () {
                var self = this;
                self.$http.get('/damei/workorder/checkNull', {params: self.form}).then(function (res) {
                    if (res.data.code == 1) {
                        window.location.href = '/damei/workorder/export?receptionStartTime=' + self.form.startDate + '&receptionEndTime=' + self.form.endDate;
                    } else {
                        self.$toastr.error(res.data.message);
                    }
                }).catch(function () {
                }).finally(function () {
                });

            },
            queryImportantDegree: function () {//获取责任类别1
                var self = this;
                self.$http.get('/api/dict/dic/getByType', {params: {'type': 3}}).then(function (res) {
                    if (res.data.code == 1) {
                        self.queryLiableTypes = res.data.data;
                        self.initQueryForm();
                        self.query();
                    }
                }).catch(function () {
                }).finally(function () {
                });
            },
            initQueryForm: function () {
                var self = this;
                if (self.queryLiableTypes && self.queryLiableTypes.length > 0) {
                    self.form.liable1 = self.queryLiableTypes[0].id;
                }
                self.form.startDate = moment().subtract(1, 'month').format('YYYY-MM-DD');
                self.form.endDate = moment().format('YYYY-MM-DD');
            },
            activeDatepiker: function () {
                DameiUtils.initDateControl($(this.$els.startDate), $(this.$els.endDate), 'yyyy-mm-dd');
            },
            query: function () {
                var self = this;
                self.$http.post('/damei/workorder/statisticsOrderNum',
                    self.form, {emulateJSON: true}).then(function (res) {
                    if (res.data.code == 1) {
                        self.customs = res.data.data.customs;
                        self.liable2s = res.data.data.liable2s;
                        self.orderTotal = res.data.data.orderTotal;
                        self.customerNum = res.data.data.customerNum;
                        self.liableTypeName = res.data.data.liableTypeName;
                        self.isnull = _.keys(self.customs).length > 0;
                    }
                });
            }
        },
        created: function () {
        },
        ready: function () {
            //查询责任类别1
            this.queryImportantDegree();
            this.activeDatepiker();
        },
        beforeDestroy: function () {
        }
    });

})(DameiUtils);


