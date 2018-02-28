+(function (Vue, $, _, moment, RocoUtils) {
    var vueIndex = new Vue({
        el: '#container',
        mixins: [RocoVueMixins.DataTableMixin],
        data: {
            user:_.extend({}, window.RocoUser),
            warnings: {
                pendingNum: 0,
                nreplyNum: 0,
                nvisitNum: 0,
                unexecutedNum: 0
            },
            today: '',
            fUser: null,
            contentTip: '5555',
            $fullCalendar: null
        },

        methods: {
            getCurrentWorkNum: function () {
                var self = this;
                self.$http.get('/mdni/workorder/getCurrentUserWorksNum').then(function (res) {
                    if (res.data.code == 1) {
                        self.warnings = res.data.data;
                    }
                });
            },
            activeFullCalendar: function () {
                var self = this;
                self.$fullCalendar = $(this.$els.calendar).fullCalendar({
                    header: {
                        left: 'prev title next',
                        // center: 'title',
                        right: 'today'
                    },
                    defaultDate: self.today,
                    height: 700,
                    editable: false, //禁止编辑
                    timezone: 'Asia/Shanghai',
                    eventLimit: false, // true allow "more" link when too many events
                    events: function (start, end, timezone, callback) {
                        $.ajax({
                            url: '/api/plans/getPlans',
                            type: "post",
                            dataType: 'json',
                            data: {
                                startTime: start.format('YYYY-MM-DD'),
                                endTime: end.format('YYYY-MM-DD')
                            },
                            success: function (res) {
                                if (res.code == 1) {
                                    var events = [];

                                    _.each(res.data, function (event, index, array) {
                                        var title = [
                                            event.content
                                        ];

                                        var color = '';
                                        var rendering = '';
                                        var start = event.startTime ? moment(event.startTime).format('YYYY-MM-DD') : '';
                                        var end = event.endTime ? moment(event.endTime).add(1, 'days').format('YYYY-MM-DD') : '';
                                        color = '#31b0d5';

                                        if (event.content) {
                                            rendering = 'background';
                                            events.push({
                                                _origin: event,
                                                title: title,
                                                start: start,
                                                end: end,
                                                className: ['t-a-c'],
                                                color: color,
                                                content: event.content
                                            });
                                        }
                                    });
                                    callback(events);
                                }
                            }
                        })
                    },
                    eventMouseover: function (event, jsEvent, view) {
                        $(jsEvent.target).popover({
                            content: event.title,
                            container: 'body',
                            placement: 'top',
                            trigger: 'hover'
                        }).popover('show');
                        // $(jsEvent.target).popover('show');
                    },
                    eventMouseout: function (event, jsEvent, view) {
                        $(jsEvent.target).popover('destroy');
                    },
                    eventClick: function (event) {
                        if (event.content) {
                            self.showContent(event.content);
                            return false;
                        }
                    }
                });
            },
            destroyFullCalendar: function () {
                var self = this;
                self.$fullCalendar.fullCalendar('destroy');
            },
            createBtnClickHandler: function (e) {
                var plan = {
                    content: '',
                    startTime: '',
                    endTime: ''
                };
                this.showModel(plan, false);
            },
            showModel: function (role, isEdit) {
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    width: 580,
                    height: 280,
                    maxHeight: 280,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal, role, isEdit);
            },
            showContent: function (content) {

                var _$modal = $('#contentModal');
                var $modal = _$modal.modal({
                    // height: 450,
                    // maxHeight: 450,
                    backdrop: 'static',
                    keyboard: false
                });
                this.contentTip = content;
            }
        },
        created: function () {
            this.fUser = window.RocoUser;
            this.today = moment().format('YYYY-MM-DD');
        },
        ready: function () {
            this.activeFullCalendar();
            this.getCurrentWorkNum();
        }
    });

    // 新建编辑弹窗
    function modal($el, model, isEdit) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueModal = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                //控制 按钮是否可点击
                disabled: false,
                //模型复制给对应的key
                plan: model,
                submitBtnClick: false
            },
            created: function () {

            },
            ready: function () {
                this.activeDatepiker();
            },
            methods: {
                activeDatepiker: function () {
                    RocoUtils.initDateControl($(this.$els.startDate), $(this.$els.endDate));
                },
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;
                            self.$http.post('/api/plans', self.plan, {emulateJSON: true}).then(function (res) {
                                if (res.data.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        vueIndex.destroyFullCalendar();
                                        vueIndex.activeFullCalendar();
                                        self.$toastr.success('操作成功');
                                    });
                                    self.$destroy();
                                    $el.modal('hide');
                                }
                            }).finally(function () {
                                self.disabled = false;
                            });
                        }
                    });
                }
            }
        });
        // 创建的Vue对象应该被返回
        return vueModal;
    }
})(Vue, jQuery, _, moment, RocoUtils);

