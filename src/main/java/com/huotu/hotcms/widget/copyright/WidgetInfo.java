/*
 * 版权所有:杭州火图科技有限公司
 * 地址:浙江省杭州市滨江区西兴街道阡陌路智慧E谷B幢4楼
 *
 * (c) Copyright Hangzhou Hot Technology Co., Ltd.
 * Floor 4,Block B,Wisdom E Valley,Qianmo Road,Binjiang District
 * 2013-2016. All rights reserved.
 */

package com.huotu.hotcms.widget.copyright;

import com.huotu.hotcms.service.common.ContentType;
import com.huotu.hotcms.service.entity.Category;
import com.huotu.hotcms.service.entity.Gallery;
import com.huotu.hotcms.service.entity.GalleryItem;
import com.huotu.hotcms.service.model.GalleryItemModel;
import com.huotu.hotcms.service.repository.CategoryRepository;
import com.huotu.hotcms.service.repository.GalleryRepository;
import com.huotu.hotcms.service.service.CategoryService;
import com.huotu.hotcms.service.service.ContentService;
import com.huotu.hotcms.service.service.GalleryItemService;
import com.huotu.hotcms.service.service.GalleryService;
import com.huotu.hotcms.widget.*;
import com.huotu.hotcms.widget.entity.PageInfo;
import com.huotu.hotcms.widget.service.CMSDataSourceService;
import me.jiangcai.lib.resource.service.ResourceService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;


/**
 * @author CJ
 */
public class WidgetInfo implements Widget, PreProcessWidget {
    public static final String VALID_COPY_ADDRESS = "companyAddress";
    public static final String VALID_COPY_INFORMATION = "contactInformation";
    public static final String VALID_COPY_BCOLOR = "copyBColor";
    public static final String VALID_COPY_TCOLOR = "copyTColor";
    public static final String VALID_COPY_PAGElINKS = "pageLinkList";
    public static final String VALID_COPY_QRCODE = "QRcodeSerial";
    public static final String VALID_COPY_PICIMG = "picImgSerial";
    public static final String VALID_COPY_QRCODE_ITEMS = "QRCodeItems";
    public static final String VALID_COPY_PICIMG_ITEMS = "picImgItems";

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
            return "这是一个版权信息";
        }
        return "This is a copyright";
    }

    @Override
    public String dependVersion() {
        return "1.1.0";
    }

    @Override
    public WidgetStyle[] styles() {
        return new WidgetStyle[]{new DefaultWidgetStyle(), new SiFanWidgetStyle(), new HyysWidgetStyle(), new MallWidgetStyle()};
    }

    @Override
    public Map<String, Resource> publicResources() {
        Map<String, Resource> map = new HashMap<>();
        map.put("thumbnail/defaultStyleThumbnail.png", new ClassPathResource("thumbnail/defaultStyleThumbnail.png"
                , getClass().getClassLoader()));
        map.put("thumbnail/sifanCopyright.png", new ClassPathResource("thumbnail/sifanCopyright.png"
                , getClass().getClassLoader()));
        map.put("thumbnail/mallstyleThumbnail.png", new ClassPathResource("thumbnail/mallstyleThumbnail.png"
                , getClass().getClassLoader()));
        map.put("thumbnail/hyys.png", new ClassPathResource("thumbnail/hyys.png"
                , getClass().getClassLoader()));
        map.put("img/code.jpg", new ClassPathResource("img/code.jpg", getClass().getClassLoader()));
        map.put("img/picImg.png", new ClassPathResource("img/picImg.png", getClass().getClassLoader()));
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
        WidgetStyle style = WidgetStyle.styleByID(this, styleId);
        if (style.id().equals("siFanCopyrightStyle")) {
            String qrcode = (String) componentProperties.get(VALID_COPY_QRCODE);
            String picImg = (String) componentProperties.get(VALID_COPY_PICIMG);
            if (qrcode == null || picImg == null) {
                throw new IllegalArgumentException("控件属性缺少,没二维码和图片");
            }
        }
        //加入控件独有的属性验证
        String companyAddress = (String) componentProperties.get(VALID_COPY_ADDRESS);
        String contactInformation = (String) componentProperties.get(VALID_COPY_INFORMATION);
        List<Map> pageLinks = (List<Map>) componentProperties.get(VALID_COPY_PAGElINKS);
        if (companyAddress == null || contactInformation == null || pageLinks == null || companyAddress.equals("")
                || contactInformation.equals("")) {
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
        properties.put(VALID_COPY_INFORMATION, "400-1818-357 加盟热线：400-1008-013");
        properties.put(VALID_COPY_ADDRESS, "杭州市滨江区阡陌路482号智慧e谷B幢4楼");
        properties.put(VALID_COPY_BCOLOR, "#fff");
        // 随意找一个数据源,如果没有。那就没有。。
        GalleryRepository galleryRepository = getCMSServiceFromCMSContext(GalleryRepository.class);
        List<Gallery> galleryList = galleryRepository.findByCategory_Site(CMSContext.RequestContext().getSite());
        if (galleryList.isEmpty()) {
            Gallery gallery = initGallery(initCategory());
            initGalleryItem(gallery, resourceService);
            properties.put(VALID_COPY_QRCODE, gallery.getSerial());
            properties.put(VALID_COPY_PICIMG, gallery.getSerial());
        } else {
            properties.put(VALID_COPY_QRCODE, galleryList.get(0).getSerial());
            properties.put(VALID_COPY_PICIMG, galleryList.get(0).getSerial());
        }
        properties.put(VALID_COPY_TCOLOR, "#000000");
        List<Map<String, Object>> pageLinks = new ArrayList<>();
        PageInfo pageInfo1 = new PageInfo();
        pageInfo1.setTitle("首页");
        pageInfo1.setPagePath("");
        pageInfo1.setId(1L);

        PageInfo pageInfo2 = new PageInfo();
        pageInfo2.setTitle("新闻");
        pageInfo2.setPagePath("xw");
        pageInfo2.setId(2L);


        PageInfo pageInfo3 = new PageInfo();
        pageInfo3.setTitle("关于我们");
        pageInfo3.setPagePath("guwm");
        pageInfo3.setId(3L);

        List<PageInfo> pageInfos = new ArrayList<>();
        pageInfos.add(pageInfo1);
        pageInfos.add(pageInfo2);
        pageInfos.add(pageInfo3);
        for (PageInfo pageInfo : pageInfos) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", pageInfo.getTitle());
            map.put("linkPath", pageInfo.getPagePath());
            map.put("flag", 0);
            map.put("id", pageInfo.getId());
            pageLinks.add(map);
        }
        Map<String, Object> map2 = new HashMap<>();
        List<Map<String, Object>> children = new ArrayList<>();
        Map<String, Object> map21 = new HashMap<>();
        map21.put("name", "公司动态");
        map21.put("linkPath", "");
        map21.put("visibleValue", "");
        map21.put("flag", 1);
        map21.put("visible", "");
        map21.put("visibleName", "");
        map21.put("pid", 4);

        Map<String, Object> map22 = new HashMap<>();
        map22.put("name", "行业动态");
        map22.put("linkPath", "");
        map22.put("visibleValue", "");
        map22.put("visible", "");
        map22.put("visibleName", "");
        map22.put("flag", 1);
        map22.put("pid", 4);
        children.add(map21);
        children.add(map22);

        map2.put("name", "动态资讯");
        map2.put("linkPath", "");
        map2.put("visibleValue", "");
        map2.put("isParent", "true");
        map2.put("visible", "");
        map2.put("visibleName", "");
        map2.put("children", children);
        map2.put("flag", 0);
        map2.put("id", 4);
        pageLinks.add(map2);

        properties.put(VALID_COPY_PAGElINKS, pageLinks);
        return properties;
    }


    @Override
    public void prepareContext(WidgetStyle style, ComponentProperties properties, Map<String, Object> variables, Map<String, String> parameters) {
        String qrCodeSerial = (String) properties.get(VALID_COPY_QRCODE);
        String picImgSerial = (String) properties.get(VALID_COPY_PICIMG);
        CMSDataSourceService cmsDataSourceService = getCMSServiceFromCMSContext(CMSDataSourceService.class);
        List<GalleryItemModel> qrCode = cmsDataSourceService.findGalleryItems(qrCodeSerial, 1);
        List<GalleryItemModel> picImg = cmsDataSourceService.findGalleryItems(picImgSerial, 1);
        variables.put(VALID_COPY_QRCODE_ITEMS, qrCode);
        variables.put(VALID_COPY_PICIMG_ITEMS, picImg);
    }


    /**
     * 初始化数据源
     *
     * @return
     */
    private Category initCategory() {
        CategoryService categoryService = getCMSServiceFromCMSContext(CategoryService.class);
        CategoryRepository categoryRepository = getCMSServiceFromCMSContext(CategoryRepository.class);
        Category category = new Category();
        category.setContentType(ContentType.Gallery);
        category.setName("默认数据源");
        categoryService.init(category);
        category.setSite(CMSContext.RequestContext().getSite());

        //保存到数据库
        categoryRepository.save(category);
        return category;
    }

    /**
     * 初始化一个图库
     *
     * @return
     */
    private Gallery initGallery(Category category) {
        GalleryService galleryService = getCMSServiceFromCMSContext(GalleryService.class);
        ContentService contentService = getCMSServiceFromCMSContext(ContentService.class);
        Gallery gallery = new Gallery();
        gallery.setTitle("默认图库标题");
        gallery.setDescription("这是一个默认图库");
        gallery.setCategory(category);
        contentService.init(gallery);
        galleryService.saveGallery(gallery);
        return gallery;
    }

    /**
     * 初始化一个图片
     *
     * @param gallery
     * @param resourceService
     * @return
     */
    private GalleryItem initGalleryItem(Gallery gallery, ResourceService resourceService) throws IOException {
        ContentService contentService = getCMSServiceFromCMSContext(ContentService.class);
        GalleryItemService galleryItemService = getCMSServiceFromCMSContext(GalleryItemService.class);
        GalleryItem galleryItem = new GalleryItem();
        galleryItem.setTitle("默认图片标题");
        galleryItem.setDescription("这是一个默认图片");
        ClassPathResource classPathResource = new ClassPathResource("img/picImg.png", getClass().getClassLoader());
        InputStream inputStream = classPathResource.getInputStream();
        String imgPath = "_resources/" + UUID.randomUUID().toString() + ".png";
        resourceService.uploadResource(imgPath, inputStream);
        galleryItem.setThumbUri(imgPath);
        galleryItem.setSize("xxx");
        galleryItem.setGallery(gallery);
        contentService.init(galleryItem);
        galleryItemService.saveGalleryItem(galleryItem);
        return galleryItem;
    }


}
