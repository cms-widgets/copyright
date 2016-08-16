package com.huotu.hotcms.widget.copyright;

import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.hotcms.widget.entity.PageInfo;
import com.huotu.widget.test.WidgetTest;
import com.huotu.widget.test.bean.WidgetViewController;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.FileDetector;
import org.openqa.selenium.remote.LocalFileDetector;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by lhx on 2016/6/24.
 */

public class TestWidgetInfo extends WidgetTest {
    @Override
    protected boolean printPageSource() {
        return true;
    }

    @Override
    protected void editorWork(Widget widget, WebElement editor, Supplier<Map<String, Object>> currentWidgetProperties) {
        WebElement contactInformation = editor.findElement(By.className("contactInformation"));
        WebElement companyAddress = editor.findElement(By.className("companyAddress"));
        WebElement copyrightContent = editor.findElement(By.className("copyrightContent"));
        Actions actions = new Actions(driver);
        actions.sendKeys(contactInformation,"abc").build().perform();
        actions.sendKeys(companyAddress,"abc").build().perform();
        actions.sendKeys(copyrightContent,"abc").build().perform();
        Map<String, Object> ps = currentWidgetProperties.get();

        assertThat(ps.get("contactInformation")).isEqualTo("abc");
        assertThat(ps.get("companyAddress")).isEqualTo("abc");
        assertThat(ps.get("copyrightContent")).isEqualTo("abc");

        List<WebElement> lis = editor.findElement(By.id("treeView")).findElements(By.tagName("li"));
        assertThat(lis.size()).isNotEqualTo(0);
        assertThat(lis.size()).isEqualTo(2);
    }



    @Override
    protected void browseWork(Widget widget, WidgetStyle style, Function<ComponentProperties, WebElement> uiChanger) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);
        List<WebElement> list = webElement.findElements(By.tagName("li"));
        assertThat(list).isNotEmpty();
        assertThat(list.size()).isEqualTo(3);
        WebElement infomartion = webElement.findElement(By.className("copyright-contact"));
        WebElement address = webElement.findElement(By.className("copyright-address"));
        WebElement content = webElement.findElement(By.className("copyright-content"));
        assertThat(infomartion.getText()).isEqualTo("400-1818-357 加盟热线：400-1008-013");
        assertThat(address.getText()).isEqualTo("杭州市滨江区阡陌路482号智慧e谷B幢4楼");
        assertThat(content.getText()).isEqualTo("Copyright&copy;2013-2016." + "杭州火图科技有限公司. 浙ICP备13027761号-5");
    }

    @Override
    protected void editorBrowseWork(Widget widget, Function<ComponentProperties, WebElement> uiChanger
            , Supplier<Map<String, Object>> currentWidgetProperties) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);
        String address = webElement.findElement(By.name(WidgetInfo.VALID_COPY_ADDRESS)).getAttribute("value");
        String content = webElement.findElement(By.name(WidgetInfo.VALID_COPY_CONTENT)).getAttribute("value");
        String informaction = webElement.findElement(By.name(WidgetInfo.VALID_COPY_INFORMATION)).getAttribute("value");
        String bgcolor = webElement.findElement(By.name(WidgetInfo.VALID_COPY_BCOLOR)).getAttribute("value");
        String tcolor = webElement.findElement(By.name(WidgetInfo.VALID_COPY_TCOLOR)).getAttribute("value");
        assertThat(address).isEqualTo(properties.get(WidgetInfo.VALID_COPY_ADDRESS));
        assertThat(content).isEqualTo(properties.get(WidgetInfo.VALID_COPY_CONTENT));
        assertThat(informaction).isEqualTo(properties.get(WidgetInfo.VALID_COPY_INFORMATION));
        assertThat(bgcolor).isEqualTo(properties.get(WidgetInfo.VALID_COPY_BCOLOR));
        assertThat(tcolor).isEqualTo(properties.get(WidgetInfo.VALID_COPY_TCOLOR));
    }
}
