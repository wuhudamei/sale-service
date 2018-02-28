var vueIndex = null;
+(function (RocoUtils) {
    $('#personalPerformance').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            day:null,
            week:null,
            month:null
        },
        methods: {
            queryOrg: function () {
                var self = this;
                self.$http.get('/api/reportCenter/personlReport').then(function (res) {
                    if (res.data.code == 1) {
                        self.day=res.data.data.day;
                        self.week=res.data.data.week;
                        self.month=res.data.data.month;
                    }
                }).catch(function () {

                }).finally(function () {
                });

            }
        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.queryOrg();
        }
    });


})
(this.RocoUtils);