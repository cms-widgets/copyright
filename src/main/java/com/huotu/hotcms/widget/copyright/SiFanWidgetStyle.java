package com.huotu.hotcms.widget.copyright;

import com.huotu.hotcms.widget.WidgetStyle;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Locale;

/**
 * Created by lhx on 2016/8/18.
 */

public class SiFanWidgetStyle implements WidgetStyle {
    @Override
    public String id() {
        return "siFanCopyrightStyle";
    }

    @Override
    public String name() {
        return "bootstrap 风格版权信息";
    }

    @Override
    public String name(Locale locale) {
        if (locale.equals(Locale.CHINESE)) {
            return name();
        }
        return "bootstrap style copyright";
    }

    @Override
    public String description() {
        return "基于bootstrap样式的版权信息";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINESE)) {
            return "基于bootstrap样式的  ";
        }
        return "Based on the bootstrap style by copyright";
    }

    @Override
    public Resource thumbnail() {
        return new ClassPathResource("thumbnail/defaultStyleThumbnail.png", getClass().getClassLoader());
    }

    @Override
    public Resource previewTemplate() {
        return null;
    }

    @Override
    public Resource browseTemplate() {
        return new ClassPathResource("/template/sifanStyleBrowseTemplate.html", getClass().getClassLoader());
    }
}