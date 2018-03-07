var vueIndex = null;
+(function (DameiUtils) {
    $('#setting').addClass('active');
    $('#depQuestion').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            $dataTable: null,
            form: {
                orgId: null,
                companyId:null
            },
            companys:{},
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
                    url: '/api/question/list',
                    method: 'get',
                    dataType: 'json',
                    cache: false, //去缓存
                    pagination: true, //是否分页
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
                        field: 'dicName',
                        title: '事项分类',
                        align: 'center'
                    }, {
                        field: 'company',
                        title: '分公司',
                        align: 'center'
                    },{
                        field: 'orgName',
                        title: '责任部门',
                        align: 'center'
                    }, {
                        field: 'createTime',
                        title: '创建时间',
                        align: 'center'
                    }, {
                        field: 'id',
                        title: '主键',
                        align: 'center',
                        visible: false
                    }, {
                        field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            //if (DameiUtils.hasPermission('admin:role:delete'))
                            fragment += ('<button data-handle="operate-delete" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
                            return fragment;
                        }
                    }]
                });
                self.$dataTable.on('click', '[data-handle="operate-delete"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '删除问题类型',
                        text: '确定删除该问题类型？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.get('/api/question/delete?id=' + id).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                self.$dataTable.bootstrapTable('refresh');
                            }
                        }).catch(function () {

                        }).finally(function () {
                            swal.close();
                        });
                    });
                    e.stopPropagation();
                });
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
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
                this.$dataTable.bootstrapTable('refresh');
            },
            createBtnClickHandler: function (e) {
                this.showModel();
            },
            queryOrg: function () {
                var self = this;
                var companyId=self.form.companyId;
                if(companyId==''){
                    self.orgs=null;
                    this.form.orgId='';
                }else{
                self.$http.get('/api/question/orgResponsibility?companyId='+companyId).then(function (res) {
                    if (res.data.code == 1) {
                        self.orgs = res.data.data;
                        this.form.orgId='';
                        if (res.data.data == null) {
                            self.form.orgId = '';
                        }
                    }
                }).catch(function () {

                }).finally(function () {
                });
                }
            },
            showModel: function () {
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    height: 260,
                    maxHeight: 260,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal);
            }
        },
        created: function () {
            this.fUser = window.DameiUser;
        },
        ready: function () {
            this.form.orgId='';
            this.form.companyId='';
            this.drawTable();
            this.queryOrganization();
        }
    });
    // 新建弹窗
    function modal($el) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueModal = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [DameiVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                //控制 按钮是否可点击
                disabled: false,
                //模型复制给对应的key
                orgQuestion: {
                            dicId:'',
                            orgId:'',
                            company:'',
                            },
                submitBtnClick: false,
                questions:null,
                orgs:null,
                companys:null
            },
            created: function () {

            },
            ready: function () {
                this.queryQuestion();
            },
            methods: {
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;
                            self.$http.post('/api/question/add', self.orgQuestion).then(function (res) {
                                if (res.data.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        vueIndex.$dataTable.bootstrapTable('refresh');
                                        self.$toastr.success('操作成功');
                                    });
                                    $el.modal('hide');
                                }
                            }).finally(function () {

                            });
                        }
                    });

                },
                queryOrg: function () {
                    var self = this;
                    var companyId=self.orgQuestion.company;
                    console.log(companyId+"companyId")
                    if(companyId==''){
                        self.orgs=null;
                        this.orgQuestion.orgId='';
                    }else {
                    self.$http.get('/api/question/orgResponsibility?companyId='+companyId).then(function (res) {
                        if (res.data.code == 1) {
                            self.orgs = res.data.data;
                            this.orgQuestion.orgId='';
                        }
                    }).catch(function () {

                    }).finally(function () {
                    });
                    }

                },
                queryQuestion: function () {
                    var self = this;
                    self.companys=vueIndex.companys;
                    self.$http.get('/api/dict/dic/getByType?type=5').then(function (res) {
                        if (res.data.code == 1) {
                            self.questions = res.data.data;
                        }
                    }).catch(function () {
                    }).finally(function () {
                    });

                }
            }
        });
        // 创建的Vue对象应该被返回
        return vueModal;
    }


})
(this.DameiUtils);