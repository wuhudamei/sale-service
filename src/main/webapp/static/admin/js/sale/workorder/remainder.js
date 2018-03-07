var vueIndex = null;
+(function (DameiUtils) {
    $('#ordermanage').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '工单库',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            //工单id
            workOrderId: '',
            organizations: '',
            treamentPlan: '',
            treamentTime: '',
            //contractStartAndEndTime: '',
            //leaders: '',
            complaintTypes: '',
            form: {
            	complaintType: '',
            	remark: '',
            	
            },
            submitting: false,
            source: '',
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
            links:[],
            order: '',
            //返回路径参数
            params: ''
        },
        methods: {
        	//返回
        	cancel: function(){
        		window.history.go(-1);
        	},
        	//提交
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
                	workOrderId: self.workOrderId,
                	//操作类型:催单
                	operationType: 'REMAINDER',
                	'complaintType.id': self.form.complaintType,
                	remark: self.form.remark,
                };
                self.submitting = true;
                
                self.$http.post('/damei/workOrderRmk/remainder', data,{
  	              		emulateJSON: true }).then(function (res) {
                    if (res.data.code == 1) {
                        self.$toastr.success('提交成功');
                        //返回
                        setTimeout(function(){
                        	if(self.source == 'store'){
                        		window.location.href = "/workorder/storeWorkOrderList?" + self.params;
                        	}else if(self.source == 'group'){
                        		window.location.href = "/workorder/groupWorkOrderList?" + self.params;
                        	}
                        	//请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                        	//self.submitting = false;
                        },1500);
                    }
                }).catch(function () {
                }).finally(function () {
                	
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
            //初始化数据
            initParam: function(){
            	var self = this;
            	self.workOrderId = DameiUtils.parseQueryString()['workOrderId'];
            	self.source = DameiUtils.parseQueryString()['source'];
            	if(self.source == 'store'){
            		$('#storeWorkOrder').addClass('active');
            	}else if(self.source == 'group'){
            		$('#groupWorkOrder').addClass('active');
            	}
            	
            	//返回参数
            	self.params = window.location.href.split("?")[1]; 
            	
                
            	//通过客户id查询客户信息
            	/*self.$http.get('/damei/workorder/'+ self.workOrderId +'/get').then(function (res) {
                    if (res.data.code == 1) {
                    	self.workOrder = res.data.data;
                    	//合同开始/竣工时间
                    	self.contractStartAndEndTime = (res.data.data.contractStartTime || '') + " / "
                								+ (res.data.data.contractCompleteTime || '');
                    	
                    	//设计师/项目经理/监理:
                    	self.leaders = (res.data.data.styListName || " ") + " / " 
								+ (res.data.data.contractorName || ' ') + " / " 
								+ (res.data.data.supervisorName || ' ');
                    }
                });*/
            },
            
            //投诉原因
            findComplaintTypes: function(){
            	var self = this;
            	self.$http.get('/api/dict/dic/getByType?type=8').then(function (res) {
                    if (res.data.code == 1) {
                    	self.complaintTypes = res.data.data;
                    }
                });
            },
          //处理记录
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
            //工单信息
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
                        if(self.order.questionType1){
	                        self.$http.get('/api/dict/dic/' + self.order.questionType1.id).then(function (res) {
	                            if (res.data.code == 1) {
	                                self.order.questionType1 = res.data.data;
	                            }
	                        });
                        }
                        self.$http.get('/api/dict/dic/' + self.order.questionType2.id).then(function (res) {
                            if (res.data.code == 1) {
                                self.order.questionType2 = res.data.data;
                            }
                        });
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
            }
        },
        created: function () {
            this.initParam();
        },
        ready: function () {
        	this.findComplaintTypes();
            this.activeDatePicker();
            this.getRemarks(this.workOrderId);
            this.getOrderDetails(this.workOrderId);
        }
    });

})
(this.DameiUtils);