var vueIndex = null;
+(function (RocoUtils) {
    $('#setting').addClass('active');
    $('#userManager').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            $dataTable: null,
            fUser: null,
            form: {
                keyword: '',
                status: '',
                companyId: '',
                departmentId: '',
                supplierId: ''
            },
            organizations: [],
            departments: [],
            suppliers: [],
            //同步按钮
            synName: '同步',
            synClass: 'btn-primary',
            synBtn: false,
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/users',
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
                    maintainSelected: true, //维护checkbox选项
                    sortName: 'id', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{
                        field: 'orgCode',
                        title: '账号',
                        align: 'center'
                    }, {
                        field: 'name',
                        title: '用户名',
                        align: 'center'
                    }, {
                        field: 'phone',
                        title: '电话',
                        align: 'center'
                    }, {
                        field: 'email',
                        title: '邮箱',
                        align: 'center'
                    }, {
                        field: 'sex',
                        title: '性别',
                        align: 'center',
                        formatter: function (val, row, index) {
                            if (val == 'MALE')
                                return '男';
                            if (val == 'FEMALE')
                                return '女';
                            return '';
                        }
                    }, {
                        field: 'company',
                        title: '公司',
                        align: 'center',
                        formatter: function (val, row, index) {
                            if(val) {
                                return val.orgName;
                            }
                        }

                    }, {
                        field: 'department',
                        title: '部门',
                        align: 'center',
                        formatter: function (val, row, index) {
                            if(val) {
                                return val.orgName;
                            }
                        }
                    }, {
                        field: 'remindFlag',
                        title: '发送微信通知',
                        align: 'center',
                        width: '5%',
                        formatter: function (val,row) {
                            if(val) {
                                return "<font color='green'>是</font>";
                            }else{
                                return "<font color='red'>否</font>";
                            }
                        }
                    },{
                        field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var operateStatus = '';
                            var operateName = '';
                            // if (row.status == 'NORMAL') {
                            //     operateStatus = 'INVALID';
                            //     operateName = '锁定';
                            // } else {
                            //     operateStatus = 'NORMAL';
                            //     operateName = '启用';
                            // }
                            //部门 公司 id
                                var comId=null;
                                var comName=null;
                                if(row.company==null){
                                }else{
                                    comId=row.company.id;
                                    comName=row.company.orgName;
                                }
                                var depId=null;
                                var depName=null;
                                if(row.department==null){
                                }else{
                                    depId=row.department.id;
                                    depName=row.department.orgName;
                                }
                            var fragment = '';

                                fragment += ('<button data-handle="operate-edit" data-id="'
                                    + row.id + '" type="button" class="m-r-xs btn btn-xs btn-info">编辑</button>');

                                fragment += ('<button data-handle="operate-job" data-id="'
                                    + row.id + '" type="button" class="m-r-xs btn btn-xs btn-warning">兼职</button>');

                                if(comId!=null&&depId!=null&&row.departmentHead!='1'){
                                    fragment += ('<button data-handle="operate-dep" ' +
                                    'data-id="' + row.id + '" ' +
                                    'data-company="' + comId+ '" ' +
                                    'data-department="' + depId+ '" ' +
                                    'data-comname="' + comName+ '" ' +
                                    'data-depname="' + depName+ '" ' +
                                    'type="button" class="m-r-xs btn btn-xs btn-primary">设置为部门领导</button>');
                                }

                                if(comId!=null&&depId!=null&&row.departmentHead=='1'){
                                    fragment += ('<button data-handle="operate-cancel" ' +
                                    'data-id="' + row.id + '" ' +
                                    'data-company="' + comId+ '" ' +
                                    'data-department="' + depId+ '" ' +
                                    'data-comname="' + comName+ '" ' +
                                    'data-depname="' + depName+ '" ' +
                                    'data-name="' + row.name+ '" ' +
                                    'type="button" class="m-r-xs btn btn-xs btn-danger">取消部门领导</button>');
                                }
                            // if (RocoUtils.hasPermission('admin:user:resetpwd'))
                            //     fragment += ('<button data-handle="operate-resetPassWord" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-info">重置密码</button>');
                            // if (RocoUtils.hasPermission('admin:user:setrole'))
                            //     fragment += ('<button data-handle="operate-setRoles" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-info">设置角色</button>');
                            // if (RocoUtils.hasPermission('admin:user:opt'))
                            //     fragment += ('<button data-handle="operate-changeStatus" data-id="' + row.id + '"data-status="' + operateStatus + '" type="button" class="m-r-xs btn btn-xs btn-info">' + operateName + '</button>');
                            // if (RocoUtils.hasPermission('admin:user:del'))
                            //     fragment += ('<button data-handle="operate-changeStatus" data-id="' + row.id + '" data-status="DELETE" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
                            return fragment;
                        }
                    }]
                });

                self.$dataTable.on('click', '[data-handle="operate-edit"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/users/' + id).then(function (res) {
                        if (res.data.code == 1) {
                            var user = res.data.data;
                            this.showModel(user, true);
                        }
                    });
                });
                //兼职
                self.$dataTable.on('click', '[data-handle="operate-job"]', function (e) {
                    var id = $(this).data('id');

                    //测试跳转
                    //window.location.href = "/api/test?id=" + id;
                    //显示modal
                    showJobModal(id);

                });

                //取消部门负责人
                self.$dataTable.on('click', '[data-handle="operate-cancel"]', function (e) {
                    var id = $(this).data('id');
                    var company = $(this).data('company');
                    var department = $(this).data('department');
                    var comName = $(this).data('comname');
                    var depName = $(this).data('depname');
                    var name = $(this).data('name');
                    var text;
                    text="取消"+name+""+comName+"公司"+depName+"的负责人"
                    swal({
                        title: "编辑部门负责人",
                        text:text ,
                        type: 'warning',
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "确定",
                        cancelButtonText: "取消",
                        closeOnConfirm: false
                    }, function (isConfirm) {
                        if (isConfirm) {
                            var user={
                                id:id,
                                departmentHead:'0'
                            }
                            self.$http.post('/api/users/updateHead',user).then(function (res) {
                                if (res.data.code == 1) {
                                    self.$toastr.success("操作成功!");
                                    vueIndex.$dataTable.bootstrapTable('refresh');
                                }
                            }).catch(function () {
                                self.$toastr.error("操作失败!");
                            }).finally(function () {
                                swal.close();
                            });
                        }
                    });
                    e.stopPropagation();
                });
                //设置为部门领导
                self.$dataTable.on('click', '[data-handle="operate-dep"]', function (e) {
                    var id = $(this).data('id');
                    var company = $(this).data('company');
                    var department = $(this).data('department');
                    var text;
                    var comName = $(this).data('comname');
                    var depName = $(this).data('depname');
                        text="设置为"+comName+"公司"+depName+"的负责人"
                        swal({
                            title: "设置部门负责人",
                            text:text ,
                            type: 'warning',
                            showCancelButton: true,
                            confirmButtonColor: "#DD6B55",
                            confirmButtonText: "确定",
                            cancelButtonText: "取消",
                            closeOnConfirm: false
                        }, function (isConfirm) {
                            if (isConfirm) {
                                var user={
                                    id:id,
                                    departmentHead:'1'
                                }
                                self.$http.post('/api/users/updateHead',user).then(function (res) {
                                    if (res.data.code == 1) {
                                        self.$toastr.success("操作成功!");
                                        vueIndex.$dataTable.bootstrapTable('refresh');
                                    }
                                }).catch(function () {
                                    self.$toastr.error("操作失败!");
                                }).finally(function () {
                                    swal.close();
                                });
                            }
                        });
                        e.stopPropagation();
                });
                self.$dataTable.on('click', '[data-handle="operate-resetPassWord"]', function (e) {
                    var id = $(this).data('id');
                    self.$http.get('/api/users/' + id + '/resetPassWord').then(function (res) {
                        if (res.data.code == 1) {
                            self.$toastr.success('重置密码成功！');
                        }
                    }).catch(function () {

                    }).finally(function () {
                        swal.close();
                    });
                    e.stopPropagation();
                });

                self.$dataTable.on('click', '[data-handle="operate-setRoles"]', function (e) {
                    var id = $(this).data('id');
                    var _$modal = $('#rolesModal').clone();
                    var $modal = _$modal.modal({
                        height: 450,
                        maxHeight: 450,
                        backdrop: 'static',
                        keyboard: false
                    });
                    rolesModal($modal, id);
                });

                self.$dataTable.on('click', '[data-handle="operate-changeStatus"]', function (e) {
                    var id = $(this).data('id');
                    var status = $(this).data('status');
                    var title = '';
                    var text = '';
                    if (status == 'NORMAL') {
                        title = '启用用户';
                        text = '确定启用这个用户吗？';
                    } else if (status == 'INVALID') {
                        title = '锁定用户';
                        text = '确定锁定这个用户吗？';
                    } else if (status == 'DELETE') {
                        title = '删除用户';
                        text = '确定删除这个用户吗？';
                    }
                    swal({
                        title: title,
                        text: text,
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        var data = {
                            id: id,
                            status: status
                        };
                        self.$http.post('/api/users', data).then(function (res) {
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
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            //同步用户
            synUser: function () {
                var self = this;
                self.synName = "同步中...";
                self.synClass = "btn-warning";
                self.synBtn = true;

                self.$http.get('/api/users/synUser').then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.$toastr.success('操作成功');
                        //刷新列表
                        self.query();
                    }
                    self.synName = "同步";
                    self.synClass = "btn-primary";
                    self.synBtn = false;
                });
            },
            // 查询组织架构
            queryOrganization: function () {
                var self = this;
                self.$http.get('/api/org/findAll').then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.organizations = res.data;
                    }
                });
            },
            //根据公司id查询所有部门
            queryDepartment: function () {
                var self = this;
                self.form.departmentId = '';
                self.suppliers = null;
                if(self.form.companyId){
                    self.$http.get('/api/org/findDepartment/' + self.form.companyId).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.departments = res.data;
                        }
                    });
                }else{
                    //清空 部门
                    self.form.supplierId = '';
                    self.suppliers = null;
                    self.departments = null;
                }
            },
            //通过 部门id 查询 所有的供应商
            querySuppliers: function () {
                var self = this;
                self.form.supplierId = '';
                if(self.form.departmentId){
                    self.$http.get('/api/org/findSuppliers/' + self.form.departmentId).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.suppliers = res.data;
                        }
                    });
                }else{
                    //没选择部门,清空选择的供应商
                    self.suppliers = null;
                }
            },
            createBtnClickHandler: function (e) {
                var user = {
                    account: '',
                    name: '',
                    phone: '',
                    email: '',
                    sex: '',
                    department: '',
                    company: {id: ''},
                    remindFlag: ''
                };
                this.showModel(user, false);
            },
            showModel: function (user, isEdit) {
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    height: 500,
                    maxHeight: 500,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal, user, isEdit);
            }
        },
        created: function () {
            this.fUser = window.RocoUser;
        },
        ready: function () {
            this.queryOrganization();
            this.drawTable();
        }
    });
    // 实现弹窗方法--编辑用户
    function modal($el, model, isEdit) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueModal = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            validators: {
                mobile: function (val) {
                    return /^1[3|4|5|7|8]\d{9}$/.test(val);
                },
                email: function (val) {
                    return (/^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
                        .test(val));
                }
            },
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                //控制 按钮是否可点击
                disabled: false,
                //模型复制给对应的key
                user: model,
                //所有组织机构--赋值
                organizations: vueIndex.organizations,
                departments: [],
                submitBtnClick: false,
                title: '',
                //供应商集合
                suppliers: null,
                //供应商id
                supplierId: '',
                //供应商对象--当用户是供应商时 赋值
                suppler: null
            },
            created: function () {
            },
            ready: function () {
                if (model.company && model.company.id)
                    this.queryDepartment(model.company.id);
                if (isEdit) {
                    this.title = '编辑用户';
                } else {
                    this.title = '新增用户';
                }
            },
            watch: {
                'user.company.id': function (val) {

                    var self = this;
                    if (val && val != '') {
                        self.queryDepartment(val);
                    }
                    //清空部门选择
                    if(self.user.department){
                        self.user.department.id = '';
                    }
                    self.supplierId = '';

                    if(self.user.department == null) {
                        self.user.department={id:''};
                    }
                    deep: true
                }
            },
            methods: {
                //根据公司id查询所有部门
                queryDepartment: function (val) {
                    var self = this;
                    self.$http.get('/api/org/findDepartment/' + val).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.departments = res.data;

                            self.chooseSupplier();
                        }
                    });
                },
                //通过familyCode 查询 所有的供应商
                querySuppliers: function (val) {
                    var self = this;
                    self.$http.get('/api/org/findSuppliers/' + val).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.suppliers = res.data;
                        }
                    });
                },
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;

                    //如果 supplierId 不为空,说明选择了供应商 就将supplierId赋值给department.id
                    if(self.supplierId){
                        self.user.department.id = self.supplierId;
                        self.supplierId = "";
                    }

                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;
                            self.$http.post('/api/users', self.user).then(function (res) {
                                if (res.data.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        vueIndex.$dataTable.bootstrapTable('refresh');
                                        self.$toastr.success('操作成功');
                                    });
                                    $el.modal('hide');
                                }
                            }).finally(function () {
                                self.disabled = false;
                            });
                        }
                    });
                },
                //如果部门选择材料部,就显示该材料部下面的所有供应商
                chooseSupplier: function () {
                    var self = this;

                    //遍历当前公司对应的所有部门
                    self.departments.forEach(function (dept) {
                        if(dept.orgName == "材料部" && self.user.department && self.user.department.id){
                            if(dept.id == self.user.department.id){
                                //通过当前familyCode 去查询所有供应商
                                self.querySuppliers(dept.familyCode);
                            }else{
                                //通过当前部门id 去查询部门对象
                                self.$http.get('/api/org/' + self.user.department.id).then(function (response) {
                                    var res = response.data;
                                    if (res.code == '1') {
                                        self.suppler = res.data;
                                        var arr = self.suppler.familyCode.split("-");
                                        if(arr.length == 4){
                                            //当前是 供应商 回显
                                            self.user.department.id = arr[2];
                                            self.supplierId = arr[3];
                                            //同时加载所有供应商
                                            self.querySuppliers(arr[0] + "-" + arr[1] + "-" + arr[2]);
                                        }
                                    }
                                });

                                //清空self.supplierId
                                self.supplierId = '';
                            }
                        }else{
                            //都不满足 就清空suppliers
                            self.suppliers = null;
                        }
                    });
                },
            }
        });
        // 创建的Vue对象应该被返回
        return vueModal;
    }

    function rolesModal($el, id) {
        //获取node
        var el = $el.get(0);

        //创建Vue对象编译节点
        var vueModal = new Vue({
                el: el,
                minxins: [RocoVueMixins.ModalMixin],
                $modal: $el, //模式窗体 jQuery 对象
                data: {
                    roles: [],
                    selectedRoles: {}
                },
                created: function () {
                    this.getRoles(id);
                },
                ready: function () {
                },
                methods: {
                    //查询用户角色信息
                    getRoles: function (id) {
                        var self = this;
                        self.$http.get('/api/users/role/' + id).then(function (res) {
                            if (res.data.code == 1) {
                                self.roles = res.data.data;
                                self.setCheckedRole();//将该用户已有的角色添加到选中角色中
                            }
                        }).catch(function () {

                        }).finally(function () {

                        });
                    },
                    setCheckedRole: function () {
                        var self = this;
                        if (self.roles) {
                            $(self.roles).each(function (index, _this) {
                                if (_this.checked == true) {
                                    self.selectedRoles[_this.id] = _this.id;
                                }
                            });
                        }
                    },
                    // 查询组织架构
                    checkSub: function (role, e) {
                        var self = this;
                        var checked = e.target.checked;
                        if (checked) {
                            self.selectedRoles[role.id] = role.id;
                        } else {
                            self.selectedRoles[role.id] = null;
                        }
                    },
                    submit: function () {
                        var self = this;
                        var roles = [];
                        for (var key in self.selectedRoles) {
                            if (self.selectedRoles[key]) {
                                roles.push(self.selectedRoles[key]);
                            }
                        }
                        if (roles.length == 0) {
                            Vue.toastr.warning('请至少选择一个角色');
                            return false;
                        }
                        var data = {
                            userId: id,
                            roles: roles
                        }
                        self.$http.post('/api/users/userrole', data, {emulateJSON: true}).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                $el.modal('hide');
                                self.$destroy();
                            }
                        }).finally(function () {
                            self.disabled = false;
                        });
                    }
                }
            }
        );
        //创建的vue对象应该被返回
        return vueModal;
    }
    //兼职modal
    function showJobModal(id) {

        //给id为jobModal的重新写id
        var _$modal = $('#jobModal');
        _$modal.on('show.bs.modal', function () {
            $("#jstreeParent").html("");
            $("#jstreeParent").append('<div id="jstree"></div>');

            $("#submitId").html("");
            $("#submitId").append('<button  @click="submit" :disabled="disabled" type="button" class="btn btn-primary">提交</button>');
        });
        var $modal = _$modal.modal({
            height: 500,
            maxHeight: 500,
            backdrop: 'static',
            keyboard: true,
        });

        //获取node
        var el = _$modal.get(0);

        //创建Vue对象编译节点
        var jobModal = new Vue({
                el: el,
                minxins: [RocoVueMixins.ModalMixin],
                $modal: _$modal, //模式窗体 jQuery 对象
                data: {
                    //用户id
                    userId: id,
                    //选择的兼职部门id
                    partTimeJob: '',
                },
                created: function () {
                    this.fetchOrgTree(id);
                },
                ready: function () {
                },
                methods: {
                    fetchOrgTree: function (userId) {
                        var self = this;
                        var _$jstree = $('#jstree');
                        //查询组织机构tree,并显示modal
                        self.$http.get('/api/org/jobTree/' + userId).then(function (res) {
                            if (res.data.code == 1) {
                                //加载jstree
                                _$jstree.jstree({
                                    core: {
                                        multiple: true,
                                        // 不加此项无法动态删除节点
                                        check_callback: true,
                                        data: res.data.data
                                    },
                                    types: {
                                        default: {
                                            icon: 'glyphicon glyphicon-stop',
                                        }
                                    },
                                    plugins: ['types', 'wholerow', 'changed', 'checkbox'],
                                    "checkbox": {
                                        "keep_selected_style": true,//是否默认选中
                                        "three_state": false,//父子级别级联选择
                                        "tie_selection": true
                                    },
                                });
                                /*//单击事件
                                _$jstree.bind('click.jstree', function(event) {
                                    /!*var eventNodeName = event.target.nodeName;
                                     if (eventNodeName == 'INS') {
                                     return;
                                     } else if (eventNodeName == 'A') {
                                     var $subject = $(event.target).parent();
                                     if ($subject.find('ul').length > 0) {
                                     } else {
                                     //选择的id值
                                     //alert($(event.target).parents('li').attr('id'));
                                     }
                                     }*!/
                                    var ref = _$jstree.jstree(true);
                                    var sel = ref.get_checked(true);
                                    if(!sel.length){
                                        return ;
                                    }
                                    /!*sel.forEach(function (item, i) {
                                        if(item.)
                                    })*!/
                                });*/
                            }
                        });
                    },
                    //提交
                    submit: function () {
                        var self = this;
                        //清空上次选中的值
                        self.partTimeJob = '';
                        self.disabled = true;

                        var ref = $('#jstree').jstree(true);
                        var sel = ref.get_checked(true);

                        //点击选中后,将当前所选择的部门id保存下来,并用&拼接多个部门id
                        sel.forEach(function (dept, index) {
                            if(index < sel.length - 1){
                                self.partTimeJob += dept.id + "&";
                            }else{
                                self.partTimeJob += dept.id;
                            }
                        });
                        //提交修改user对象
                        var data = {
                            id: self.userId,
                            partTimeJob: self.partTimeJob
                        }
                        self.$http.post('/api/users/', data).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('操作成功');
                                self.disabled = false;
                                $modal.modal('hide');
                                self.$destroy();
                            }
                        }).finally(function () {
                            self.disabled = false;
                        });
                    }
                }
            }
        );
        //创建的vue对象应该被返回
        return jobModal;

    }
})
(this.RocoUtils);