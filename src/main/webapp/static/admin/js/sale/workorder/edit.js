var vueIndex = null;
+(function (RocoUtils) {
    $('#workOrderList').addClass('active');
    $('#assign').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '工单分配',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            user: '',
            id:'',
            showOrgTree: false, // 是否显示机构树
            showSrcOrgTree: false,
            liableDepartmentName: null,
            orgData: null, // 机构树数据
            links: [],
            remarks: [],
            operations: {
                'CREATE': '创建',
                'CONSULTOVER': "咨询完毕",
                'OPERATION': '处理',
                'OPERATIONAGAIN':'重新分派责任部门',
                'RECEIVE': '接收',
                'FOLLOWUP': '跟进',
                'REJECT': '责任部门申诉',
                'REJECTAGAIN': "客管部门再次申诉",
                'ASSIGN': '分配',
                'REPLY': '回复',
                'CALLBACK': '再联系',
                'VISIT': '回访',
                'FINISHORDER': '完成工单',
                'REMARK': '备注',
                'CLOSED': '结案',
                'UNEXECUTED': '回访未执行',
                'REMAINDER': '催单',
                'SELECTREMAINDER': '查看催单'
            },
            questionType1s:'',
            questionType2s:'',
            order: '',
            form:{
            	liableDepartment:'',
            	questionType1:'',
            	questionType2:'',
            	problem: '',
                suggestion: '',
            	workType: ''
            },
            organizations: '',
            treamentPlan: '',
            treamentTime: '',
            brand:''
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            rejectClickHandler: function () {
                var self = this;
                var data = {
                    id:vueIndex.id,
                    operationType:'REJECTAGAIN',
                    problem: self.form.problem, 
                    workType: self.form.workType,
                    suggestion:  self.form.suggestion
                };
                self.$http.post('/mdni/workorder/updateWorkOrder',data).then(function (res) {
                    if (res.data.code == 1) {
                        self.$toastr.success('提交成功');
                        window.location.href = "/workorder/list?status=ASSIGN";
                    }
                }).catch(function () {
                }).finally(function () {
                    //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                    self.submitting = false;
                });
            },
            // 获取机构选择树的数据
            fetchOrgTree: function () {
                var self = this;
                this.$http.get('/api/org/fetchDept').then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                        self.orgData = res.data;
                    }
                })
            },
            // 勾选机构数外部时，隐藏窗口
            clickOut: function () {
                this.showOrgTree = false;
                this.showSrcOrgTree = false;
            },
            // 选择机构时回调事件
            selectOrg: function (node) {
                var self = this;
                self.liableDepartmentName = node.name;
                self.form.liableDepartment = node.id;
                this.showOrgTree = false
            },
            submitClickHandler: function () {
                var self = this;
                if (self.submitting) {
                    return false;
                }
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.submit();
                    }
                });
            },
            submit: function () {
                var self = this;
                var data = {
                    id:vueIndex.id,
                    liableDepartment: self.form.liableDepartment,
                    questionType1:self.form.questionType1,
                    questionType2:self.form.questionType2,
                    operationType:'OPERATIONAGAIN',
                    problem: self.form.problem, 
                    workType: self.form.workType,
                    suggestion:  self.form.suggestion
                };
                self.submitting = true;

                self.$http.post('/mdni/workorder/updateWorkOrder', data).then(function (res) {
                    if (res.data.code == 1) {
                        self.$toastr.success('提交成功');
                        window.location.href = "/workorder/list?status=ASSIGN";
                    }
                }).catch(function () {
                }).finally(function () {
                    //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                    self.submitting = false;
                });
            },
            cancel: function(){
                window.history.go(-1);
            },
            getOrderDetails: function (orderId) {
                var self = this;
                self.$http.get('/mdni/workorder/' + orderId + '/get').then(function (res) {
                    if (res.data.code == 1) {
                        self.order = res.data.data;

                        //给问题 和 工单类型 赋值
                        self.form.problem = self.order.problem;
                        self.form.workType = self.order.workType;
                        //给分配中工单分类赋值
                        if (self.order.photo) {
                            var arry = self.order.photo.split(',');
                            _.each(arry, function (ele) {
                                self.links.push({a: ele});
                            })
                        }

                        //将problem中的\n替换成</br>
                        if(self.order.problem){
                            self.order.problem = self.order.problem.replace(/\n/g,'<br/>');
                        }
                        //将treamentPlan中的\n替换成</br>
                        if(self.order.treamentPlan) {
                            self.order.treamentPlan = self.order.treamentPlan.replace(/\n/g, '<br/>');
                        }

                        if(self.order.workType){
                            if(self.order.workType == 'PRESALE'){
                                self.order.workTypeName = '售前';
                            }
                            if(self.order.workType == 'SELLING'){
                                self.order.workTypeName = '售中';
                            }
                            if(self.order.workType == 'AFTERSALE'){
                                self.order.workTypeName = '售后';
                            }
                        }

                        if(self.order.complaintType){
                            self.$http.get('/api/dict/dic/' + self.order.complaintType.id).then(function (res) {
                                if (res.data.code == 1) {
                                    self.order.complaintType = res.data.data;
                                }
                            });
                        }
                        if(self.order.questionType1) {
                            self.$http.get('/api/dict/dic/' + self.order.questionType1.id).then(function (res) {
                                if (res.data.code == 1) {
                                    self.order.questionType1 = res.data.data;
                                }
                            });
                        }
                        if(self.order.questionType2) {
                            self.$http.get('/api/dict/dic/' + self.order.questionType2.id).then(function (res) {
                                if (res.data.code == 1) {
                                    self.order.questionType2 = res.data.data;
                                }
                            });
                        }


                        //品牌
                        if(self.order.brand){
                            self.$http.get('/api/dict/brand/' + self.order.brand).then(function (res) {
                                if (res.data.code == 1) {
                                    self.order.brand = res.data.data;
                                }
                            });
                        }

                        //工单来源
                        if(self.order.source){
                            self.$http.get('/api/dict/dic/' + self.order.source.id).then(function (res) {
                                if (res.data.code == 1) {
                                    self.order.source = res.data.data;
                                }
                            });
                        }

                    }
                });
            },
            getRemarks: function (orderId) {
                var self = this;
                self.$http.get('/mdni/workorder/' + orderId + '/getRemarks').then(function (res) {
                    if (res.data.code == 1) {
                        self.remarks = res.data.data;
                        self.remarks.forEach(function (remark) {
                            var operationName = self.operations[remark.operationType];
                            if (operationName) {
                                remark.operationName = operationName;
                            } else {
                                remark.operationName = remark.operationType;
                            }
                        });
                    }
                });
            },
            getQuestionTypes: function (val, type) {
                var self = this;
                self.$http.get('/api/dict/dic/getByComLiableDep', {
                    params: {
                        'type': type,
                        'depId': val
                    }
                }).then(function (res) {
                    if (res.body.code == 1) {
                        self.questionType1s = res.body.data;
                        if (self.form.questionType1 == null) {
                            self.form.questionType1 = self.questionType1s[0].id;
                        }
                    }
                }).catch(function () {

                }).finally(function () {

                });
            },
            getImportantList: function (id, type, event) {
                var self = this;
                self.$http.get('/api/dict/dic/getByType', {
                    params: {
                        'parentType': id,
                        'type': type
                    }
                }).then(function (res) {
                    if (res.body.code == 1) {
                        if (type == 6) {
                            self.questionType2s = res.body.data;
                            if (event == 'watch') {
                                if (self.questionType2s[0] != undefined) {
                                    self.form.questionType2 = self.questionType2s[0].id;
                                }
                            }
                        }
                    }
                });
            },
            activeDatePicker: function () {
                $(this.$els.treamentTime).datetimepicker({
                    format: 'yyyy-mm-dd hh:00',
                    minView: 'day',
                    autoSize: true
                });

                $(this.$els.feedbackTime).datetimepicker({
                    format: 'yyyy-mm-dd hh:ii:ss',
                    minView: 'hour',
                    autoSize: true
                });
            }
        },
        created: function () {
            this.user = window.RocoUser;

        },
        watch: {
            "form.liableDepartment": function (val) {
                this.getQuestionTypes(val, 5);
            },
            "form.questionType1": function (val, oldVal) {
                var self = this;
//                    var importantId = self.importantDegree1s[val].id;
                self.getImportantList(val, 6, 'watch');
            }
        },
        ready: function () {
            this.activeDatePicker();
            var params = RocoUtils.parseQueryString(window.location.search.substr(1));
            if (params) {
                for (var key in params) {
                    var value = params[key];
                    this.id = value;

                }
            };
            this.getOrderDetails(params.id);
            this.getRemarks(params.id);
            this.fetchOrgTree();
        }
    });

})
(this.RocoUtils);