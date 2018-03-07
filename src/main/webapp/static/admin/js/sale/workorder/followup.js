var vueIndex = null;
+(function (DameiUtils) {
    $('#workOrderList').addClass('active');
    $('#processing').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        components: {
            'web-uploader': DameiVueComponents.WebUploaderComponent
        },
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '跟进',
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
            //工单对象
            order: '',
            organizations: '',
            treamentPlan: '',
            treamentTime: '',
            brand: '0',
            brands:null,
            submitting: false,
            //本次添加图片数组
            photoLinks: [],
            //本次添加图片
            photo: '',
            webUploaderSub: {
                type: 'sub',
                formData: {},
                accept: {
                    title: '文件',
                    extensions: 'jpg,jpeg,png'
                },
                server: '/api/upload',
                //上传路径
                fileNumLimit: 5,
                fileSizeLimit: 50000 * 1024,
                fileSingleSizeLimit: 5000 * 1024
            },
        },
        methods: {
            fetchBrand: function () {
                var self = this;
                this.$http.get('/api/dict/brand/findAll').then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                        self.brands = res.data;
                    }
                })
            },
            rejectClickHandler: function () {
                var self = this;
                var data = {
                    id: vueIndex.id,
                    treamentPlan: self.treamentPlan,
                    treamentTime: self.treamentTime,
                    brand: self.brand,
                    operationType: 'REJECT'
                };
                self.submitting = true;
                self.$http.post('/damei/workorder/updateWorkOrder', data).then(function (res) {
                    if (res.data.code == 1) {
                        self.$toastr.success('提交成功');
                        setTimeout(function(){
                        	window.location.href = "/workorder/list?status=FOLLOWUP";
                        },1500);
                    }
                }).catch(function () {
                }).finally(function () {
                    //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                    //self.submitting = false;
                });
            },
            //工单完成
            finishOrder: function () {
                var self = this;
                if (self.submitting) {
                    return false;
                }
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.finish();
                    }
                });
            },
            finish: function () {
                var self = this;
                var data = {
                    id: vueIndex.id,
                    treamentPlan: self.treamentPlan,
                    operationType: 'FINISHORDER',
                    //图片: 原来的+本次新增的
                    photo : self.order.photo + self.photo,
                };

                swal({
                        title: '确定完成工单吗？',
                        text: '确认完成后,工单状态不可回退!',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.submitting = true;
                        self.$http.post('/damei/workorder/updateWorkOrder', data).then(function (res) {
                            if (res.data.code == 1) {
                                self.$toastr.success('提交成功');
                                setTimeout(function () {
                                    window.location.href = "/workorder/list?status=PROCESSING";
                                }, 1500);
                            }
                        }).catch(function () {
                        }).finally(function () {
                            swal.close();
                        });
                });
            },
            //处理提交
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
                    treamentPlan: self.treamentPlan,
                    treamentTime: self.treamentTime,
                    brand: self.brand.toString(),
                    operationType: 'FOLLOWUP',
                    //图片: 原来的+本次新增的
                    photo : self.order.photo + self.photo,
                };
                self.submitting = true;

                self.$http.post('/damei/workorder/updateWorkOrder', data).then(function (res) {
                    if (res.data.code == 1) {
                        self.$toastr.success('提交成功');
                        setTimeout(function(){
                        	window.location.href = "/workorder/list?status=PROCESSING";
                        },1500);
                    }
                }).catch(function () {
                }).finally(function () {
                    //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                    //self.submitting = false;
                });
            },
            cancel: function(){
                window.history.go(-1);
            },
            getOrderDetails: function (orderId) {
                var self = this;
                self.$http.get('/damei/workorder/' + orderId + '/get').then(function (res) {
                    if (res.data.code == 1) {
                        self.order = res.data.data;
                        self.treamentPlan = self.order.treamentPlan;

                        //将problem中的\n替换成</br>
                        if(self.order.problem){
                            self.order.problem = self.order.problem.replace(/\n/g,'<br/>');
                        }
                        //将treamentPlan中的\n替换成</br>
                        if(self.order.treamentPlan) {
                            self.order.treamentPlan = self.order.treamentPlan.replace(/\n/g, '<br/>');
                        }

                        if(self.order.brand){
                            self.brand = self.order.brand.toString();
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
            //删除图片
            deleteFlie: function () {
                var self = this;
                self.$http.delete('/api/upload', {
                    params: {
                        path: self.photo
                    }
                }).then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.photo = '';
                        self.photoLinks = [];
                    } else {
                        self.$toastr.error("删除失败");
                    }
                });
            },
        },
        created: function () {
            this.user = window.DameiUser;
            this.fetchBrand();
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
        },
        events: {
            'webupload-upload-success-sub': function (file, res) {
                var self = this;
                if (res.code == '1') {
                    self.$toastr.success('上传成功');
                    self.photo += res.data.path + ',';
                } else {
                    self.$toastr.error(res.message);
                }
            }
        },
        watch: {
            "photo": function () {
                var self = this;
                self.photoLinks = [];
                if (self.photo) {
                    var arry = self.photo.split(',');
                    _.each(arry, function (ele) {
                        self.photoLinks.push({a: ele});
                    })
                }
            }
        }

    });

})
(this.DameiUtils);