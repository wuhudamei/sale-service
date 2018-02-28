var vueIndex = null;
+(function (RocoUtils) {
    $('#workOrderVisit').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            // 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '/',
                name: '工单回访',
                active: true //激活面包屑的
            }],
            $dataTable: null,
            //工单id
            workOrderId: '',
            form: {
            	//回访结果
            	orderStatus: '',
            	complaintType: '',
            	remark: '',
            	//是否生成新工单
            	isNew: false,
            	customerFeedbackTime: '',
            	importantDegree1: ''
            	
            },
            submitting: false,
            source: '',
            complaintTypes: [],
            importances: [],
            remarks: [],
            order: '',
            //返回路径参数
            params: '',
            //不满意
            unsatisfied: false,
            links:[],
            /*operations: {
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
            },*/
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
        },
        methods: {
        	//返回
        	cancel: function(){
        		var self = this;
        		window.history.go(-1);
        	},
        	//提交
            submitClickHandler: function () {
                var self = this;
                if (self.submitting) {
                    return false;
                }
                self.$validate(true, function () {
                	//选择新工单
                	var flag = false;
                	
                	//如果选择了不满意,必须要选择 不满意原因
            		if(self.form.orderStatus == 'UNSATISFIED' && !self.form.complaintType){
            			self.$toastr.error("请选择不满意原因");
            			return;
            		}
            		if(self.form.isNew == 'false' && self.form.orderStatus){
                		flag = true;
                	}else if(self.form.isNew == 'true'){
                		if(self.form.orderStatus && self.form.remark && self.form.customerFeedbackTime){
                			flag = true;
                		}
                	}
            		
            		
                    if (self.$validation.valid || flag) {
                    	self.submit();
                    }else{
                    	self.$toastr.error("请填写必填项!");
                    }
                });
            },
            submit: function () {
                var self = this;
                self.submitting = true;
                var copyFlag = "N";
                if(self.form.isNew == 'true'){
                	//生成新工单:将copyFlag 改为Y(要生成新工单)
                	copyFlag = "Y";
                }
                //更新工单
                //不生成新工单,更新原工单状态为:回访结果
            	var workOrder = {
            			//工单id
            			id: self.workOrderId,
            			//投诉原因--不满意原因,不去更新工单表,只是存到轨迹表中
                    	//'complaintType.id': self.form.complaintType,
                    	//回访结果
                    	visitResult: self.form.remark,
                    	//工单状态
                    	orderStatus: self.form.orderStatus,
                    	copyFlag: copyFlag
            	};
            	//更新工单
                self.$http.post('/mdni/workorder/updateWorkOrderById', workOrder,{
	              		emulateJSON: true }).then(function (res) {
	              			if(res.data.code == 1){
	              				//插入工单轨迹
	                			var data = {
	              	        			//工单id
	                					workOrderId: self.workOrderId,
	              	                	//操作类型:回访
	              	        			operationType: 'VISIT',
	              	        			//投诉原因--在回访时,将不满意原因存储到轨迹表中
	              	                	'complaintType.id': self.form.complaintType,
	              	                	//备注
	              	                	remark: self.form.remark
	              	                };
	              	            	//添加工单轨迹表
	              	                self.$http.post('/mdni/workOrderRmk/add', data,{
	              	              		emulateJSON: true }).then(function (res) {
	              	                    if (res.data.code == 1) {
	              	                    }else{
	              	                    	vueIndex.$toastr.error("提交失败!");
	              	                    	return;
	              	                    }
	              	                }).catch(function () {
	              	                	vueIndex.$toastr.error("提交失败!");
	              	                	return;
	              	                }).finally(function () {
	              	                });
	              			}else{
	              				vueIndex.$toastr.error("提交失败!");
	              				return;
	              			}
		                }).catch(function () {
		                }).finally(function () {
		                });
                
                if(self.form.isNew == 'true'){
                	//1.更新原工单状态为:回访结果
                	//2.新增新工单
                	var workOrder = {
                			//工单id
                			workOrderId: self.workOrderId,
                			//投诉原因
                        	'complaintType.id': self.form.complaintType,
                        	//问题:回访备注
                        	problem: self.form.remark,
                        	//客户要求回电时间
                        	customerFeedbackTime: self.form.customerFeedbackTime,
                        	//重要程度
                        	'importantDegree1.id': self.form.importantDegree1,
                        	//工单状态
                        	orderStatus: RocoUtils.parseQueryString()['orderStatus']
                        	
                	};
                	//生成新工单,同时新增工单轨迹
                	self.$http.post('/mdni/workorder/addNewOrderByOldIdWithOrderRMK', workOrder,{
  	              		emulateJSON: true }).then(function (res) {
	                    if (res.data.code == 1) {
	                    }
	                }).catch(function () {
	                }).finally(function () {
	                	
	                });
                }
                vueIndex.$toastr.success("提交成功!");
                //返回
            	setTimeout(function(){
            		if(self.source == 'completed'){
            			window.location.href = "/workorder/workOrderListWithComplete?" + self.params;
            		}else if(self.source == 'invalidVisit'){
            			window.location.href = "/workorder/workOrderListWithInvalid?" + self.params;
            		}else if(self.source == 'failureVisit'){
            			window.location.href = "/workorder/workOrderListWithUnsuccessful?" + self.params;
            		}
            		
            		//self.submitting = false;
            	},1500);
            },
            
            activeDatePicker: function () {
            	var self = this;
            	$('#customerFeedbackTime', self._$el).datetimepicker({
            		/*format: 'yyyy-mm-dd hh:ii:ss',
                    minView: 'hour',
                    autoSize: true*/
            	});
               
            },
            //初始化数据
            initParam: function(){
            	var self = this;
            	self.workOrderId = RocoUtils.parseQueryString()['workOrderId'];
            	self.source = RocoUtils.parseQueryString()['source'];
            	if(self.source == 'completed'){
            		$('#completedWordOrder').addClass('active');
            	}else if(self.source == 'invalidVisit'){
            		$('#invalidReturnVisit').addClass('active');
            	}else if(self.source == 'failureVisit'){
            		$('#unsuccessful').addClass('active');
            	}
            	
            	//返回参数
            	self.params = window.location.href.split("?")[1]; 
            	
            	//通过客户id查询客户信息
            	self.$http.get('/mdni/workorder/'+ self.workOrderId +'/get').then(function (res) {
                    if (res.data.code == 1) {
                    	self.order = res.data.data;
                    	////给出当前工单的重要程度
                    	self.form.importantDegree1 = self.order.importantDegree1.id;
                    }
                });
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
            //重要程度
            findImportances: function(){
            	var self = this;
            	self.$http.get('/api/dict/dic/getByType?type=7').then(function (res) {
                    if (res.data.code == 1) {
                    	self.importances = res.data.data;
                    }
                });
            },
            //处理记录
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
            //工单信息
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
                        if(self.order.questionType2){
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
            //选择了不满意时,让不满意原因为 必选
            chooseUnsatisfied: function(){
            	var self = this;
            	if(self.form.orderStatus == 'UNSATISFIED'){
            		self.unsatisfied = true;
            	}else{
            		self.unsatisfied = false;
            	}
            }
            
        },
        created: function () {
            this.initParam();
        },
        ready: function () {
        	this.findComplaintTypes();
        	this.findImportances();
            this.activeDatePicker();
            this.getRemarks(this.workOrderId);
            this.getOrderDetails(this.workOrderId);
        }
    });

})
(this.RocoUtils);