/**
 * Created by admin on 2016/6/24.
 */
CMSWidgets.initWidget({
// 编辑器相关
    editor: {
        properties: null,
        saveComponent: function (onSuccess, onFailed) {
            var me = this;
            $.each($(".copyPTop"), function (i, obj) {
                me.properties.copyPTop = $(obj).val() + "px";
            });
            $.each($(".copyPBottom"), function (i, obj) {
                me.properties.copyPBottom = $(obj).val() + "px";
            });
            $.each($(".copyBColor"), function (i, obj) {
                me.properties.copyBColor = $(obj).val();
            });
            $.each($(".copyTColor"), function (i, obj) {
                me.properties.copyTColor = $(obj).val();
            });
            $.each($(".copyFSize"), function (i, obj) {
                me.properties.copyFSize = $(obj).val() + "px";
            });
            $.each($(".copyTBold"), function (i, obj) {
                me.properties.copyTBold = $(obj).is(":checked");
            });
            $.each($(".copyContent"), function (i, obj) {
                me.properties.copyContent = $(obj).val();
            });

            onSuccess(me.properties)
            return me.properties;
        },
        initProperties: function () {
            this.properties.copyPTop = "10px";
            this.properties.copyPBottom = "20px";
            this.properties.copyBColor = "#000000"
            this.properties.copyTColor = "#000000"
            this.properties.copyFSize = ""
            this.properties.copyTBold = ""
            this.properties.copyContent = ""
        },
        open: function (globalId) {
            this.properties = widgetProperties(globalId);
            this.initProperties();
        },
        close: function (globalId) {

        }
    }
})
