/**
 * Created by admin on 2016/6/24.
 */
CMSWidgets.initWidget({
// 编辑器相关
    editor: {
        properties: null,
        saveComponent: function (onSuccess, onFailed) {
            var me = this;
            $.each($(".contactInformation"), function (i, obj) {
                me.properties.contactInformation = $(obj).val();
            });
            me.properties.QRcode = $(".QRcode").attr("src")

            $.each($(".companyAddress"), function (i, obj) {
                me.properties.companyAddress = $(obj).val();
            });
            $.each($(".copyBColor"), function (i, obj) {
                me.properties.copyBColor = $(obj).val();
            });
            $.each($(".copyTColor"), function (i, obj) {
                me.properties.copyTColor = $(obj).val();
            });
            var treeObj = $.fn.zTree.getZTreeObj("treeView");
            var nodes = treeObj.transformToArray(treeObj.getNodes());
            me.properties.pageLinkList=nodes;
            if (me.properties.contactInformation=='' || me.properties.companyAddress==''){
                onFailed("控件缺乏参数，请填写参数");
                return;
            }
            onSuccess(me.properties);
            return me.properties;
        },
        initProperties: function () {
            $('.js-addEditBtn').addEdit({
                amount: 1,
                hasImage: true,
                imageClass: 'QRcode'
            });
            var treeNode = null;
            var setting = {
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onClick: onClick
                }
            };
            function onClick(event, treeId, treenode) {
                treeNode = treenode;
                $("input[name='name'] ").val(treenode.name);
                $("input[name='pagePath'] ").val(treenode.pagePath);
            };
            if(this.properties.pageLinkList==undefined || this.properties.pageLinkList.length==0){
                this.properties.pageLinkList = [{id:1,name:'首页',pagePath:'/'}
                    ,{id:2,name:'关于我们',pagePath:'/'}
                    ,{id:3,name:'加入我们',pagePath:'/'}];
            }
            $.fn.zTree.init($("#treeView"), setting, this.properties.pageLinkList);

            $(".form-horizontal").on("click", ".addRootNodes", function () {
                var rootNode = {id: 1, name: "rootNode1", uri: ''};
                var treeObj = $.fn.zTree.getZTreeObj("treeView").addNodes(null, rootNode);
            });

            $(".form-horizontal").on("click", ".delNodes", function () {
                $.fn.zTree.getZTreeObj("treeView").removeNode(treeNode);
                treeNode = null;
            });

            $(".form-horizontal").on("click", ".reset", function () {
                $.fn.zTree.init($("#treeView"), setting, '');
                treeNode = null;
            });

            $(".form-horizontal").on("click", ".saveNode", function () {
                treeNode.name = $("input[name='name'] ").val();
                treeNode.pagePath = $("input[name='pagePath'] ").val();
                $.fn.zTree.getZTreeObj("treeView").updateNode(treeNode);
            });
        },
        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.initProperties();

        },
        close: function (globalId) {
            $(".form-horizontal").off("click", ".addRootNodes");
            $(".form-horizontal").off("click", ".saveNode");
            $(".form-horizontal").off("click", ".reset");
            $(".form-horizontal").off("click", ".delNodes");
        }
    }
});
