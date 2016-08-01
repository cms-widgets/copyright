package com.huotu.hotcms.widget.copyright;

import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.widget.test.WidgetTest;
import com.huotu.widget.test.WidgetTestConfig;
import com.huotu.widget.test.bean.WidgetViewController;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

/**
 * Created by lhx on 2016/6/24.
 */

public class TestWidgetInfo extends WidgetTest {
    @Override
    protected boolean printPageSource() {
        return false;
    }

    @Autowired
    private WidgetViewController widgetViewController;

    @Override
    protected void editorWork(Widget widget, WebElement editor, Supplier<Map<String, Object>> currentWidgetProperties) {

        Map<String,Object> ps = currentWidgetProperties.get();
        String bold = ps.get("copyTBold").toString();
        assertThat(bold).as("默认没有选中粗体").isEqualTo("false");

        //设置粗体
        WebElement elementBold = editor.findElement(By.className("copyTBold"));
        elementBold.click();

        ps = currentWidgetProperties.get();
        bold = ps.get("copyTBold").toString();
        assertThat(bold).as("单击选中粗体").isEqualTo("true");

        bold = ps.get("copyFSize").toString();
        assertThat(bold).as("默认12px").isEqualTo("12px");

    }

    @Override
    protected void browseWork(Widget widget, WidgetStyle style, Function<ComponentProperties, WebElement> uiChanger) {

        ComponentProperties componentProperties = new ComponentProperties();
        ComponentProperties properties = new ComponentProperties();
        properties.put("copyPTop","20px");
        properties.put("copyPBottom",  "20px");
        properties.put("copyBColor", "#111111");
        properties.put("copyTColor", "#111111");
        properties.put("copyFSize", "19px");
        properties.put("copyTBold", "true");
        properties.put("copyContent", "火图的版权信息");
        componentProperties.put("properties", properties);

        WebElement webElement = uiChanger.apply(componentProperties);

        List<WebElement> copyrightA = webElement.findElements(By.className("copyright"));
        assertThat(copyrightA.size()).isEqualTo(1);
        assertThat(copyrightA.get(0).getText()).isEqualToIgnoringCase("火图的版权信息");

    }
}
