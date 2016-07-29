/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.copyright;

import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import me.jiangcai.lib.resource.service.ResourceService;
import org.apache.http.entity.ContentType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * @author CJ
 */
public class WidgetInfo implements Widget{
    public static final String VALID_COPY_PTOP = "copyPTop";
    public static final String VALID_COPY_PBOTTOM = "copyPBottom";
    public static final String VALID_COPY_BCOLOR = "copyBColor";
    public static final String VALID_COPY_TCOLOR = "copyTColor";
    public static final String VALID_COPY_FSIZE = "copyFSize";
    public static final String VALID_COPY_TBOLD = "copyTBold";
    public static final String VALID_COPY_CONTENT = "copyContent";

    /*
     * 指定风格的模板类型 如：html,text等
     */
    public static final String VALID_STYLE_TEMPLATE = "styleTemplate";

    @Override
    public String groupId() {
        return "com.huotu.hotcms.widget.copyright";
    }

    @Override
    public String widgetId() {
        return "copyright";
    }


    @Override
    public String name(Locale locale) {
        if (locale.equals(Locale.CHINESE)) {
            return "版权信息";
        }
        return "copyright";
    }

    @Override
    public String description() {
        return "这是一个版权信息，你可以对组件进行自定义修改。";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINESE)) {
            return "这是一个版权信息，你可以对组件进行自定义修改。";
        }
        return "This is a copyright,  you can make custom change the component.";
    }

    @Override
    public int dependBuild() {
        return 0;
    }

    @Override
    public WidgetStyle[] styles() {
        return new WidgetStyle[]{new DefaultWidgetStyle()};
    }


    @Override
    public Resource widgetJs() {
        return new ClassPathResource("js/copyright.js", getClass().getClassLoader());
    }


    @Override
    public Map<String, Resource> publicResources() {
        Map<String, Resource> map = new HashMap<>();
        map.put("thumbnail/defaultStyleThumbnail.png",new ClassPathResource("thumbnail/defaultStyleThumbnail.png"
                ,getClass().getClassLoader()));
        return map;
    }

    @Override
    public Resource widgetDependencyContent(ContentType contentType) {
        if (contentType.getMimeType().equalsIgnoreCase("text/css")){
            return  new ClassPathResource("css/copyright.css",getClass().getClassLoader());
        }
        return null;
    }

    @Scheduled
    @Override
    public void valid(String styleId, ComponentProperties componentProperties) throws IllegalArgumentException {
        WidgetStyle[] widgetStyles = styles();
        boolean flag = false;
        if (widgetStyles == null || widgetStyles.length < 1) {
            throw new IllegalArgumentException();
        }
        for (WidgetStyle ws : widgetStyles) {
            if ((flag = ws.id().equals(styleId))) {
                break;
            }
        }
        if (!flag) {
            throw new IllegalArgumentException();
        }
        //加入控件独有的属性验证
        String copyPTop = (String) componentProperties.get(VALID_COPY_PTOP);
        String copyPBottom = (String) componentProperties.get(VALID_COPY_PBOTTOM);
        String copyBColor = (String) componentProperties.get(VALID_COPY_BCOLOR);
        String copyTColor = (String) componentProperties.get(VALID_COPY_TCOLOR);
        String bold = (String) componentProperties.get(VALID_COPY_TBOLD);
        String copyFSize = (String) componentProperties.get(VALID_COPY_FSIZE);
        String copyContent = (String) componentProperties.get(VALID_COPY_CONTENT);
        if (copyPTop == null || copyPBottom == null || copyBColor == null || copyTColor == null || bold == null
                || copyFSize == null || copyContent==null || copyPTop.equals("") || copyPBottom.equals("")
                || copyBColor.equals("") || copyTColor.equals("") || copyFSize.equals("")|| copyContent.equals("")) {
            throw new IllegalArgumentException("控件属性缺少");
        }

    }

    @Override
    public Class springConfigClass() {
        return null;
    }

    @Override
    public ComponentProperties defaultProperties(ResourceService resourceService) {
        ComponentProperties properties = new ComponentProperties();
        properties.put(VALID_COPY_PTOP,"10px");
        properties.put(VALID_COPY_PBOTTOM,"10px");
        properties.put(VALID_COPY_BCOLOR,"#fff");
        properties.put(VALID_COPY_TCOLOR,"#000000");
        properties.put(VALID_COPY_FSIZE,"16px");
        properties.put(VALID_COPY_TBOLD,true);
        properties.put(VALID_COPY_CONTENT,"Copyright&copy;2013-2016.\n" +"杭州火图科技有限公司. 浙ICP备13027761号-5");
        return properties;
    }

}
