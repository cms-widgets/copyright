/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.copyright;

import com.huotu.hotcms.service.entity.support.WidgetIdentifier;
import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import me.jiangcai.lib.resource.service.ResourceService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


/**
 * @author CJ
 */
public class WidgetInfo implements Widget {
    public static final String VALID_COPY_ADDRESS = "companyAddress";
    public static final String VALID_COPY_INFORMATION = "contactInformation";
    public static final String VALID_COPY_BCOLOR = "copyBColor";
    public static final String VALID_COPY_TCOLOR = "copyTColor";
    public static final String VALID_COPY_CONTENT = "copyrightContent";
    public static final String VALID_COPY_QRCODE_URI = "QRcodeUri";
    public static final String VALID_COPY_PAGElINKS = "pageLinkList";

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
        if (locale.equals(Locale.CHINA)) {
            return "版权信息";
        }
        return "copyright";
    }


    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
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
    public Map<String, Resource> publicResources() {
        Map<String, Resource> map = new HashMap<>();
        map.put("thumbnail/defaultStyleThumbnail.png", new ClassPathResource("thumbnail/defaultStyleThumbnail.png"
                , getClass().getClassLoader()));
        map.put("img/code.jpg", new ClassPathResource("img/code.jpg", getClass().getClassLoader()));
        map.put("js/copyright.js", new ClassPathResource("js/copyright.js", getClass().getClassLoader()));
        return map;
    }

    @Override
    public Resource widgetDependencyContent(MediaType mediaType) {
        if (mediaType.isCompatibleWith(CSS)) {
            return new ClassPathResource("css/copyright.css", getClass().getClassLoader());
        }
        if (mediaType.isCompatibleWith(Javascript)) {
            return new ClassPathResource("js/copyright.js", getClass().getClassLoader());
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
        String copyPTop = (String) componentProperties.get(VALID_COPY_ADDRESS);
        String copyPBottom = (String) componentProperties.get(VALID_COPY_INFORMATION);
        String copyBColor = (String) componentProperties.get(VALID_COPY_BCOLOR);
        String copyTColor = (String) componentProperties.get(VALID_COPY_TCOLOR);
        String copyContent = (String) componentProperties.get(VALID_COPY_CONTENT);
        List<Map> pageLinks = (List<Map>) componentProperties.get(VALID_COPY_PAGElINKS);
        if (copyPTop == null || copyPBottom == null || copyBColor == null || copyTColor == null
               || pageLinks==null || copyContent == null || copyPTop.equals("") || copyPBottom.equals("")
                || copyBColor.equals("") || copyTColor.equals("") || copyContent.equals("") ||pageLinks.size()==0) {
            throw new IllegalArgumentException("控件属性缺少");
        }

    }

    @Override
    public Class springConfigClass() {
        return null;
    }

    @Override
    public ComponentProperties defaultProperties(ResourceService resourceService) throws IOException {
        ComponentProperties properties = new ComponentProperties();
        WidgetIdentifier identifier = new WidgetIdentifier(groupId(), widgetId(), version());
        properties.put(VALID_COPY_ADDRESS, "400-1818-357 加盟热线：400-1008-013");
        properties.put(VALID_COPY_INFORMATION, "杭州市滨江区阡陌路482号智慧e谷B幢4楼");
        properties.put(VALID_COPY_BCOLOR, "#fff");
        properties.put(VALID_COPY_TCOLOR, "#000000");
        properties.put(VALID_COPY_CONTENT, "Copyright&copy;2013-2016." + "杭州火图科技有限公司. 浙ICP备13027761号-5");
        try {
            properties.put(VALID_COPY_QRCODE_URI, resourceService.getResource("widget/" + identifier.toURIEncoded()
                    + "/img/code.jpg").httpUrl().toURI().toString());
        } catch (URISyntaxException e) {
            throw new IOException(e);
        }
        List<Map<String,Object>> pageLinks = new ArrayList<>();
        Map<String,Object> map = new HashMap();
        map.put("name","链接");
        map.put("url","url");
        pageLinks.add(map);
        pageLinks.add(map);
        pageLinks.add(map);
        pageLinks.add(map);
        pageLinks.add(map);
        pageLinks.add(map);
        properties.put(VALID_COPY_PAGElINKS,pageLinks);
        return properties;
    }

}
