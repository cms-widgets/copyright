/**
 * Created by admin on 2016/6/24.
 */
CMSWidgets.initWidget({
// 编辑器相关
    editor: {
        saveComponent: function (onFailed) {
            this.properties.copyLayout = $("input[name='copyLayout']:checked").val();
            this.properties.cuttingColor = $("input[name='cuttingColor']").val();
            this.properties.activeColor = $("input[name='activeColor']").val();
            this.properties.hoverColor = $("input[name='hoverColor']").val();
            this.properties.copyPaddingTop = $("input[name='copyPaddingTop']").val();
            this.properties.paddingRight = $("input[name='paddingRight']").val();
            this.properties.copyPaddingBottom = $("input[name='copyPaddingBottom']").val();
            this.properties.paddingLeft = $("input[name='paddingLeft']").val();
            this.properties.contactInformation = $("input[name='contactInformation']").val();
            this.properties.companyAddress = $("input[name='companyAddress']").val();
            this.properties.copyBColor = $("input[name='copyBColor']").val();
            this.properties.copyTColor = $("input[name='copyTColor']").val();
            var me = this;
            if (me.properties.QRcodeSerial == '' || me.properties.picImgSerial == '') {
                onFailed("控件缺乏参数，请填写参数");
                return;
            }
            var nodes = $.getTreeViewData();
            me.properties.pageLinkList = nodes;
            if (me.properties.contactInformation == '' || me.properties.companyAddress == '') {
                onFailed("控件缺乏参数，请填写参数");
                return;
            }
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
                    treeNodes: node
                });
            } else {
                $('#treeView').addTreeView({
                    treeNodes: that.properties.pageLinkList
                });
            }
        },
        open: function (globalId) {
            this.initProperties();
        }
    }
});
