var order;
var orderList;
var operation;
+(function (DameiUtils,moment) {
    orderList = new Vue({
        validators: {
            laterThanStart: function (val, startTime) {
                try {
                    var end = moment(val);
                    return end.isAfter(startTime);
                } catch (e) {
                    return false;
                }
            }
        },
        el: '#container',
        mixins: [DameiVueMixins.DataTableMixin],
        data: {
            user:_.extend({}, window.DameiUser),
            fUser: null,
            form: {},
            // 事项分类
            problemCategories: [],
            // 投诉原因
            complaintTypes: [],
            // 重要程度
            importances: [],
            organizations : [],
            // 部门
            departments: [],
            orgquestions : [],
			questions:[],
            _$el: null, // 自己的 el $对象
            _$dataTable: null, // datatable $对象
            // 用于回显页码
            pageNumber: 1
        },
        methods: {
            // 返回
            cancel: function(){
                window.history.go(-1);
            },
            addActiveClass: function (type) {
            	$('#setting').addClass('active');
            	$('#timeLimit').addClass('active');},
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },   // 部门
            // 查询门店
            queryOrganization: function () {
                var self = this;
                self.$http.get('/api/org/findAll').then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.organizations = res.data;
                    }
                });
            },// 门店部门
            findOrganizations: function(){
            	var self = this;
            	var url = "/api/org/findDepartment/" + self.form.companyId;
            	// 清空选中的
            	if(self.form.companyId){
            		self.$http.get(url).then(function (res) {
            			if (res.data.code == 1) {
            				self.departments = res.data.data;
            			}
            		});
            	}
            }, 
            createBtnClickHandler:function(){
         	    var _$modal = $('#myModal').clone();
	            var $modal = _$modal.modal({
	                height: 436,
	                width: 598,
	                backdrop: 'static',
	                keyboard: false
	            });
	         		// 获取 node
	         		// var el = $modal.get(0);
	         		// 获取 node
	              	new Vue({
	          		  el: $modal[0],
	          		// $modal: $el, //模式窗体 jQuery 对象
	          		  data: {
	          			  organizations : [],
	          			  departments: [],
	          			  orgquestions : [],
	          			  orgquestions:[],
	          			  questions:[]
	          		  },
	              ready: function () {
	            	  this.queryOrganization();
	              },
	              methods: {
	            	  saveTimeLimit:function(){
	            		  var self = this;
	            		  self.$validate(true, function () {
	                          if (self.$validation.valid) {
	                        	var formData = _.cloneDeep(self.form);
	                        	Vue.http.post('/damei/timeLimit/save', formData,{headers: {},emulateJSON: true}).then(function(response) {
	                        		var responseText = response.data;
	                        		if(responseText['code'] == '1'){  //操作成功，刷新列表
	                        			self.$toastr.success(responseText['message']);
	                        			$modal.modal('hide');
	                        			orderList.$dataTable.bootstrapTable('refresh');
	                        		}else{
	                        			self.$toastr.error(responseText['message']);
	                        		}
	                        	    }, function(response) {
	                        	        console.log(response)
	                        	    });
	                          }
	                      });
	            	  },
	            	  queryOrganization: function () {
	                      var self = this;
	                      self.$http.get('/api/org/findAll').then(function (response) {
	                          var res = response.data;
	                          if (res.code == '1') {
	                              self.organizations = res.data;
	                          }
	                      });
	                  },// 门店部门
	                  findOrganizations: function(){
	                  	var self = this;
	                  	var url = "/api/org/findDepartment/" + self.form.companyId;
	                  	if(self.form.companyId){
	                  		self.$http.get(url).then(function (res) {
	                  			if (res.data.code == 1) {
	                  				self.departments = res.data.data;
	                  			}
	                  		});
	                  	}
	                  },
	                  // 查询部门问题
	                  findOrgQuestion: function(){
	                  	var self = this;
	                  	var url = "/api/question/findOrgQuestion/" + self.form.departmentId;
	                  	// 清空选中的
	                  	if(self.form.companyId){
	                  		self.$http.get(url).then(function (res) {
	                  			if (res.data.code == 1) {
	                  				self.orgquestions = res.data.data;
	                  			}
	                  		});
	                  	}
	                  },
	                  findQuestionType: function(){
	                      var self = this;
	                      var url = '/api/dict/dic/getByType?type=6&parentType='+self.form.questionCategoryId
	                      self.$http.get(url).then(function (response) {
	                          var res = response.data;
	                          if (res.code == '1') {
	                              self.questions = res.data;
	                          }
	                      });
	                    }
	              }
          		}) 
            },
            // 查询部门问题
            findOrgQuestion: function(){
            	var self = this;
            	var url = "/api/question/findOrgQuestion/" + self.form.departmentId;
            	// 清空选中的
            	if(self.form.companyId){
            		self.$http.get(url).then(function (res) {
            			if (res.data.code == 1) {
            				self.orgquestions = res.data.data;
            			}
            		});
            	}
            },
            findQuestionType: function(){
                var self = this;
                var url = '/api/dict/dic/getByType?type=6&parentType='+self.form.questionCategoryId;
                self.$http.get(url).then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.questions = res.data;
                    }
                });
              },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/damei/timeLimit/list',
                    method: 'get',
                    dataType: 'json',
                    cache: false, // 去缓存
                    pagination: true, // 是否分页
                    sidePagination: 'server', // 服务端分页
                    pageNumber: self.pageNumber,
                    queryParams: function (params) {
                        // 将table 参数与搜索表单参数合并
                        return _.extend({}, params, self.form);
                    },
                    mobileResponsive: true,
                    undefinedText: '-', // 空数据的默认显示字符
                    striped: true, // 斑马线
                    maintainSelected: true, // 维护checkbox选项
                    sortName: 'company_id', // 默认排序列名
                    sortOrder: 'desc', // 默认排序方式
                    columns: [{
                        field: 'id',
                        title: 'id',
                        align: 'center',
                        visible: false,
                        order: 'desc'
                    },{
                        field: 'orgName',
                        title: '门店',
                        align: 'center',
                        sortable: true,
                        visible: true,
                        order: 'desc'
                    }, {
                        field: 'departmentName',
                        title: '责任部门',
                        align: 'center',
                        sortable: true,
                        visible: true
                    }, {
                        field: 'uestionCategory',
                        title: "事项分类",
                        align: 'center',
                        sortable: true,
                        visible: true
                    }, {
                        field: 'questionType',
                        title: "问题类型",
                        align: 'center',
                        sortable: true,
                        visible: true
                    }, {
                        field: 'durationHour',
                        sortable: true,
                        title: "最长时间(小时)",
                        align: 'center'
                    },{
                        field: '', // 将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        visible: self.form.status != 'URGE',
                        formatter: function (value, row, index) {
                        	 var html = '<button style="margin-left:10px;" data-handle="edit" data-id="' + row.id + '"  class="m-r-xs btn btn-xs btn-primary" type="button">编辑</button>' ;
                        	 html += '<button style="margin-left:10px;" data-handle="del" data-id="' + row.id + '"  class="m-r-xs btn btn-xs btn-primary" type="button">删除</button>' ;
                             return html;
                        }
                    }]
                });
                // 详情
                self.$dataTable.on('click', '[data-handle="edit"]', function (e) {
                    var id = $(this).data('id');
             	    var _$modal = $('#myModal').clone();
    	            var $modal = _$modal.modal({
    	                height: 436,
    	                width: 598,
    	                backdrop: 'static',
    	                keyboard: false
    	            });
    	         		// 获取 node
    	         		// var el = $modal.get(0);
    	         		// 获取 node
    	            var editView = new Vue({
    	          		  el: $modal[0],
    	          		// $modal: $el, //模式窗体 jQuery 对象
    	          		  data: {
    	          			  edit:true,
    	          			  organizations : [],
    	          			  departments: [],
    	          			  orgquestions : [],
    	          			  orgquestions:[],
    	          			  questions:[],
    	          			  form: {  //绑定FROM域
    	          				questionCategoryId:"",
    	          				duration:"",
    	          				id:""
    	          			  }
    	          		  },
    	              ready: function () {
    	            	  this.queryOrganization();
    	            	  this.setValues(id);
    	              },
    	              methods: {
    	            	  setValues:function(id){
    	            		var self = this;
	            			Vue.http.post('/damei/timeLimit/info', {id:id},{headers: {},emulateJSON: true}).then(function(response) {
                        		var responseText = response.data;
                        		var rdata = responseText.data;
                        		self.form.companyId = rdata.info.companyId;  //门店
                        		self.departments = rdata.department;  // 部门下拉
                        		self.form.departmentId = rdata.info.departmentId; //部门下拉选中
                        		self.orgquestions = rdata.questionCategory;  //问题类型下拉
                        		self.form.questionCategoryId = rdata.info.questionCategoryId;//问题类型下拉选中
                        		self.questions = rdata.questionType;//问题分类下拉
                        		self.form.questionTypeId = rdata.info.questionTypeId;
                        		self.form.duration = rdata.info.duration;
                        		self.form.id = id;
                        	    }, function(response) {
                        	        console.log(response)
                        	    });
    	            	  },
    	            	  saveTimeLimit:function(){
    	            		  var self = this;
    	            		  self.$validate(true, function () {
    	                          if (self.$validation.valid) {
    	                        	var formData = _.cloneDeep(self.form);
    	                        	Vue.http.post('/damei/timeLimit/save', formData,{headers: {},emulateJSON: true}).then(function(response) {
    	                        		var responseText = response.data;
    	                        		 self.$toastr.success('添加成功');
    	                        		 $modal.modal('hide');
    	                        		 orderList.$dataTable.bootstrapTable('refresh');
    	                        	    }, function(response) {
    	                        	        console.log(response)
    	                        	    });
    	                          }
    	                      });
    	            	  },
    	            	  queryOrganization: function () {
    	                      var self = this;
    	                      self.$http.get('/api/org/findAll').then(function (response) {
    	                          var res = response.data;
    	                          if (res.code == '1') {
    	                              self.organizations = res.data;
    	                          }
    	                      });
    	                  },// 门店部门
    	                  findOrganizations: function(){
    	                  	var self = this;
    	                  	var url = "/api/org/findDepartment/" + self.form.companyId;
    	                  	if(self.form.companyId){
    	                  		self.$http.get(url).then(function (res) {
    	                  			if (res.data.code == 1) {
    	                  				self.departments = res.data.data;
    	                  			}
    	                  		});
    	                  	}
    	                  },
    	                  // 查询部门问题
    	                  findOrgQuestion: function(){
    	                  	var self = this;
    	                  	var url = "/api/question/findOrgQuestion/" + self.form.departmentId;
    	                  	// 清空选中的
    	                  	if(self.form.companyId){
    	                  		self.$http.get(url).then(function (res) {
    	                  			if (res.data.code == 1) {
    	                  				self.orgquestions = res.data.data;
    	                  			}
    	                  		});
    	                  	}
    	                  },
    	                  findQuestionType: function(){
    	                      var self = this;
    	                      var url = '/api/dict/dic/getByType?type=6&parentType='+self.form.questionCategoryId
    	                      self.$http.get(url).then(function (response) {
    	                          var res = response.data;
    	                          if (res.code == '1') {
    	                              self.questions = res.data;
    	                          }
    	                      });
    	                    }
    	              }
              		}) 
                });
                // 详情
                self.$dataTable.on('click', '[data-handle="del"]', function (e) {
                	 var selfm = this;
                	 var id = $(this).data('id');
                	 Vue.http.post('/damei/timeLimit/del', {id:id},{headers: {},emulateJSON: true}).then(function(response) {
                		orderList.$toastr.success('删除成功');
                 		orderList.$dataTable.bootstrapTable('refresh');
                 	    }, function(response) {
                 	        console.log(response)
                 	    });
                });
            }
        },
        created: function () {
        	this.queryOrganization();// 初始化部门
        },
        ready: function () {
            this.addActiveClass(this.form.status);
            // 画表
            this.drawTable();
        },
        watch: {},
        beforeDestroy: function () {
        }
    });
// 列表操作备注
})(DameiUtils,moment);

