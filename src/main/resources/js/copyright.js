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
            me.properties.QRcode = $(".QRcode").attr("src");

            $.each($(".companyAddress"), function (i, obj) {
                me.properties.companyAddress = $(obj).val();
            });
            $.each($(".copyBColor"), function (i, obj) {
                me.properties.copyBColor = $(obj).val();
            });
            $.each($(".copyTColor"), function (i, obj) {
                me.properties.copyTColor = $(obj).val();
            });
            var nodes = $.getTreeViewData();
            me.properties.pageLinkList = nodes;
            if (me.properties.contactInformation == '' || me.properties.companyAddress == '') {
                onFailed("控件缺乏参数，请填写参数");
                return;
            }
            onSuccess(me.properties);
            return me.properties;
        },
        initProperties: function () {
            var that = this;
            if (that.properties.pageLinkList == undefined) {
                var node = [{id: 1, name: '首页', flag: 0, linkPath: 'www'}, {
                    id: 2,
                    name: '首页2',
                    flag: 0,
                    linkPath: 'www'
                }, {id: 3, name: '首页3', flag: 0, linkPath: 'www'}]
                $('#treeView').addTreeView({
                    debug: true,
                    treeNodes: node
                });
            } else {
                $('#treeView').addTreeView({
                    debug: true,
                    treeNodes: that.properties.pageLinkList
                });
            }
            $('.js-addEditBtn').addEdit({
                amount: 1,
                title: '二维码图片',
                hasImage: true,
                imageClass: 'QRcode'
            });

        },
        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.initProperties();

        },
        close: function (globalId) {
        }
    }
});
