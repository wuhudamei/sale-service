var tt = null;
+(function () {
    $('#setting').addClass('active');
    $('#organizationMenu').addClass('active');
    tt = new Vue({
        el: '#container',
        data: {
            $dataTable: null,
            form: {
                keyword: '',
                status: '',
                parents: '',
                selectedItem: ''
            },

            organizations: ''
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            fetchJsTree: function () {
                var self = this;
                var _$jstree = $('#jstree');
                this.$http.get('/api/org/tree').then(function (res) {
                    if (res.data.code == 1) {
                        _$jstree.jstree({
                            core: {
                                multiple: false,
                                // 不加此项无法动态删除节点
                                check_callback: true,
                                data: res.data.data
                            },
                            types: {
                                default: {
                                    icon: 'glyphicon glyphicon-stop'
                                }
                            },
                            plugins: ['types', 'wholerow', 'changed']
                        });
                    }
                }).catch(function () {

                }).finally(function () {

                });
            },
            createBtnClickHandler: function (e) {
                var self = this;
                var _$jstree = $('#jstree');
                var org = {
                    id: '',
                    orgCode: '',
                    orgName: '',
                    parentId: '',
                    familyCode: '',
                    type:'DEPARTMENT',
                    depType:'LIABLEDEPARTMENT'
                };
                var ref = _$jstree.jstree(true),
                    sel = ref.get_selected(true);
                // 未选择分类创建一级分类
                if (!sel.length) {
                    toastr.error("请选择节点");
                    return;
                } else {
                    org.parentId = sel[0].id;
                    this.showModel(org, false);
                }


            },
            editBtn: function () {
                var self = this;
                var _$jstree = $('#jstree');
                var ref = _$jstree.jstree(true),
                    sel = ref.get_selected(true);
                if (!sel.length) {
                    toastr.error("请选择节点");
                    return;
                } else {
                    self.$http.get('/api/org/' + sel[0].id).then(function (res) {
                        if (res.data.code == 1) {
                            var org = res.data.data;
                            this.showModel(org, true);
                            // ref.rename_node(sel,data.text);
                        }
                    });
                }
            },
            deleteBtn: function () {
                var self = this;
                var _$jstree = $('#jstree');
                var ref = _$jstree.jstree(true),
                    sel = ref.get_selected(true);
                if (!sel.length) {
                    toastr.error("请选择节点");
                    return;
                } else {
                    swal({
                        title: "你确定删除该分类吗?",
                        text: "警告:删除后不可恢复！",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        closeOnConfirm: false
                    }, function (isConfirm) {
                        if (isConfirm) {
                            self.$http.get('/api/org/delete/' + sel[0].id).then(function (res) {
                                if (res.data.code == 1) {
                                    swal("操作成功!", "", "success");
                                    ref.delete_node(sel);
                                }
                            }).catch(function () {
                                swal("操作失败！", "", "error");
                            }).finally(function () {
                                swal.close();
                            });
                        }
                    });
                }
            },
            showModel: function (org, isEdit) {
                var self = this;

                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    height: 300,
                    maxHeight: 450,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal, org, isEdit, function (data) {
                    var _$jstree = $('#jstree');

                    var ref = _$jstree.jstree(true),
                        sel = ref.get_selected(true);
                    if (org.id != '') {
                        ref.rename_node(sel, data.text);
                    } else {
                        ref.create_node(org.parentId, data);
                    }
                });
            }
        },
        created: function () {
            this.fUser = window.DameiUser;
        },
        ready: function () {
            this.fetchJsTree();
        }
    });
    // 实现弹窗方法
    function modal($el, model, isEdit, callback) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueModal = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [DameiVueMixins.ModalMixin],
            validators: {
                validAppName: function (val) {
                    if (_.trim(val) === '') {
                        return true;
                    }
                    return /^[A-Za-z0-9_-]+$/.test(val);
                }
            },
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                //控制 按钮是否可点击
                disabled: false,
                //模型复制给对应的key
                parenId: '',
                parents: '',
                org: model,
                submitBtnClick: false
            },
            created: function () {
            },
            ready: function () {
            },
            methods: {
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;

                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;

                            self.$http.post('/api/org', self.org).then(function (res) {
                                if (res.data.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        self.$toastr.success('添加成功');
                                        callback(res.data.data);
                                    });
                                    $el.modal('hide');
                                }
                            }).catch(function () {
                                swal(res.data.message, "", "error");
                            }).finally(function () {
                                self.disabled = false;
                            });
                        }
                    });
                },
                //选择部门: 且上次部门类型是供应商的,就给 部门类型默认值 LIABLEDEPARTMENT
                chooseDepartment: function () {
                    var self = this;
                    if(self.org.type == "DEPARTMENT" && self.org.depType == "SUPPLIER"){
                        self.org.depType = "LIABLEDEPARTMENT";
                    }
                }
            }
        });

        // 创建的Vue对象应该被返回
        return vueModal;
    }
})();