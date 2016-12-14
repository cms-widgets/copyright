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
        return "SiFan样式";
    }

    @Override
    public String name(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return name();
        }
        return "SiFan style";
    }

    @Override
    public String description() {
        return "版权样式控件,该样式仅展示链接列表，站点logo,二维码图片。";
    }

    @Override
    public String description(Locale locale) {
        if (locale.equals(Locale.CHINA)) {
            return description();
        }
        return "Copyright style controls, the style is only to show the list of links, site logo," +
                " two-dimensional code pictures.";
    }

    @Override
    public Resource thumbnail() {
        return new ClassPathResource("thumbnail/sifanCopyright.png", getClass().getClassLoader());
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
