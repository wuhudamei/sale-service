var vueIndex = null;
+(function (DameiUtils) {
    $('#workOrderList').addClass('active');
    $('#urge').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            $dataTable: null,
            user: '',
            id: '',
            links: [],
            remarks: [],
            order: '',
            organizations: '',
            treamentPlan: '',
            treamentTime: '',
            brand: '0'
        },
        methods: {

            getOrderDetails: function (orderId) {
                var self = this;
                self.$http.get('/damei/workorder/' + orderId + '/get').then(function (res) {
                    if (res.data.code == 1) {
                        self.order = res.data.data;
                        if (self.order.photo) {
                            var arry = self.order.photo.split(',');
                            _.each(arry, function (ele) {
                                self.links.push({a: ele});
                            })
                        }

                        self.$http.get('/api/dict/dic/' + self.order.liableType1.id).then(function (res) {
                            if (res.data.code == 1) {
                                self.order.liableType1 = res.data.data;
                            }
                        });

                        self.$http.get('/api/dict/dic/' + self.order.questionType1.id).then(function (res) {
                            if (res.data.code == 1) {
                                self.order.questionType1 = res.data.data;
                            }
                        });
                        self.$http.get('/api/dict/dic/' + self.order.questionType2.id).then(function (res) {
                            if (res.data.code == 1) {
                                self.order.questionType2 = res.data.data;
                            }
                        });

                    }
                });
            },
            getRemarks: function (orderId) {
                var self = this;
                self.$http.get('/damei/workOrderRmk/getRemainder/' + orderId).then(function (res) {
                    if (res.data.code == 1) {
                        self.remarks = res.data.data;
                    }
                });
            }
        },
        created: function () {
            this.user = window.DameiUser;

        },
        ready: function () {

            var params = DameiUtils.parseQueryString(window.location.search.substr(1));
            if (params) {
                for (var key in params) {
                    var value = params[key];
                    this.id = value;

                }
            }
            ;
            this.getOrderDetails(params.id);
            this.getRemarks(params.id);
        }
    });

})
(this.DameiUtils);