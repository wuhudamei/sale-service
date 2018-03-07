// 全局 mixin
Vue.mixin({
    ready: function () {
        // 将 $el jquery 化
        this._$el = $(this.$el);
    }
});

// 列表 Mixin
DameiVueMixins.DataTableMixin = {
    created: function () {
        // 会给你创建一个选中对象
        this.selectedRows = {};
    },
    methods: {
        // idKey 必须保证为不重复的唯一 id值
        checkEventHandle: function (key) {
            var self = this;
            if (!key) {
                key = 'id';
            }
            if (self.$dataTable) {
                // 单选选中
                self.$dataTable.on('check.bs.table', function (e, row, $element) {
                    var id = row[key];
                    self.selectedRows[id] = id;
                    self.selectedRows = _.assign({}, self.selectedRows);
                });

                // 单选取消
                self.$dataTable.on('uncheck.bs.table', function (e, row, $element) {
                    var id = row[key];
                    delete self.selectedRows[id];
                    self.selectedRows = _.assign({}, self.selectedRows);
                });

                // 全选选中
                self.$dataTable.on('check-all.bs.table', function (e, rows) {
                    _.forEach(rows, function (element, index, array) {
                        self.selectedRows[element[key]] = element[key];
                    });
                    self.selectedRows = _.assign({}, self.selectedRows);
                });

                // 全选取消
                self.$dataTable.on('uncheck-all.bs.table', function (e, rows) {
                    _.forEach(rows, function (element, index, array) {
                        delete self.selectedRows[element[key]];
                    });
                    self.selectedRows = _.assign({}, self.selectedRows);
                });
            }
        }
    }
};


// 模式窗体 Mixin
DameiVueMixins.ModalMixin = {
    created: function () {
    },
    ready: function () {
        var self = this;
        var $modal = self.$options.$modal;
        if ($modal) {
            // 隐藏前触发 Vue 对象的销毁
            $modal.on('hide.bs.modal', function (e) {
                if (e.target == self.$el) {
                    self.$destroy();
                    console.info('hide.bs.modal from ModalMixin');
                }
            });
            $modal.on('hidden.bs.modal', function (e) {
                if (e.target == self.$el) {
                    console.info('hidden.bs.modal from ModalMixin');
                }
            })
        }
    }
};

//表单数据回显的 Mixin
DameiVueMixins.FormMixin = {
    data:{
        //初次加载form标记
        firstFormFlag: true,
        //初次加载pageNum标记
        firstPageNumFlag: true,
        //
        bootstrapTableObj: null
    },
    ready : function () {
        //在每次页面初始化时,取出存在 sessionStorage 中的form值,进行回显
        this.loadFormParms();

    },
    watch: {
        form: {
            handler: function (val, oldval) {
                var self = this;
                console.log( self.$dataTable + '/////');
                //将from表单中变化了的数据 存储到 sessionStorage 中;
                if(!self.firstFormFlag && val){
                    //alert(JSON.stringify(val));
                    for(obj in val){
                        if(val[obj]){
                            //有值就存储
                            //alert(obj + "..." + val[obj]);
                            sessionStorage.setItem(obj, val[obj]);
                        }else{
                            //没有就将其 删除下--去掉上次存储的数据
                            sessionStorage.removeItem(obj);
                        }
                    }
                }
                self.firstFormFlag = false;
            },
            deep: true//对象内部的属性监听，也叫深度监听
        },
        //监控表格 options 对象
        /*bootstrapTableObj: {
            handler: function (val, oldval) {
                if(!oldval){
                    return ;
                }
                if(oldval){
                    alert(val == oldval)
                }
                //获取当前页码
                var self = this;
                console.log(val)
                if(!self.firstPageNumFlag){

                }
                self.firstPageNumFlag = false;
            },
            deep: true//对象内部的属性监听，也叫深度监听
        }*/
    },
    methods: {
        //加载并回显form数据
        loadFormParms: function () {
            var self = this;
            if(self.form){
                var count = 0;
                for(obj in self.form){
                    //alert(obj + "..." + self.form[obj]);
                    var item = sessionStorage.getItem(obj);
                    if(item){
                        self.form[obj] = item;
                        count ++ ;
                    }
                }
                if(count == 0){
                    //没有存储任何一个时,将firstFormFlag置为 false
                    self.firstFormFlag = false;
                }
                console.log(self.form);
                //alert(JSON.stringify(self.form));



                //延迟 等待画表完成后, 获取表格对象
                setTimeout(function () {
                    //获取列表table的id值
                    var dataTableId = $(".bootstrap-table table:eq(1)").attr("id");

                    //页码回显
                    var pageNum = sessionStorage.getItem("currentPageNum");
                    if(pageNum){
                        $("#" + dataTableId).bootstrapTable("getOptions").pageNumber = parseInt(pageNum );
                    }
                    //每页显示条数回显
                    var pageSize = sessionStorage.getItem("currentPageSize");
                    if(pageSize){
                        $("#" + dataTableId).bootstrapTable("getOptions").pageSize = parseInt(pageSize);
                    }

                    //重新赋值
                    /*$("#" + dataTableId).bootstrapTable('selectPage', pageNum);*/

                    //获取列表的 选项options对象
                    /*self.bootstrapTableObj = $("#" + dataTableId).bootstrapTable("selectPage");


                    self.bootstrapTableObj = $(".pagination");
                    console.log(self.bootstrapTableObj);*/

                    //监听 pageNum和pageSize的变化,并将其存入 sessionStorage
                    $("#" + dataTableId).on('page-change.bs.table',function (obj, pageNum, pageSize) {
                        //将当前页码存储起来
                        sessionStorage.setItem("currentPageNum", pageNum);
                        sessionStorage.setItem("currentPageSize", pageSize);
                    });

                    //取出数据后,清空sessionStorage
                    sessionStorage.clear();
                },100);
            }
        }
    }
};
