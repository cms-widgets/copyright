package com.huotu.hotcms.widget.copyright;

import com.huotu.hotcms.widget.WidgetStyle;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Locale;

/**
 * Created by lhx on 2016/8/18.
 */

public class HyysWidgetStyle implements WidgetStyle {
    @Override
    public String id() {
        return "hyysCopyrightStyle";
    }

    @Override
    public String name() {
        return "二级链接样式";
    }

    @Override
    public String name(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return name();
        }
        return "hyys style";
    }

    @Override
    public String description() {
        return "基于bootstrap样式的版权信息";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return "基于bootstrap样式的 ";
        }
        return "Based on the bootstrap style by copyright";
    }

    @Override
    public Resource thumbnail() {
        return new ClassPathResource("thumbnail/hyys.png", getClass().getClassLoader());
    }

    @Override
    public Resource previewTemplate() {
        return null;
    }

    @Override
    public Resource browseTemplate() {
        return new ClassPathResource("/template/seclevelStyleBrowserTemplate.html", getClass().getClassLoader());
    }
}