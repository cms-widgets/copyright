package com.huotu.hotcms.widget.copyright;

import com.huotu.hotcms.widget.ComponentProperties;
import com.huotu.hotcms.widget.Widget;
import com.huotu.hotcms.widget.WidgetStyle;
import com.huotu.widget.test.Editor;
import com.huotu.widget.test.WidgetTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
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
    protected void editorWork(Widget widget, Editor editor, Supplier<Map<String, Object>> currentWidgetProperties) {
        WebElement contactInformation = editor.getWebElement().findElement(By.className("contactInformation"));
        WebElement companyAddress = editor.getWebElement().findElement(By.className("companyAddress"));
        contactInformation.clear();
        companyAddress.clear();
        Actions actions = new Actions(driver);
        actions.sendKeys(contactInformation, "abc").build().perform();
        actions.sendKeys(companyAddress, "abc").build().perform();
        Map<String, Object> ps = currentWidgetProperties.get();

        assertThat(ps.get("contactInformation")).isEqualTo("abc");
        assertThat(ps.get("companyAddress")).isEqualTo("abc");

        List<WebElement> lis = editor.getWebElement().findElement(By.id("treeView")).findElement(By.className("ztree"))
                .findElements(By.tagName("li"));
        assertThat(lis.size()).isNotEqualTo(0);
        assertThat(lis.size()).isEqualTo(3);

        WebElement addRootNodes = editor.getWebElement().findElement(By.className("js-addRoot"));
        addRootNodes.click();
        lis = editor.getWebElement().findElement(By.id("treeView")).findElement(By.className("ztree"))
                .findElements(By.tagName("li"));
        assertThat(lis.size()).isEqualTo(4);

        lis = editor.getWebElement().findElement(By.id("treeView")).findElement(By.className("ztree"))
                .findElements(By.tagName("li"));
        lis.get(0).findElement(By.tagName("a")).click();

        WebElement name = editor.getWebElement().findElement(By.name("text"));
        WebElement pagePath = editor.getWebElement().findElement(By.name("url"));
        name.clear();
        pagePath.clear();
        actions.sendKeys(name, "abc").build().perform();
        actions.sendKeys(pagePath, "abc").build().perform();

        WebElement saveNode = editor.getWebElement().findElement(By.className("js-save-node"));
        saveNode.click();

        Map map = currentWidgetProperties.get();
        List<Map<String, Object>> links = (List<Map<String, Object>>) map.get(WidgetInfo.VALID_COPY_PAGElINKS);
        assertThat(links.get(0).get("name").toString()).isEqualTo("abc");
        assertThat(links.get(0).get("linkPath").toString()).isEqualTo("abc");
    }

    @Override
    protected void browseWork(Widget widget, WidgetStyle style, Function<ComponentProperties, WebElement> uiChanger) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);
        List<WebElement> list;
        if (style instanceof MallWidgetStyle) {
            list = webElement.findElements(By.tagName("span"));
        } else {
            list = webElement.findElements(By.tagName("li"));
        }
        assertThat(list).isNotEmpty();
        if (style instanceof HyysWidgetStyle) {
            assertThat(list.size()).isEqualTo(6);
        } else {
            assertThat(list.size()).isEqualTo(4);
        }

        if (style instanceof DefaultWidgetStyle) {
            WebElement infomartion = webElement.findElement(By.className("copyright-contact"));
            assertThat(infomartion.getText()).isEqualTo("400-1818-357 加盟热线：400-1008-013");
        }
        WebElement address = webElement.findElement(By.className("copyright-address"));
        assertThat(address.getText()).isEqualTo("地址：杭州市滨江区阡陌路482号智慧e谷B幢4楼");
    }

    @Override
    protected void editorBrowseWork(Widget widget, Function<ComponentProperties, WebElement> uiChanger
            , Supplier<Map<String, Object>> currentWidgetProperties) throws IOException {
        ComponentProperties properties = widget.defaultProperties(resourceService);
        WebElement webElement = uiChanger.apply(properties);
        driver.findElement(By.id("editorInit")).click();
        String address = webElement.findElement(By.name(WidgetInfo.VALID_COPY_ADDRESS)).getAttribute("value");
        String informaction = webElement.findElement(By.name(WidgetInfo.VALID_COPY_INFORMATION)).getAttribute("value");
        String bgcolor = webElement.findElement(By.name(WidgetInfo.VALID_COPY_BCOLOR)).getAttribute("value");
        String tcolor = webElement.findElement(By.name(WidgetInfo.VALID_COPY_TCOLOR)).getAttribute("value");
        assertThat(address).isEqualTo(properties.get(WidgetInfo.VALID_COPY_ADDRESS));
        assertThat(informaction).isEqualTo(properties.get(WidgetInfo.VALID_COPY_INFORMATION));
        assertThat(bgcolor).isEqualTo(properties.get(WidgetInfo.VALID_COPY_BCOLOR));
        assertThat(tcolor).isEqualTo(properties.get(WidgetInfo.VALID_COPY_TCOLOR));
    }


}
