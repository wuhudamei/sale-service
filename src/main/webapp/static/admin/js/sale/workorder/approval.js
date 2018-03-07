var vueIndex = null;
+(function (DameiUtils) {
    $('#workOrderList').addClass('active');
    $('#').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '延期审核',
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
            order: '',
            organizations: '',
            treamentTime: '',
            brand: '0',
            brands:null,
            submitting: false,
            approval:null,
            result:{
                approvalResult:"1",
                remarks:null,
                workorderId:null,
                approvalId:null
            }
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            fetchBrand: function () {
                var self = this;
                this.$http.get('/api/dict/brand/findAll').then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                        self.brands = res.data;
                    }
                })
            },
            //处理提交
            submitClickHandler: function () {
                var self = this;
                if (self.submitting) {
                    return false;
                }
                var title='';
                if(self.result.approvalResult=='1'){
                    title+='确定通过这条延期审批';
                }else if(self.result.approvalResult=='2'){
                    title+='确定驳回这条延期审批';
                }
                swal({
                    title: '审批',
                    text: title,
                    type: 'info',
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    showCancelButton: true,
                    showConfirmButton: true,
                    showLoaderOnConfirm: true,
                    confirmButtonColor: '#ed5565',
                    closeOnConfirm: false
                }, function () {
                    self.$http.post('/damei/worktime/result',self.result).then(function (res) {
                        if (res.data.code == 1) {
                            window.location.href="/workorder/approvalList";
                            self.$toastr.success('操作成功');
                        }
                    }).catch(function () {
                    }).finally(function () {
                        swal.close();
                        //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                        //self.submitting = false;
                    });
                });

            },
            cancel: function(){
                window.history.go(-1);
            },
            fetchApproval: function(id,ids){
                var self=this;
                self.result.workorderId=ids;
                self.result.approvalId=id;
                self.$http.get('/damei/worktime/approvalById?id='+id).then(function (res) {
                    if (res.data.code == 1) {
                        self.approval=res.data.data;
                    }
                }).catch(function () {
                }).finally(function () {
                    //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                    //self.submitting = false;
                });

            },
            getOrderDetails: function (orderId) {
                var self = this;
                self.$http.get('/damei/workorder/' + orderId + '/get').then(function (res) {
                    if (res.data.code == 1) {
                        self.order = res.data.data;

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
                self.$http.get('/damei/workorder/' + orderId + '/getRemarks').then(function (res) {
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
            },
            //校验网页来源
            checkFromWx: function () {
                var ua = window.navigator.userAgent.toLowerCase();
                if(ua.match(/MicroMessenger/i) == 'micromessenger'){
                    return true;
                }else{
                    return false;
                }
            },
        },
        created: function () {
            this.user = window.DameiUser;
            this.fetchBrand();
        },
        ready: function () {
            var uri = window.location.search.substr(1);
            if(this.checkFromWx()){
                //来自微信端 替换成&
                uri = uri.replace(';','&');
            }
            var params = DameiUtils.parseQueryString(uri);
            if (params) {
                for (var key in params) {
                    var value = params[key];
                    this.id = value;
                }
            };
            this.fetchApproval(params.ids,params.id);
            this.getOrderDetails(params.id);
            this.getRemarks(params.id);
        }
    });

})
(this.DameiUtils);