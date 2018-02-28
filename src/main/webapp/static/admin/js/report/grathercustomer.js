var vueIndex = null;
+(function (RocoUtils) {
    // 接口Url
    var httpUrl = 'http://report.rocozxb.cn';
    var key = '7bqwe2235df6aq2we4r3t6y1vxnmhjklpewd23';

    Vue.filter('filter-state', function(state) {
        switch(state) {
            case 1: return '新增'
            case 2: return '退订'
            case 3: return '转单'
            case 4: return '存余'
        }
    })

    Vue.filter('filter-count', function(row, type) {
        return JSON.parse(row.replace(new RegExp("'", 'gm'), '"'))[type]
    })

   vueIndex = new Vue({
        el: '#container',
        data: {
            form: {
                city: '', // 城市
                startDate: '', // 开始日期
                endDate: '', // 结束日期
                state: '' // 状态
            },
            list: [],
            date: {}
        },
        ready: function() {
            this.fetchDate()
        },
        methods: {
            // 获取时间选择范围
            fetchDate: function() {
                var self = this
                self.$http.get('/static/admin/js/report/date.json')
                    .then(function(res) {
                        console.log(res.data.start)
                        self.date = res.data
                        self.$nextTick(function() {
                            self.initDatePlu()
                        })
                    })
            },
            // 获取数据
            fetchData: function() {
                var self = this
                
                self.$http.get('/static/admin/js/report/grathercustomer.json', {
                        params: self.form
                    })
                    .then(function(res) {
                        if(res.data.code == 1) {
                            _.forEach(res.data.data, function(item, index) {
                                item.index = index + 1
                                item = _.extend(item, JSON.parse(item.rowDataJson.replace(new RegExp("'", 'gm'), '"')))
                                switch(item.statusNum) {
                                    case 1: item.statuStr = '新增'; break;
                                    case 2: item.statuStr = '退订'; break;
                                    case 3: item.statuStr = '转单'; break;
                                    case 4: item.statuStr = '存余'; break;
                                }
                                item.cost = '财务'
                            })

                            self.list = res.data.data

                            self.$nextTick(function() {
                                $('#table').bootstrapTable({
                                    data: self.list,
                                    height: 500
                                });
                            })
                        }
                    })

                // self.$http.jsonp('http://192.168.6.110:3206/api/GatherCustomerReport/getQueryTest',
                // {
                //     params: self.form,
                //     jsonp: 'jsoncallback'
                // }).then(function(res) {
                //     if(res.data.code == 1) {
                //          _.forEach(res.data.data, function(item, index) {
                //             item.index = index + 1
                //             item = _.extend(item, JSON.parse(item.rowDataJson.replace(new RegExp("'", 'gm'), '"')))
                //             switch(item.statusNum) {
                //                 case 1: item.statuStr = '新增'; break;
                //                 case 2: item.statuStr = '退订'; break;
                //                 case 3: item.statuStr = '转单'; break;
                //                 case 4: item.statuStr = '存余'; break;
                //             }
                //             item.cost = '财务'
                //         })

                //         self.list = res.data.data

                //         self.$nextTick(function() {
                //             $('#table').bootstrapTable({
                //                 data: self.list,
                //                 height: 500
                //             });
                //         })
                //     }
                // })
            },
            initDatePlu: function() {
                var self = this
                self.form.startDate = self.date.start
                self.form.endDate = self.date.end

                $(this.$els.startDate).datetimepicker({
                    format: 'yyyy-mm-dd hh:ii',
                    startView: 2,
                    startDate: self.date.start,
                    endDate: self.date.end,
                    minView: 0
                }).on('changeDate', function(ev) {
                    $(self.$els.endDate).datetimepicker('setStartDate', ev.date)
                });

                $(this.$els.endDate).datetimepicker({
                    format: 'yyyy-mm-dd hh:ii',
                    startView: 2,
                    startDate: self.date.start,
                    endDate: self.date.end,
                    minView: 0
                }).on('changeDate', function(ev) {
                    $(self.$els.startDate).datetimepicker('setEndDate', ev.date)
                });

                this.fetchData()
            },
            // 查询
            query: function() {
                var self = this

                self.list = []

                self.fetchData()
            },
            // 清空
            reset: function() {

            },
            // 导出
            export: function() {
                var self = this;
                var iframe = document.createElement('iframe');
                iframe.style.display = 'none';
                iframe.frameborder = 0;
                iframe.src = url;
                document.body.appendChild(iframe);
            }
        }
    })
})(this.RocoUtils)