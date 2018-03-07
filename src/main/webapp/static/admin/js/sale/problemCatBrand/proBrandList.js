var vueIndex = null;
+(function (DameiUtils) {
    $('#setting').addClass('active');
    $('#proCatBrandMenu').addClass('active');
    vueIndex = new Vue({
        el: '#container',
        data: {
            $dataTable: null,
            form: {
            	keyword: '',
            	questionType1: ''
            },
            dictList: {},
        },
        methods: {
            auto: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
            },
            drawTable: function () {
                var self = this;
                self.$dataTable = $(this.$els.dataTable).bootstrapTable({
                    url: '/api/dict/problemCatbrand/list',
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
                    sortName: 'id', //默认排序列名
                    sortOrder: 'desc', //默认排序方式
                    columns: [{
                        field: 'questionType1',
                        title: '事项分类',
                        align: 'center',
                    	formatter: function(value){
                    		if(value){
                    			return value.name;
                    		}
                    		return "";
                    	}
                    }, {
                        field: 'brand',
                        title: '品牌',
                        align: 'center',
                        formatter: function(value){
                        	if(value){
                        		return value.name;
                        	}
                        	return "";
                        }
                    },{
                        field: 'createTime',
                        title: '创建时间',
                        align: 'center'
                    }, {
                        field: 'id',
                        title: '主键',
                        align: 'center',
                        visible: false
                    }, {
                        field: '', //将id作为排序时会和将id作为操作field内容冲突，导致无法排序
                        title: "操作",
                        align: 'center',
                        formatter: function (value, row, index) {
                            var fragment = '';
                            //if (DameiUtils.hasPermission('admin:role:delete'))
                            fragment += ('<button data-handle="operate-delete" data-id="' + row.id + '" type="button" class="m-r-xs btn btn-xs btn-danger">删除</button>');
                            return fragment;
                        }
                    }]
                });
                self.$dataTable.on('click', '[data-handle="operate-delete"]', function (e) {
                    var id = $(this).data('id');
                    swal({
                        title: '删除品牌事项分类',
                        text: '确定删除？',
                        type: 'info',
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        showCancelButton: true,
                        showConfirmButton: true,
                        showLoaderOnConfirm: true,
                        confirmButtonColor: '#ed5565',
                        closeOnConfirm: false
                    }, function () {
                        self.$http.get('/api/dict/problemCatbrand/delete?id=' + id).then(function (res) {
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
            // 查询数据字典--事项分类
            queryDictList: function () {
                var self = this;
                self.$http.get('/api/dict/dic/getByType?type=5').then(function (response) {
                    var res = response.data;
                    if (res.code == '1') {
                        self.dictList = res.data;
                    }
                });
            },
            query: function () {
                this.$dataTable.bootstrapTable('selectPage', 1);
                this.$dataTable.bootstrapTable('refresh');
            },
            createBtnClickHandler: function (e) {
                this.showModel();
            },
            showModel: function () {
                var _$modal = $('#modal').clone();
                var $modal = _$modal.modal({
                    height: 180,
                    maxHeight: 260,
                    backdrop: 'static',
                    keyboard: false
                });
                modal($modal);
            }
        },
        created: function () {
            this.fUser = window.DameiUser;
        },
        ready: function () {
            this.drawTable();
            this.queryDictList();
        }
    });
    // 新建弹窗
    function modal($el) {
        // 获取 node
        var el = $el.get(0);
        // 创建 Vue 对象编译节点
        var vueModal = new Vue({
            el: el,
            // 模式窗体必须引用 ModalMixin
            mixins: [DameiVueMixins.ModalMixin],
            $modal: $el, //模式窗体 jQuery 对象
            data: {
                //控制 按钮是否可点击
                disabled: false,
                //模型复制给对应的key
                problemCatbrand: {
                	questionType1Id: '',
                    brandId: '',
                },
                submitBtnClick: false,
                dictList: null,
                brandList: null
            },
            created: function () {
            },
            ready: function () {
            	this.dictList = vueIndex.dictList;
            	this.queryBrandList();
            },
            methods: {
                submit: function () {
                    var self = this;
                    self.submitBtnClick = true;
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.disabled = true;
                            var data = {
                            	'questionType1.id': self.problemCatbrand.questionType1Id,
                            	'brand.id': self.problemCatbrand.brandId
                            };
                            self.$http.post('/api/dict/problemCatbrand/add', data,
                            		{emulateJSON: true}).then(function (res) {
                                if (res.data.code == 1) {
                                    $el.on('hidden.bs.modal', function () {
                                        vueIndex.$dataTable.bootstrapTable('refresh');
                                        self.$toastr.success('操作成功');
                                    });
                                    $el.modal('hide');
                                }
                            }).finally(function () {

                            });
                        }
                    });

                },
                queryBrandList: function(){
                	var self = this;
                    self.$http.get('/api/dict/brand/findAll').then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.brandList = res.data;
                        }
                    });
                }
            }
        });
        // 创建的Vue对象应该被返回
        return vueModal;
    }


})
(this.DameiUtils);