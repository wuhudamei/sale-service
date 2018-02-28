var vueIndex = null;
+(function (RocoUtils) {
    $('#workOrderList').addClass('active');
    $('#received').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        components: {
            'web-uploader': RocoVueComponents.WebUploaderComponent
        },
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '处理',
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
            treamentPlan: '',
            treamentTime: '',
            brand: '0',
            brands: null,
            //此事项最大完成时限
            duration: '',
            placeholder: '请选择预计完成时间',
            submitting: false,
            //是否展示申诉按钮
            showRejectBtn: RocoUtils.hasPermission('workorder:reject-btn'),
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
            cancel: function(){
                window.history.go(-1);
            },
            //申诉
            rejectClickHandler: function () {
                var self = this;
                var data = {
                    id: vueIndex.id,
                    treamentPlan: self.treamentPlan,
                    brand: self.brand.toString(),
                    operationType: 'REJECT',
                    //图片: 原来的+本次新增的
                    photo : self.order.photo + self.photo,
                };
                if(!self.treamentPlan){
                    self.$toastr.error("申诉理由不能为空!");
                    return;
                }
                self.submitting = true;
                
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        self.$http.post('/mdni/workorder/updateWorkOrder', data).then(function (res) {
                            if (res.data.code == 1) {
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
                    }
                });
            },
            //完成--提交
            submitClickHandler: function () {
                var self = this;
                if (self.submitting) {
                    return false;
                }
                //如果是 材料部、好得很、小定制、木门必须确认品牌
                if((!self.brand || self.brand == 0) && (RocoUser.departmentName == '材料部'
                    || RocoUser.departmentName == '好得很部' || RocoUser.departmentName == '小定制部'
                    || RocoUser.departmentName == '木门事业部')){
                    self.$toastr.error('材料部、好得很部、小定制部、木门事业部必须确认品牌!');
                    return ;
                }

                self.$validate(true, function () {
                    if (self.$validation.valid) {
                    	if(self.treamentTime == null || self.treamentTime == ""){
                    		self.$toastr.error('预计完成时间不能为空!');
                    		self.submitting = false;
                    		return false;
                    	}
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
                    operationType: 'OPERATION',
                    //图片: 原来的+本次新增的
                    photo : self.order.photo + self.photo,
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
            //根据门店,部门,类别,类型 查询时限
            getDurationByQuery: function () {
                var self = this;
                self.$http.get('/mdni/timeLimit/getLimitTime?companyId=' + self.order.liableCompany.id
                    + '&departmentId=' + self.order.liableDepartment.id
                    + '&questionCategoryId=' + self.order.questionType1.id
                    + '&questionTypeId=' + self.order.questionType2.id
                    + '&createDate=' + self.order.createDate).then(function (res) {
                        if(res.data.code == 1 && res.data.data){
                            self.duration = res.data.data.duration;
                            var finalDate = res.data.data.finalDate;
                            self.placeholder = "应在" + finalDate + "之前完成";
                        }
                });
            },
            getOrderDetails: function (orderId) {
                var self = this;
                self.$http.get('/mdni/workorder/' + orderId + '/get').then(function (res) {
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

                        //如果品牌为空,就给默认值0
                        if(!self.order.brand){
                            self.order.brand = 0;
                        }

                        //查询时限
                        self.getDurationByQuery();

                        self.brand=self.order.brand;
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
            },
            activeDatePicker: function () {
                $(this.$els.treamentTime).datetimepicker({
                    format: 'yyyy-mm-dd hh:00',
                    minView: 'day',
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
            this.user = window.RocoUser;
            this.fetchBrand();
        },
        ready: function () {
            this.activeDatePicker();
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
(this.RocoUtils);