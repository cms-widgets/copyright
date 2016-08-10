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
            $.each($(".companyAddress"), function (i, obj) {
                me.properties.companyAddress = $(obj).val();
            });
            $.each($(".copyBColor"), function (i, obj) {
                me.properties.copyBColor = $(obj).val();
            });
            $.each($(".copyTColor"), function (i, obj) {
                me.properties.copyTColor = $(obj).val();
            });
            $.each($(".copyrightContent"), function (i, obj) {
                me.properties.copyrightContent = $(obj).val();
            });
            if (me.properties.contactInformation=='' || me.properties.companyAddress=='' || me.properties.copyrightContent==''){
                onFailed("控件缺乏参数，请填写参数");
                return;
            }
            onSuccess(me.properties);
            return me.properties;
        },
        uploadImage: function () {
            var me = this;
            uploadForm({
                ui: '#copyrightImage',
                inputName: 'file',
                maxWidth: 200,
                maxHeight: 200,
                maxFileCount: 1,
                isCongruent: false,
                successCallback: function (files, data, xhr, pd) {
                    me.properties.QRcodeUri = data.fileUri;
                },
                deleteCallback: function (resp, data, jqXHR) {
                    me.properties.QRcodeUri = "";
                }
            });
        },
        initProperties: function () {
            this.properties.copyBColor = "#000000";
            this.properties.copyTColor = "#000000";
            this.properties.companyAddress = "";
            this.properties.contactInformation = "";
            this.properties.copyrightContent = "";
            this.properties.pageLinkList = [];
            this.properties.QRcodeUri = "";
            var that = this;
            var setting = {
                check: {
                    enable: true
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback:{
                    onCheck:onCheck
                }
            };
            function onCheck(e,treeId,treeNode){
                that.properties.pageLinkList=[];
                var treeObj=$.fn.zTree.getZTreeObj("treeView");
                var nodes=treeObj.getCheckedNodes(true);
                for(var i=0;i<nodes.length;i++){
                    var item = {
                        id: nodes[i].id,
                        pid: nodes[i].pid,
                        name: nodes[i].name,
                        pagePath: nodes[i].pagePath
                    };
                    that.properties.pageLinkList.push(item)
                }
            }

            /*<![CDATA[*/
            var zNodes = /*[[${@cmsDataSourceService.findSiteNotParentPage()}]]*/ '[]';
            /*]]>*/
            $.fn.zTree.init($("#treeView"), setting, jQuery.parseJSON(zNodes));

        },
        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.initProperties();
            this.uploadImage();
        },
        close: function (globalId) {
            $('#copyrightImage').siblings().remove();
        }
    }
});
