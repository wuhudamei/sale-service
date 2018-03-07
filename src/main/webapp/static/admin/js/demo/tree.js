var tt = null;
+(function (DameiUtils) {
    // 引入文件:
    // /static/admin/js/directives/clickoutside.js 全局引入就可以了,本系统已引入在 include/admin/head.jsp
    // /static/css/wx/zTreeStyle.css
    // /static/admin/js/jquery.ztree.core.js
    // /static/admin/js/jquery.ztree.excheck.js

    // <%@include file="/WEB-INF/views/admin/component/ztree.jsp" %>
    // <script src="/static/admin/js/components/ztree.js"></script>

    $('#dictionaryMenu').addClass('active');

    tt = new Vue({
        el: '#container',
        data: {

            nodesCheck: null,
            nodesSelect: null,
            showTreeCheck: false,
            showTreeSelect: false
        },
        methods: {
            // 该方法获取checkbox选择状态变化的节点数据
            treeCheckboxChange: function(node) {
                console.log(node);
            },
            // 点击节点的时候返回该节点的数据
            treeClick: function(node) {
                console.log(node);
            },
            createBtnClickHandler: function() {
                var self = this;
                // 获取选择的checkbox节点
                var nodesCheckbox =  self.$refs.nodesCheckbox.getCheckedNodes();
                console.log(nodesCheckbox);
                // 获取选择的节点
                var nodesSelect = self.$refs.nodesSelect && self.$refs.nodesSelect.getSelectedNodes();
                console.log(nodesSelect);
            },
            clickOut: function() {
                this.showTreeSelect = false;

            },
            clickOutTT: function() {
                this.showTreeCheck = false;
            }
        },
        created: function () {
            var self = this;

            setTimeout(function() {
                // 树的数据结构
                // 如果需要默认显示选择状态，则需要设置 checked:true
                self.nodesCheck = [
                    { id:1, pId:0, name:"随意勾选 1", open:true},
                    { id:11, pId:1, name:"随意勾选 1-1", open:true},
                    { id:111, pId:11, name:"随意勾选 1-1-1"},
                    { id:112, pId:11, name:"随意勾选 1-1-2"},
                    { id:12, pId:1, name:"随意勾选 1-2", open:true},
                    { id:121, pId:12, name:"随意勾选 1-2-1"},
                    { id:122, pId:12, name:"随意勾选 1-2-2"},
                    { id:2, pId:0, name:"随意勾选 2", checked:true, open:true},
                    { id:21, pId:2, name:"随意勾选 2-1"},
                    { id:22, pId:2, name:"随意勾选 2-2", open:true, checked:true},
                    { id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
                    { id:222, pId:22, name:"随意勾选 2-2-2"},
                    { id:23, pId:2, name:"随意勾选 2-3"}
                ];

                self.nodesSelect = [
                    { id:1, pId:0, name:"随意勾选 1", open:true},
                    { id:11, pId:1, name:"随意勾选 1-1", open:true},
                    { id:111, pId:11, name:"随意勾选 1-1-1"},
                    { id:112, pId:11, name:"随意勾选 1-1-2"},
                    { id:12, pId:1, name:"随意勾选 1-2", open:true},
                    { id:121, pId:12, name:"随意勾选 1-2-1"},
                    { id:122, pId:12, name:"随意勾选 1-2-2"},
                    { id:2, pId:0, name:"随意勾选 2", open:true},
                    { id:21, pId:2, name:"随意勾选 2-1"},
                    { id:22, pId:2, name:"随意勾选 2-2", open:true},
                    { id:221, pId:22, name:"随意勾选 2-2-1"},
                    { id:222, pId:22, name:"随意勾选 2-2-2"},
                    { id:23, pId:2, name:"随意勾选 2-3"}
                ]
            }, 1000)
        },
        ready: function () {

        }
    });

})(DameiUtils);