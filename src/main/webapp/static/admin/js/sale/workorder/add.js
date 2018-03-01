var order;
var orderList;
var operation;
var vueModal11;
+(function (RocoUtils) { 
    $('#workOrderAddMenu').addClass('active');

    Vue.validator('telphone', function (tel) {
        return /^1[3|4|5|7|8]\d{9}$/.test(tel);
    });

    orderList = new Vue({
        el: '#container',
        mixins: [RocoVueMixins.DataTableMixin],
        data: {

            form: {
                keyword: '',
                queryLiableType1: '',
                status: '',
                statusFlag: '',
                manage: '',
                liableName: '',
                srcName: '',
                srcId: '',
                receptionStartTime: '',
                receptionEndTime: '',
                liableCompany: '',
                contractCompany: '',
                liableId: '',
                copyFlag: '',
                userId: '',
                quickCreateOrder: '',
                brand: '0',
                personName: '',
                compName: ''
            },
            showOrgTree: false, // 是否显示机构树
            showSrcOrgTree: false, // 是否显示机构树
            orgData: null, // 机构树数据
            companies: null,
            srcOrgData: null,
            queryLiableTypes: null,
            selectedRows: {}, //选中列
            modalModel: null, //模式窗体模型
            _$el: null, //自己的 el $对象
            _$dataTable: null, //datatable $对象
        },
        methods: {
            activeDatePicker: function () {
                var self = this;
                $(this.$els.receptionStartTime).datetimepicker({
                    format: 'yyyy-mm-dd',
                    minView: 2,
                    autoSize: true
                });
                $(this.$els.receptionEndTime).datetimepicker({
                    format: 'yyyy-mm-dd',
                    minView: 2,
                    autoSize: true
                });
            },
            exportWorkOrder: function () {
                var self = this;
                var keyword = self.form.keyword;
                var queryLiableType1 = self.form.queryLiableType1;
                var status = self.form.status;
                var liableId = self.form.liableId;
                var manage = self.form.manage;
                var srcId = self.form.srcId;
                var receptionStartTime = self.form.receptionStartTime;
                var receptionEndTime = self.form.receptionEndTime;
                var personName = self.form.personName;
                window.open('/mdni/workorder/export?keyword=' + keyword + '&queryLiableType1=' +
                    queryLiableType1 + '&status=' + status + '&liableId=' + liableId + '&srcId=' + srcId + '&receptionStartTime=' +
                    receptionStartTime + '&receptionEndTime=' + receptionEndTime + '&personName=' + personName + '&manage=' + manage);
            },
            fetchCompany: function () {
                var self = this;


            },
            queryImportantDegree: function () {//获取责任类别1
                var self = this;
                self.$http.get('/api/dict/dic/getByType', {params: {'type': 3}}).then(function (res) {
                    if (res.data.code == 1) {
                        self.queryLiableTypes = res.data.data;
                    }
                }).catch(function () {
                }).finally(function () {
                });
            },
            query: function () {
                var self = this;
                if (self.submitting) {
                    return false;
                }
                self.$validate(true, function () {
                    if (self.$validation.valid) {
                        this.$dataTable.bootstrapTable('selectPage', 1);
                        this.$dataTable.bootstrapTable('refresh', {
                            pageNumber: 1
                        })
                    }
                });

            },
            createWorkOrder: function (e) { //创建工单
                this.showModel();
            },

            // 获取机构选择树的数据
            fetchOrgTree: function (id, type) {
                var self = this;
                this.$http.get('/api/org/fetchDeptById/' + id).then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                        self.orgData = res.data;
                    }
                })
            },
            fetchOrgTree2: function (id, type) {
                var self = this;
                this.$http.get('/api/org/fetchDeptById/' + id).then(function (response) {
                    var res = response.data;
                    if (res.code == 1) {
                        self.srcOrgData = res.data;
                    }
                })
            },
            // 勾选机构数外部时，隐藏窗口
            clickOut: function () {
                this.showOrgTree = false;

            },
            clickOut2: function () {
                this.showSrcOrgTree = false;
            },
            // 选择机构时回调事件
            selectOrg: function (node) {
                var self = this;
                self.form.liableName = node.name;
                self.form.liableId = node.id;
            },
            // 选择机构时回调事件
            selectSrcOrg: function (node) {
                var self = this;
                self.form.srcName = node.name;
                self.form.srcId = node.id;
            },
            showModel: function () { //创建工单页面
                setTimeShowModel();
            }
        },
        created: function () {
            var self = this;
            // self.fetchOrgTree();
            // self.fetchOrgTree2();
            self.fetchCompany();

            this.showModel();
        },
        ready: function () {
            this.queryImportantDegree();
            if (this.form.quickCreateOrder == 1) {//首页快速发起工单标识
                setTimeShowModel();
            }
            this.$nextTick(function () {
                this.activeDatePicker();
            });


        },
        watch: {
            "form.liableCompany": function (val, oldVal) {
                var self = this;
//                    var liableId = self.liableType1s[val].id;
                if (val) {
                    self.fetchOrgTree(val, 4);
                    self.fetchOrgTree2(val, 4);
                } else {
                    self.orgData = null;
                    self.liableDepartmentName = '';
                }

            },
            "form.liableDepartment": function (val) {

            }
        },
        beforeDestroy: function () {
        }
    });

    /**
     * 延时显示合同信息列表
     * @returns
     */
    function setTimeShowModel() {
        modal();
        setTimeout(function () {
            showMdniOrder();
        }, 200);
    }

    // 创建工单 ******************大页面对象 order ***************************************
    function modal(rowData, id, isEdit) {
        // 获取 node
        // 创建 Vue 对象编译节点
        order = new Vue({
            el: '#container',
            components: {
                'web-uploader': RocoVueComponents.WebUploaderComponent
            },
            // 模式窗体必须引用 ModalMixin
            mixins: [RocoVueMixins.ModalMixin],
            data: {
                user: _.extend({}, window.RocoUser),
                links: [],
                showOrgTree: false, // 是否显示机构树
                showSrcOrgTree: false,
                orgData: null, // 机构树数据
                //控制 按钮是否可点击
                disabled: false,
                //模型复制给对应的key
//                user: model,
//                submitBtnClick: true,
                importantDegree1s: null,
                liableType1s: null,
                liableType2s: null,
                questionType1s: null,
                questionType2s: null,
                companies: null,
                //事项分类对应的 品牌集合
                problemCatBrands: null,
                liableDepartmentName: null,
                //工单来源
                sourceList: [],
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
                form: {
                    orderId: rowData && rowData.mdniOrder && rowData.mdniOrder.orderId || null,
                    orderNo: rowData && rowData.mdniOrder && rowData.mdniOrder.orderNo || null,
                    customerId: rowData && rowData.customerId || null,
                    customerName: rowData && rowData.customerName || null,
                    customerMobile: rowData && rowData.customerMobile || null,
                    customerAddress: rowData && rowData.customerAddress || null,
                    contractStartTime: rowData && rowData.contractStartTime || null,
                    contractCompleteTime: rowData && rowData.contractCompleteTime || null,
                    //实际开工时间
                    actualStartTime: rowData && rowData.actualStartTime || null,
                    //实际竣工时间
                    actualCompletionTime: rowData && rowData.actualCompletionTime || null,
                    paymentStage: rowData && rowData.paymentStage || null,
                    styListName: rowData && rowData.styListName || null,
                    styListMobile: rowData && rowData.stylistMobile || null,
                    contractorName: rowData && rowData.contractorName || null,
                    contractorMobile: rowData && rowData.contractorMobile || null,
                    //superVisorName 大写???
                    supervisorName: rowData && rowData.superVisorName || null,
                    supervisorMobile: rowData && rowData.supervisorMobile || null,
                    importantDegree1: rowData && rowData.importantDegree1.id || null,
                    receptionPerson: rowData && rowData.receptionPerson.id || null,
                    receptionPersonName: rowData && rowData.receptionPerson.name || null,
                    srcDepartment: rowData && rowData.srcDepartment.id || null,
                    srcDepartmentCode: rowData && rowData.srcDepartment.orgCode || null,
                    srcDepartmentName: rowData && rowData.srcDepartment.orgName || window.RocoUser.departmentName,
                    srcCompany: rowData && rowData.srcCompany.id || null,
                    srcCompanyCode: rowData && rowData.srcCompany.orgCode || null,
                    // liablePerson: rowData && rowData.liablePerson.id || null,
                    // liablePersonName: rowData && rowData.liablePerson.name || null,
                    liableDepartment: rowData && rowData.liableDepartment.id || null,
                    liableCompany: rowData && rowData.liableCompany || '',
                    contractCompany: rowData && rowData.contractCompany || '',
                    brand: rowData && rowData.brand || '0',
                    liableDepartmentName: rowData && rowData.liableDepartment.orgName || null,
                    liableType1: rowData && rowData.liableType1.id || '',
                    liableType2: rowData && rowData.liableType2.id || null,
                    questionType1: rowData && rowData.questionType1.id || '',
                    questionType2: rowData && rowData.questionType2.id || '',
                    workType: '',
                    source: rowData && rowData.source.id || '',
                    problem: rowData && rowData.problem || null,
                    customerFeedbackTime: rowData && rowData.customerFeedbackTime || null,
                    photo: rowData && rowData.photo || '',
                    compName: '',
                },
                //是否已经选择了合同信息
                liabled: false
            },
            methods: {

                activeDatePicker: function () {
                    $(this.$els.customerFeedbackTime).datetimepicker({
                        format: 'yyyy-mm-dd hh:00:00',
                        minView: 'day',
                        autoSize: true
                    });
                },
                showOrderBtnClickHandler: function (e) { //显示合同信息单击事件
                    showMdniOrder();
                },
                showCustomerBtnClickHandler: function (e) { //显示合同信息单击事件
                    showCustomers();
                },
                showliablePersonTable: function (type) { //选择责任人
                    showliablePersonTable(type);
                },
                importantDegree: function (type) {//获取重要程度1
                    var self = this;
                    self.$http.get('/api/dict/dic/getByType', {params: {'type': type}}).then(function (res) {
                        if (res.data.code == 1) {
                            if (type == 7) {
                                self.importantDegree1s = res.data.data;
                                if (self.form.importantDegree1 == null) {
                                    self.form.importantDegree1 = self.importantDegree1s[0].id;
                                }
                            }
                            else if (type == 8) {
                                self.liableType1s = res.data.data;
                                // if (self.form.liableType1 == null) {
                                //     self.form.liableType1 = self.liableType1s[0].id;
                                // }
                            }
                        }
                    }).catch(function () {

                    }).finally(function () {

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
                        if (res.data.code == 1) {
                            self.questionType1s = res.data.data;
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
                // 获取机构选择树的数据
                fetchOrgTree: function (id, type) {
                    var self = this;
                    this.$http.get('/api/org/fetchDeptById/' + id).then(function (response) {
                        var res = response.data;
                        if (res.code == 1) {
                            self.orgData = res.data;
                        }
                    })
                },
                fetchCompany: function () {
                    var self = this;
                    if (self.user.departmentType != 'GROUPCUSTOMERSERVICE') {
                        this.$http.get('/api/org/findByLogginUser/' + self.user.company).then(function (response) {
                            var res = response.data;
                            if (res.code == 1) {
                                self.form.compName = res.data.orgName;
                                self.form.liableCompany = res.data.id;
                            }else{
                            	self.$toastr.error(res.message);
                            }
                        })
                    } else {
                        this.$http.get('/api/org/findAll').then(function (response) {
                            var res = response.data;
                            if (res.code == 1) {
                                self.companies = res.data;
                            }else{
                            	self.$toastr.error(res.message);
                            }
                        })
                    }
                },
                //通过选择的事项分类,加载下面的品牌
                fetchBrand: function () {
                    var self = this;
                    if(self.form.questionType1 == ''){
                    	return ;
                    }
                    this.$http.get('/api/dict/problemCatbrand/findBrandsByQuestionId?questionType1Id=' 
                    		+ self.form.questionType1).then(function (response) {
                        var res = response.data;
                        if (res.code == 1) {
                            self.problemCatBrands = res.data;
                        }else{
                        	self.$toastr.error(res.message);
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
                            self.submit(1);
                        }
                    });
                },
                consultClickHandler: function () {
                    var self = this;
                    if (self.submitting) {
                        return false;
                    }
                    self.$validate(true, function () {
                        if (self.$validation.valid) {
                            self.submit(0);
                        }
                    });
                },
                //提交工单
                submit: function (val) {
                    var self = this;
                    var baseurl = '/mdni/workorder';

                    self.submitting = true;
                    var formData = _.cloneDeep(self.form);
                    var data = {
                        mdniOrder: {
                            orderId: formData.orderId,
                            orderNo: formData.orderNo,
                        },
                        customerId: formData.customerId,
                        customerName: formData.customerName,
                        customerMobile: formData.customerMobile,

                        customerAddress: formData.customerAddress,
                        contractStartTime: formData.contractStartTime,
                        contractEndTime: formData.contractEndTime,
                        contractCompleteTime: formData.contractCompleteTime,
                        //实际开工时间
                        actualStartTime: formData.actualStartTime,
                        actualCompletionTime: formData.actualCompletionTime,

                        styListName: formData.styListName,
                        styListMobile: formData.styListMobile,
                        contractorName: formData.contractorName,
                        contractorMobile: formData.contractorMobile,
                        supervisorName: formData.supervisorName,
                        supervisorMobile: formData.supervisorMobile,
                        liableCompany: {
                            id: formData.liableCompany
                        },
                        // contractCompany: {
                        //     id: formData.contractCompany
                        // },
                        importantDegree1: {
                            id: formData.importantDegree1
                        },
                        questionType1: {
                            id: formData.questionType1
                        },
                        questionType2: {
                            id: formData.questionType2
                        },
                        receptionPerson: {
                            id: formData.receptionPerson
                        },
                        srcCompany: {
                            id: formData.srcCompany,
                            orgCode: formData.srcCompanyCode
                        },
                        srcDepartment: {
                            id: formData.srcDepartment,
                            orgCode: formData.srcDepartmentCode
                        },
                        liablePerson: {
                            id: formData.liablePerson
                        },
                        liableDepartment: {
                            id: formData.liableDepartment
                        },
                        liableType1: {
                            id: formData.liableType1
                        },
                        liableType2: {
                            id: formData.liableType2
                        },
                        copyFlag: 'N',
                        copyWorkId: id,
                        brand: formData.brand,
                        source: {
                            id: formData.source,
                        },
                        workType: formData.workType,
                        problem: formData.problem,
                        photo: formData.photo,
                        customerFeedbackTime: formData.customerFeedbackTime
                    };
                    if (isEdit) {
                        url = baseurl + '/update/' + id;
                    } else {
                        url = baseurl + '/save/' + val;
                    }
                    self.$http.post(url, data).then(function (res) {
                        if (res.body.code == 1) {
                            // // window.location.href = ctx + "/index";
                            self.$toastr.success('提交成功');
                            window.location.href = "/index";
                            // $modal.modal('hide');

                            orderList.$dataTable.bootstrapTable('refresh');
                        }
                    }).catch(function () {


                    }).finally(function () {
                        //请求返回后dom还没有销毁，任然可以重复提交，所以提交成功也不可再次提交
                        self.submitting = false;
                    });
                },
                deleteFlie: function () {
                    var self = this;
                    self.$http.delete('/api/upload', {
                        params: {
                            path: self.form.photo
                        }
                    }).then(function (response) {
                        var res = response.data;
                        if (res.code == '1') {
                            self.form.photo = '';
                            self.links = [];
                        } else {
                            self.$toastr.error("删除失败");
                        }
                    });
                },
                cancel: function () {
                    window.history.go(-1);
                },
                initFunction: function () {
                    var self = this;
                    self.form.customerName = RocoUtils.parseQueryString()['customerName'] || '';
                    self.form.customerMobile = RocoUtils.parseQueryString()['customerMobile'] || '';
                    self.form.customerAddress = RocoUtils.parseQueryString()['customerAddress'] || '';
                },
                //工单来源
                getSourceList: function () {
                    //工单来源
                    this.$http.get('/api/dict/dic/getByType?type=9').then(function (res) {
                        if (res.data.code == 1) {
                            order.sourceList = res.data.data;
                            /*if (order.sourceList && order.sourceList.length > 0) {
                                order.form.source = order.sourceList[0].id;
                            }*/
                        }
                    });
                }

            },
            events: {
                'webupload-upload-success-sub': function (file, res) {
                    var self = this;
                    if (res.code == '1') {
                        self.$toastr.success('上传成功');
                        self.form.photo += res.data.path + ',';
                    } else {
                        self.$toastr.error(res.message);
                    }
                }
            },
            watch: {
            	//当事项分类值 发生变化时,执行
                "form.questionType1": function (val, oldVal) {
                    var self = this;
//                    var importantId = self.importantDegree1s[val].id;
                    self.getImportantList(val, 6, 'watch');
                    self.fetchBrand();
                  //问题列别变化时,将问题类型清空
                    this.form.questionType2 = '';
                    //清空品牌
                    this.form.brand = '0';
                    this.problemCatBrands = null;
                },
                "form.liableCompany": function (val, oldVal) {
                    var self = this;
//                    var liableId = self.liableType1s[val].id;
                    self.form.liableDepartment = '';
                    self.form.questionType1 = '';
                    self.form.questionType2 = '';
                    self.liableDepartmentName = '请选择责任部门';

                    if (val) {
                        self.fetchOrgTree(val, 4);
                    } else {
                        self.orgData = null;
                        self.liableDepartmentName = '';
                    }
                },
                "form.liableDepartment": function (val) {
                    this.getQuestionTypes(val, 5);
                    //选择部门时,将事项分类/问题类型清空
                    this.form.questionType1 = '';
                    this.form.questionType2 = '';
                    //清空品牌
                    this.form.brand = '0';
                    this.problemCatBrands = null;
                    
                },

                "form.photo": function () {
                    var self = this;
                    self.links = [];
                    if (self.form.photo) {
                        var arry = self.form.photo.split(',');
                        _.each(arry, function (ele) {
                            self.links.push({a: ele});
                        })
                    }
                }
            },
            created: function () {
                var self = this;
                // self.fetchOrgTree();
                self.fetchCompany();
                self.fetchBrand();
                this.importantDegree(7);
                this.importantDegree(8);
                if (this.form.importantDegree1 != null && this.form.importantDegree1 != undefined) {
                    this.getImportantList(this.form.importantDegree1, 2);
                }
                if (this.form.liableType1 != null && this.form.liableType1 != undefined) {
                    this.getImportantList(this.form.liableType1, 4);
                }
                if (this.form.questionType1 != null && this.form.questionType1 != undefined) {
                    this.getImportantList(this.form.questionType1, 6);
                }
            },
            ready: function () {
                this.activeDatePicker();
                this.form.receptionPerson = window.RocoUser.userId;
                this.form.receptionPersonName = window.RocoUser.name;
                this.form.srcDepartment = window.RocoUser.department;
                this.form.srcDepartmentCode = window.RocoUser.departmentCode;
                this.form.srcDepartmentName = window.RocoUser.departmentName;
                this.form.srcCompany = window.RocoUser.company;
                this.form.srcCompanyCode = window.RocoUser.companyCode;
                //获取请求路径上的客户信息,并给相应的赋值
                this.initFunction();
                this.getSourceList();
                var self = this;
                if (self.form.photo) {
                    var arry = self.form.photo.split(',');
                    _.each(arry, function (ele) {
                        self.links.push({a: ele});
                    })
                }

            }
        });
        // 创建的Vue对象应该被返回
        return order;

    }


    function showMdniOrder() {
        var _modal = $('#mdnOrder').clone();
        var $el = _modal.modal({
            height: 800,
            backdrop: 'static',
        });
        $el.on('shown.bs.modal',
            function () {
                var el = $el.get(0);
                vueModal11 = new Vue({
                    el: el,
                    http: {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    },
                    mixins: [RocoVueMixins.ModalMixin],
                    created: function () {
                    },
                    data: {
                        user: _.extend({}, window.RocoUser),
                        selectOrder: [],
                        form: {
                            contractCompany: ''
                        },
                        selectCustomer: [],
                        companies: null
                    },
                    methods: {
                        query: function () {
                            var self = this;
                            if (self.submitting) {
                                return false;
                            }
                            
                            if(RocoUser.departmentType=='GROUPCUSTOMERSERVICE' && (self.form.contractCompany == null || self.form.contractCompany == '')){//集团
                            	self.$toastr.error("请选择门店!");
                            	return false;
                            }
                            if(RocoUser.departmentType!='GROUPCUSTOMERSERVICE' && (self.form.contractCompany == null || self.form.contractCompany == '')){
                            	self.form.contractCompany = RocoUser.company;
                            }
                            self.checkUsed();
                            
//                            if (self.$validation.valid) {
//                                self.checkUsed();
//                                // this.$dataTable.bootstrapTable('selectPage', 1);
//                            }
                        },
//                      query: function () {
//                          this.$dataTable.bootstrapTable('selectPage', 1);
//                          this.$dataTable.bootstrapTable('refresh');
//                      },
                        checkUsed:function () {
                            var self = this;
                            this.$http.get('/mdni/workorder/checkUsed/'+self.form.contractCompany).then(function (response) {
                                var res = response.data;
                                if (res.code == 1) {
                                    // self.companies = res.data;
                                    self.$dataTable.bootstrapTable('refresh', {
                                        pageNumber: 1
                                    })
                                }else{
                                    self.$toastr.error("该功能暂未启用");
                                    // self.companies = res.data;
                                    self.$dataTable.bootstrapTable('refresh', {
                                        pageNumber: 1
                                    })
                                }
                            })
                        },
                        fetchCompany: function () {
                            var self = this;
                            this.$http.get('/api/org/findAll').then(function (response) {
                                var res = response.data;
                                if (res.code == 1) {
                                    self.companies = res.data;
                                }
                            })
                        },
                        drawTable: function () {
                            var self = this;
                            self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                                url: '/mdni/workorder/mdnOrderList',
                                method: 'get',
                                dataType: 'json',
                                cache: false,
                                pagination: true,//不分页
                                sidePagination: 'server',
                                queryParams: function (params) {
                                    return _.extend({}, params, self.form);
                                },
                                mobileResponsive: true,
                                undefinedText: '-',
                                striped: true,
                                maintainSelected: true,
                                sortOrder: 'desc',
                                columns: [{
                                    field: "orderId",
                                    visible: false
                                }, {
                                    checkbox: true,
                                    align: 'center',
                                    width: '5%',
                                    radio: true
                                }, {
                                    field: 'orderNo',
                                    title: '项目编号',
                                    width: '10%',
                                    orderable: false,
                                    align: 'center',
                                }, {
                                    field: 'customerId',
                                    title: '客户ID',
                                    width: '6%',
                                    orderable: false,
                                    visible: false,
                                    align: 'center',
                                }, {
                                    field: 'customerName',
                                    title: '客户姓名',
                                    width: '6%',
                                    orderable: false,
                                    align: 'center',
                                }, {
                                    field: 'mobile',
                                    title: '客户电话',
                                    width: '6%',
                                    orderable: false,
                                    visible: true,
                                    align: 'center',
                                }, {
                                    field: 'address',
                                    title: '地址',
                                    width: '15%',
                                    orderable: false,
                                    visible: true,
                                    align: 'center',
                                }, {
                                    field: 'contractStartTime',
                                    title: '合同开始日期',
                                    width: '10%',
                                    orderable: false,
                                    align: 'center',
                                    formatter: function (val) {
                                        if(val && val.endsWith(".0")){
                                            if(val == '1980-01-01 00:00:00.0') {
                                                return '';
                                            }else{
                                                val = val.substring(0, val.lastIndexOf("."));
                                            }
                                        }
                                        return val;
                                    }
                                }, {
                                    field: 'contractCompleteTime',
                                    title: '合同竣工日期',
                                    width: '10%',
                                    orderable: false,
                                    align: 'center',
                                    formatter: function (val) {
                                        if(val && val.endsWith(".0")){
                                            if(val == '1980-01-01 00:00:00.0') {
                                                return '';
                                            }else{
                                                val = val.substring(0, val.lastIndexOf("."));
                                            }
                                        }
                                        return val;
                                    }
                                }, {
                                    field: 'paymentStage',
                                    title: '财务收款阶段',
                                    width: '10%',
                                    orderable: false,
                                    align: 'center',
                                }, {
                                    field: 'styListName',
                                    title: '设计师',
                                    width: '6%',
                                    orderable: false,
                                    visible: false
                                }, {
                                    field: 'styListMobile',
                                    title: '设计师电话',
                                    width: '6%',
                                    orderable: false,
                                    visible: false
                                }, {
                                    field: 'contractor',
                                    title: '项目经理',
                                    width: '6%',
                                    orderable: false,
                                    visible: false
                                }, {
                                    field: 'contact',
                                    title: '项目经理电话',
                                    width: '6%',
                                    orderable: false,
                                    visible: false
                                }, {
                                    field: 'superVisorName',
                                    title: '监理',
                                    width: '6%',
                                    orderable: false,
                                    visible: false
                                }, {      //superVisorMobile
                                    field: 'superVisoMobile',
                                    title: '监理电话',
                                    width: '6%',
                                    orderable: false,
                                    visible: false
                                }]
                            });
                            self.$dataTable.on('check.bs.table', function (row, data) {
                                self.selectOrder = [];
                                var order = {
                                    orderId: data.orderId,
                                    orderNo: data.orderNo,
                                    customerId: data.customerId,
                                    customerName: data.customerName,
                                    customerMobile: data.mobile,
                                    customerAddress: data.address,
                                    contractStartTime: data.contractStartTime,
                                    contractCompleteTime: data.contractCompleteTime,
                                    //客户收款状态
                                    paymentStage: data.paymentStage,
                                    //实际开工时间
                                    actualStartTime: data.actualStartTime,
                                    //实际竣工时间
                                    actualCompletionTime: data.actualCompletionTime,
                                    styListName: data.styListName,
                                    styListMobile: data.styListMobile,
                                    contractorName: data.contractor,
                                    contractorMobile: data.contact,
                                    supervisorName: data.superVisorName,
                                    supervisorMobile: data.superVisoMobile
                                };
                                self.selectOrder.push(order);
                            });
                        },

                        commitUsers: function () {
                            var self = this;
                            if (self.selectOrder != null && self.selectOrder != undefined && self.selectOrder.length > 0) {
                                order.form.orderId = self.selectOrder[0].orderId;
                                order.form.orderNo = self.selectOrder[0].orderNo;
                                order.form.customerId = self.selectOrder[0].customerId;
                                order.form.customerName = self.selectOrder[0].customerName;
                                order.form.customerMobile = self.selectOrder[0].customerMobile;
                                order.form.customerAddress = self.selectOrder[0].customerAddress;
                                order.form.contractStartTime = self.selectOrder[0].contractStartTime;
                                order.form.contractCompleteTime = self.selectOrder[0].contractCompleteTime;
                                //如果合同开始时间/合同竣工时间是 1980-01-01 00:00:00.0 就显示空串
                                if(order.form.contractStartTime && order.form.contractStartTime.endsWith(".0")){
                                    if(order.form.contractStartTime == '1980-01-01 00:00:00.0'){
                                        order.form.contractStartTime = '';
                                    }else{
                                        order.form.contractStartTime = order.form.contractStartTime.substring
                                                    (0,order.form.contractStartTime.lastIndexOf("."));
                                    }
                                }
                                if(order.form.contractCompleteTime && order.form.contractCompleteTime.endsWith(".0")){
                                    if(order.form.contractCompleteTime == '1980-01-01 00:00:00.0'){
                                        order.form.contractCompleteTime = '';
                                    }else{
                                        order.form.contractCompleteTime = order.form.contractCompleteTime.substring
                                        (0,order.form.contractCompleteTime.lastIndexOf("."));
                                    }
                                }
                                //如果实际开工时间/实际竣工时间是 1980-01-01 00:00:00.0 就显示空串
                                //实际开工时间
                                order.form.actualStartTime = self.selectOrder[0].actualStartTime;
                                //实际竣工时间
                                order.form.actualCompletionTime = self.selectOrder[0].actualCompletionTime;

                                if(order.form.actualStartTime && order.form.actualStartTime.endsWith(".0")){
                                    if(order.form.actualStartTime == '1980-01-01 00:00:00.0'){
                                        order.form.actualStartTime = '';
                                    }else{
                                        order.form.actualStartTime = order.form.actualStartTime.substring(
                                                                            0,order.form.actualCompletionTime.lastIndexOf("."));
                                    }
                                }
                                if(order.form.actualCompletionTime && order.form.actualCompletionTime.endsWith(".0")){
                                    if(order.form.actualCompletionTime == '1980-01-01 00:00:00.0'){
                                        order.form.actualCompletionTime = '';
                                    }else{
                                        order.form.actualCompletionTime = order.form.actualCompletionTime.substring(
                                                                            0,order.form.actualCompletionTime.lastIndexOf("."));
                                    }
                                }
                                order.form.paymentStage = self.selectOrder[0].paymentStage;
                                order.form.styListName = self.selectOrder[0].styListName;
                                order.form.styListMobile = self.selectOrder[0].styListMobile;
                                order.form.contractorName = self.selectOrder[0].contractorName;
                                order.form.contractorMobile = self.selectOrder[0].contractorMobile;
                                order.form.supervisorName = self.selectOrder[0].supervisorName;
                                order.form.supervisorMobile = self.selectOrder[0].supervisorMobile;
                                //将查询时使用的门店 条件,作为门店
                                if (self.form.contractCompany) {
                                    order.form.liableCompany = self.form.contractCompany;
                                    order.liabled = true;
                                }
                            }
                            $el.modal('hide');
                            this.$destroy();
                        }
                    },
                    ready: function () {
                        this.drawTable();
                        this.fetchCompany();
                    }
                });

                // 创建的Vue对象应该被返回
                return vueModal11;
            });

    }

    // 显示本地客户列表
    function showCustomers() {
        var _modal = $('#customerDiv').clone();
        var $el = _modal.modal({
            height: 450,
            backdrop: 'static',
        });
        $el.on('shown.bs.modal',
            function () {
                var el = $el.get(0);
                var vueModal = new Vue({
                    el: el,
                    http: {
                        headers: {
                            'Content-Type': 'application/x-www-form-urlencoded'
                        }
                    },
                    mixins: [RocoVueMixins.ModalMixin],
                    created: function () {
                    },
                    data: {
                        user: _.extend({}, window.RocoUser),
                        form: {
                            cusCompany: RocoUser.company
                        },
                        selectCustomer: [],
                        companies: null
                    },
                    methods: {
                        query: function () {
//                            this.$dataTable.bootstrapTable('selectPage', 1);
                            this.$dataTable.bootstrapTable('refresh');
                        },
                        fetchCompany: function () {
                            var self = this;
                            this.$http.get('/api/org/findAll').then(function (response) {
                                var res = response.data;
                                if (res.code == 1) {
                                    self.companies = res.data;
                                }
                            })
                        },
                        drawTable: function () {
                            var self = this;
                            self.$dataTable = $('#dataTable', self._$el).bootstrapTable({
                                url: '/api/customer/findCustomer',
                                method: 'get',
                                dataType: 'json',
                                cache: false,
                                pagination: true,//不分页
                                sidePagination: 'server',
                                queryParams: function (params) {
                                    return _.extend({}, params, self.form);
                                },
                                mobileResponsive: true,
                                undefinedText: '-',
                                striped: true,
                                maintainSelected: true,
                                sortOrder: 'desc',
                                columns: [{
                                    field: "id",
                                    visible: false
                                }, {
                                    checkbox: true,
                                    align: 'center',
                                    width: '5%',
                                    radio: true
                                }, {
                                    field: 'customerName',
                                    title: '客户姓名',
                                    width: '15%',
                                    orderable: false
                                }, {
                                    field: 'customerMobile',
                                    title: '客户电话',
                                    width: '15%',
                                    orderable: false
                                }, {
                                    field: 'customerAddress',
                                    title: '地址',
                                    width: '65%',
                                    orderable: false,
                                    visible: true
                                }]
                            });
                            self.$dataTable.on('check.bs.table', function (row, data) {
                                self.selectCustomer = [];
                                var customer = {
                                    customerId: data.id,
                                    customerName: data.customerName,
                                    customerMobile: data.customerMobile,
                                    customerAddress: data.customerAddress,
                                };
                                self.selectCustomer.push(customer);
                            });
                        },
                        commitCustomer: function () {
                            var self = this;
                            if (self.selectCustomer != null && self.selectCustomer != undefined && self.selectCustomer.length > 0) {
                                order.form.customerId = self.selectCustomer[0].id;
                                order.form.customerName = self.selectCustomer[0].customerName;
                                order.form.customerMobile = self.selectCustomer[0].customerMobile;
                                order.form.customerAddress = self.selectCustomer[0].customerAddress;
                                //当前门店的查询条件 回显到工单门店中
                                order.form.liableCompany = self.form.cusCompany;
                            }
                            $el.modal('hide');
                            order.liabled = true;
                            this.$destroy();
                        },
                    },
                    ready: function () {
                        this.drawTable();
                        this.fetchCompany();
                    }
                });

                // 创建的Vue对象应该被返回
                return vueModal;
            });

    }

    /**
     * 处理、回复、回访
     * @param id    工单ID
     * @param flag    操作标识
     * @returns
     */

})(RocoUtils);


