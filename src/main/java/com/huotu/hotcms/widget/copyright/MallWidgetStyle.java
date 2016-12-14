package com.huotu.hotcms.widget.copyright;

import com.huotu.hotcms.widget.WidgetStyle;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.Locale;

/**
 * Created by lhx on 2016/8/18.
 */

public class MallWidgetStyle implements WidgetStyle {
    @Override
    public String id() {
        return "MallStyle";
    }

    @Override
    public String name() {
        return "商城样式";
    }

    @Override
    public String name(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return name();
        }
        return "shopping mall style";
    }

    @Override
    public String description() {
        return "商城版权样式，该样式不展示图片、二维码及业务热线。";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return description();
        }
        return "Mall copyright style, the style does not show pictures, two-dimensional code and business hotline.";
    }

    @Override
    public Resource thumbnail() {
        return new ClassPathResource("thumbnail/mallstyleThumbnail.png", getClass().getClassLoader());
    }

    @Override
    public Resource previewTemplate() {
        return null;
    }

    @Override
    public Resource browseTemplate() {
        return new ClassPathResource("/template/mallStyleBrowseTemplate.html", getClass().getClassLoader());
    }
}
