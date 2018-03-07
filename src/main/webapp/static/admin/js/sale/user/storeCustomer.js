//门店客户库
var vueIndex = null;
+(function (DameiUtils) {
    $('#customerMenu').addClass('active');
    $('#storeCustomer').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
        	// 面包屑
            breadcrumbs: [{
                path: '/',
                name: '主页'
            }, {
                path: '',
                name: '客户管理'
            }, {
                path: '/',
                name: '门店客户库',
                active: true // 激活面包屑的
            }],
            $dataTable: null,
            fUser: null,
            form: {
                keyword: ''
            },
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/customer/storeList',
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
                        field: '',
                        title: '编号',
                        align: 'center',
                        formatter: function (value,row,index) {
                            return ++index;
                        }
                    }, {
                        field: 'customerName',
                        title: '客户姓名',
                        align: 'center'
                    }, {
                        field: 'customerMobile',
                        title: '客户电话',
                        align: 'center'
                    }, {
                        field: 'customerAddress',
                        title: '地址',
                        align: 'center'
                    }, {
                        field: 'amount',
                        title: '工单数',
                        align: 'center',
                        formatter:function(value,row){
                        	if(!value){
                        		return "0";
                        	}else{
                        		return '<a href="javascript:void(0)"  data-handle="show-workOrderList" data-id="' + row.id + '" >' + value + '</a>';
                        	}
                        }
                    },{
                        field: '',
                        title:'操作',
                        align: 'center',
                        width: '5%',
                        formatter: function(value,row){
                            if(!row.blackFlag && DameiUtils.hasPermission('customer:menu-blackBtn')) {
                                return '<button data-handle="black-customer" style="margin-left:15px;" ' +
                                    'class="btn btn-xs btn-danger" data-id="' + row.id + '" type="button">无需回访</button>';
                            }
                        }
                    }]
                });
                
                self.$dataTable.on('click','[data-handle="show-workOrderList"]',function(e){
                	//点击工单数  显示该客户下的所有工单列表
                	findWorkOrderByCustomerId($(this).data().id);
                	e.stopPropagation();
                });

                //加入黑名单 添加黑名单标记
                self.$dataTable.on('click','[data-handle="black-customer"]',function(e){
                    var id = $(this).data('id');
                    swal({
                            title: '添加黑名单标记',
                            text: '是否给该客户添加黑名单标记?',
                            type: 'info',
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            showCancelButton: true,
                            showConfirmButton: true,
                            showLoaderOnConfirm: true,
                            confirmButtonColor: '#ed5565',
                            closeOnConfirm: false
                        },
                        function () {
                            var data = {
                                'id': id,
                                'blackFlag': true
                            };
                            self.$http.post('/api/customer/update', data, {
                                emulateJSON: true
                            }).then(function (res) {
                                if (res.data.code == 1) {
                                    Vue.toastr.success("操作成功");
                                    self.$dataTable.bootstrapTable('refresh');
                                } else {
                                    Vue.toastr.error(res.data.message);
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
            	this.$dataTable.bootstrapTable('selectPage',1);
                this.$dataTable.bootstrapTable('refresh');
            },
            //点击工单数  显示该客户下的所有工单列表
            findWorkOrderByCustomerId : function(){
            	findWorkOrderByCustomerId();
            }
        },
        created: function () {
            this.fUser = window.DameiUser;
        },
        ready: function () {
            this.drawTable();
        }
    });

  //工单列表
    function findWorkOrderByCustomerId(customerId) {
       var arr = [{
		           field: 'workOrderCode',
		           title: '工单编号',
		           align: 'center'
		       }, {
		           field: 'customerName',
		           title: '客户姓名',
		           align: 'center',
		           formatter: function(value,row){
		        	   vueModal2.customerName = value;
		        	   return '<a href="/workorder/workOrderInfo?workOrderId='+ row.id 
               				+'&firstId=customerMenu&secondId=storeCustomer" >' + value + '</a>';
		           }
		       }, {
		           field: 'customerMobile',
		           title: '客户电话',
		           align: 'center',
		           formatter: function(value){
		        	   vueModal2.customerMobile = value;
		        	   return value;
		           }
		       }, {
		           field: 'customerAddress',
		           title: '地址',
		           align: 'center',
		           formatter: function(value){
		        	   vueModal2.customerAddress = value;
		           		return value;
		           }
		       }, {
		           field: 'problem',
		           title:'问题描述',
		           align: 'center',
		           formatter: function(value){
			           	if(value.length > 10){
			           		return value.substring(0,10) + "...";
			           	}else{
			           		return value;
			           	}
		           }
		       },{
		           field: 'questionType1',
		           title:'事项分类',
		           align: 'center',
		           formatter: function(value){
			           	if(value){
			           		return value.name;
			           	}else{
			           		return "";
			           	}
		           }
		       }, {
		           field: 'complaintType',
		           title:'投诉原因',
		           align: 'center',
		           formatter: function(value){
			           	if(value){
			           		return value.name;
			           	}else{
			           		return "";
			           	}
		           }
		       }, { 
		           field: 'importantDegree1',
		           title:'重要程度',
		           align: 'center',
		           formatter: function(value){
			           	if(value){
			           		return value.name;
			           	}else{
			           		return "";
			           	}
		           }
		       }, {
		           field: 'operationDateFromRmk',
		           title:'完成时间',
		           align: 'center'
		       }];
      /*var getUrl = '/api/employee/listCollection?store=' + DameiUser.storeCode
      				+ '&dataSource=' + vueIndex.form.dataSource;*/
      var getUrl = '/damei/workorder/findWorkOrdersByCustomerId';
      var $modal = $('#modalBrand').clone();
      $modal.modal({
    	width: 1000, 
        height: 350,
        maxHeight: 400
      });
      var vueModal2 = null
      $modal.on('shown.bs.modal',
        function () {
          vueModal2 = new Vue({
            el: $modal.get(0),
            mixins: [DameiVueMixins.DataTableMixin],
            data: {
              $dataTable: null,
              modalModel: null,
              //模式窗体模型
              _$el: null,
              //自己的 el $对象
              customerName: '',
              customerMobile: '',
              customerAddress: ''
              
            },
            created: function () {},
            ready: function () {
            	this.drawTable();
            },
            methods: {
              drawTable: function () {
                var self = this;
                self.$dataTable = $('#dataTableBrand', self._$el).bootstrapTable({
                  url: getUrl,
                  method: 'get',
                  dataType: 'json',
                  //去缓存
                  cache: false,
                  //是否分页
                  pagination: false,
                  //服务端分页
                 // sidePagination: 'server',
                  singleSelect: true,
                 //pageSize:5,
                  queryParams: function (params) {
                	  // 将table 参数与搜索表单参数合并
                	  return _.extend({'customerId': customerId},params, self.form);
                  },
                  mobileResponsive: true,
                  undefinedText: '-',
                  //空数据的默认显示字符
                  striped: true,
                  //斑马线
                  maintainSelected: true,
                  //维护checkbox选项
                  sortOrder: 'desc',
                  //默认排序方式
                  columns: arr
                });
              },
              //发起工单-去发起工单页面,
              addWorkOrder: function(){
            	  var self = this;
            	  window.location.href = "/workorder/add?customerName=" + self.customerName
            	  						+ "&customerMobile=" + self.customerMobile
            	  						+ "&customerAddress=" + self.customerAddress;
              }
              
            }
          });
        });
    }
    
})
(this.DameiUtils);