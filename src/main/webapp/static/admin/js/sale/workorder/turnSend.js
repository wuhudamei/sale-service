var vueIndex = null;
+(function (RocoUtils) {
    $('#workOrderList').addClass('active');
    $('#received').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '转派',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            user: '',
            id: '',
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
                'SELECTREMAINDER': '查看催单',
                'MODIFYEXPECTEDTIME': '修改预期时间',
                'TURNSEND' : '转派'
            },
            submitting: false,
            departments: null,
            department: '',
            order: '',
            brand: '',
            //供应商名称
            departmentName: '',
        },
        methods: {
            cancel: function(){
                window.history.go(-1);
            },
            //获取供应商名称
            getDeptName: function () {
                var self = this;
                if(self.departments){
                    self.departments.forEach(function (dept) {
                       if(self.department == dept.id){
                           self.departmentName = dept.orgName;
                       }
                    });

                }
            },
            //查询该材料部下的所有供应商
            findDepartments: function () {
                var self = this;
                //拼接familyCode
                var familyCode = "1-" + RocoUser.company + "-" +RocoUser.department;
                self.$http.get('/api/org/findSuppliers/' + familyCode).then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.departments = res.data;
                    }
                });
            },
            //提交,1.将工单责任部门改为 供应商; 2.插入一条新轨迹
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
                    id: vueIndex.id,
                    liableDepartment: vueIndex.department,
                    //供应商名称
                    departmentName: vueIndex.departmentName,
                    operationType: 'TURNSEND'
                };
                self.submitting = true;
                self.$http.post('/mdni/workorder/updateWorkOrder', data).then(function (res) {
                    if (res.body.code == 1) {
                        self.$toastr.success('提交成功');
                        setTimeout(function(){
                        	window.location.href = "/workorder/list?status=RECEIVED";
                        },1500);
                    }
                }).catch(function () {
                }).finally(function () {
                	//请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                	//self.submitting = false;
                });
            },

            //工单详情tab页
            getOrderDetails: function (orderId) {
                var self = this;
                self.$http.get('/mdni/workorder/' + orderId + '/get').then(function (res) {
                    if (res.data.code == 1) {
                        self.order = res.data.data;
                        self.brand = self.order.brand;

                        //将problem中的\n替换成</br>
                        if(self.order.problem){
                            self.order.problem = self.order.problem.replace(/\n/g,'<br/>');
                        }
                        //将treamentPlan中的\n替换成</br>
                        if(self.order.treamentPlan) {
                            self.order.treamentPlan = self.order.treamentPlan.replace(/\n/g, '<br/>');
                        }

                        if (self.order.photo) {
                            var arry = self.order.photo.split(',');
                            _.each(arry, function (ele) {
                                self.links.push({a: ele});
                            })
                        }

                        if(self.order.workType){
                            if(self.order.workType=='PRESALE'){
                                self.order.workTypeName='售前';
                            }
                            if(self.order.workType=='SELLING'){
                                self.order.workTypeName='售中';
                            }
                            if(self.order.workType=='AFTERSALE'){
                                self.order.workTypeName='售后';
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
            }
        },
        created: function () {
            this.user = window.RocoUser;
            this.findDepartments();
        },
        ready: function () {
            var params = RocoUtils.parseQueryString(window.location.search.substr(1));
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
(this.RocoUtils);