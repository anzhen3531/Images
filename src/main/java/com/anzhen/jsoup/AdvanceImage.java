package com.anzhen.jsoup;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.anzhen.entity.AImage;
import com.anzhen.entity.AImageTag;
import com.anzhen.entity.ATag;
import com.anzhen.service.AImageService;
import com.anzhen.service.AImageTagService;
import com.anzhen.service.ATagService;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 这个只是针对首页
 */
@Component
@Slf4j
@EnableScheduling
@EnableAsync
public class AdvanceImage {

    /**
     * 爬取的网站基础前缀
     */
    static final String basePath = "https://wallhaven.cc/hot?page=";

    /**
     * 创建默认大小的缓冲区
     */
    static ByteBuffer buffer = ByteBuffer.allocate(1024);

    @Resource
    AImageService aImageService;

    @Resource
    AImageTagService aImageTagService;

    @Resource
    ATagService aTagService;
    /**
     * 封装请求头
     */
    static HashMap<String, String> headers = new HashMap<>();

    /**
     * 文档对象
     */
    static Document document;

    // 大图路径
    public static List<String> hrefs = null;
    /**
     * 缩略图路径
     */
    public static Map<String, String> thumbnailsMapping = null;

    public static List<ATag> tags = null;

    static {
        // 封装请求头
        headers.put("Referer", "https://wallhaven.cc/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"95\", \"Chromium\";v=\"95\", \";Not A Brand\";v=\"99\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "Windows");
        headers.put("User-Agent",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36");
    }

    public static void main(String[] args) {
        AdvanceImage advanceImage = new AdvanceImage();
        advanceImage.scheduledTask();
    }

    /**
     * 开启定时任务 定时爬取图片 将这个定时任务改成定时任务+异步任务
     */
    @Scheduled(cron = "0 0 18 * * ?")
    @Async
    public void scheduledTask() {
        log.info("定时任务执行");
        try {
            getThumbnail(basePath + 1);
        } catch (Exception e) {
            log.error(e.getMessage());
            log.info("由于出现了读取失败的问题 尝试重新运行定时任务  第一次尝试");
            try {
                getThumbnail(basePath + 1);
            } catch (Exception ex) {
                log.info("由于出现了读取失败的问题 尝试重新运行定时任务  第二次尝试");
                try {
                    getThumbnail(basePath + 1);
                } catch (Exception ee) {
                    log.error(ee.getMessage());
                }
            }
        }
    }

    /**
     * 获取缩略图List
     *
     * @param path : 文件路径
     */
    public void getThumbnail(String path) throws Exception {
        ATag aTag = null;
        document = Jsoup.connect(path).headers(headers).get();
        thumbnailsMapping = new HashMap<>();
        log.info("解析文件成功");
        // 获取首页标签的缩略图
        List<String> tagThumbnail = getIndexImage("thumb-listing-page");
        log.info("获取图片选择器成功");
        for (String s : tagThumbnail) {
            // 代码优化 使用异步写入文件
            Document photoDoc = Jsoup.connect(s).headers(headers).get();
            List<String> photoPath = getPhotoPath(photoDoc);
            List<ATag> tags = getTags(photoDoc);
            String s2 = thumbnailsMapping.get(s);
            log.info("key {} -> value {}", s, s2);
            for (String s1 : photoPath) {
                writePhotoByMinIO(s1, s2, tags);
                log.info("photoPath -> {}", s1);
            }
        }
    }

    /**
     * 获取图片的标签
     * 
     * @param document
     * @return
     */
    private List<ATag> getTags(Document document) {
        // 获取标签的外层对象
        tags = new ArrayList<>();
        ATag aTag = null;
        Elements elementsByClass = document.getElementsByClass("tag tag-sfw");
        if (CollUtil.isEmpty(elementsByClass)) {
            return new ArrayList<>();
        }
        for (Element element : elementsByClass) {
            Elements tagAList = element.getElementsByTag("a");
            if (CollUtil.isEmpty(tagAList)) {
                continue;
            }
            for (Element tagA : tagAList) {
                if (StrUtil.isNotBlank(tagA.text())) {
                    aTag = new ATag();
                    aTag.setTagName(tagA.text());
                    tags.add(aTag);
                }
            }
        }
        return tags;
    }

    /**
     * 通过类选择器获取首页的照片粗略图路径
     *
     * @param className 类选择器名称
     */
    public List<String> getIndexImage(String className) {
        // 大图路径
        hrefs = new ArrayList<>();
        Elements elements = document.getElementsByClass(className);
        for (Element element : elements) {
            Elements li = element.getElementsByTag("li");
            for (Element element1 : li) {
                // 获取到A标签之后的内容 保存缩略图
                Elements a = element1.getElementsByTag("a");
                String href = null;
                for (Element element2 : a) {
                    href = element2.attr("href");
                    if (!href.isBlank() && !href.isEmpty()) {
                        hrefs.add(href);
                        break;
                    }
                }
                // 缩略图地址
                Elements img = element1.getElementsByTag("img");
                for (Element imgElement : img) {
                    String src = imgElement.attr("data-src");
                    if (StrUtil.isNotBlank(src) && StrUtil.isNotBlank(href)) {
                        thumbnailsMapping.put(href, src);
                        break;
                    }
                }
            }
        }
        return hrefs;
    }

    /**
     * 解析不同标签中的缩略图路径
     *
     * @param className 标签的class属性名
     */
    public List<String> getTagThumbnail(String className) {
        List<String> hrefs = new ArrayList<>();
        Elements elements = document.getElementsByClass(className);
        for (Element element : elements) {
            Elements a = element.getElementsByTag("a");
            for (Element element1 : a) {
                String href = element1.attr("href");
                hrefs.add(href);
            }
        }
        return hrefs;
    }

    /**
     * 遍历缩略图路径 获取大图路径
     */
    public List<String> getPhotoPath(Document document) {
        List<String> list = new ArrayList<>();
        Elements scrollbox = document.getElementsByClass("scrollbox");
        for (Element element : scrollbox) {
            Elements img = element.getElementsByTag("img");
            for (Element path : img) {
                String src = path.attr("src");
                list.add(src);
            }
        }
        return list;
    }

    /**
     * 写入到本地文件
     */
    public void writePhoto(String path) throws Exception {
        if (StringUtil.isBlank(path)) {
            return;
        }
        long startTime = System.currentTimeMillis();
        // 打开url 流
        InputStream inputStream = new URL(path).openStream();
        try (FileOutputStream fileOutputStream = new FileOutputStream("D:\\Files\\" + UUID.randomUUID() + ".jpg")) {
            int temp;
            while ((temp = inputStream.read()) != -1)
                fileOutputStream.write(temp);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("写入完成 ！！！！");
        System.out.println("耗时为:" + (endTime - startTime) / 1000);
    }

    /**
     * 使用NIO写入本地文件
     *
     * @param path : 文件路径
     */
    public void writeNioPhoto(String path) throws Exception {
        if (StringUtil.isBlank(path)) {
            return;
        }
        long startTime = System.currentTimeMillis();
        InputStream inputStream = new URL(path).openStream();
        RandomAccessFile rw = new RandomAccessFile("D:\\Files\\" + UUID.randomUUID() + ".jpg", "rw");
        // 打开FileChannel
        FileChannel channel = rw.getChannel();

        byte[] readAllBytes = inputStream.readAllBytes();
        // 通过判断来确定开辟的区域是否大于当前图片的大小
        if (readAllBytes.length > buffer.capacity()) {
            buffer = ByteBuffer.allocate(readAllBytes.length);
        }
        buffer.put(readAllBytes);
        // 初始化缓冲区位置
        buffer.flip();
        // 写入操作
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }
        // 关闭流
        channel.close();
        inputStream.close();
        buffer.clear();
        long endTime = System.currentTimeMillis();
        log.info("写入完成 ！！！！");
        log.info("耗时为:" + (endTime - startTime) / 1000);
    }

    /**
     * 通过MinIO接口进行上传
     */
    public void writePhotoByMinIO(String path, String thumbnailsPath, List<ATag> tags) throws Exception {
        if (StringUtil.isBlank(path)) {
            return;
        }
        long startTime = System.currentTimeMillis();
        // 写入全部的标签
        aTagService.saveBatch(tags);
        List<Integer> tagIds = tags.stream().map(ATag::getId).collect(Collectors.toList());
        // 打开url 流
        URLConnection urlConnection = new URL(path).openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        // 会造成获取不到的情况
        URLConnection thumbnailsConnection = new URL(thumbnailsPath).openConnection();
        InputStream thumbnailsInputStream = thumbnailsConnection.getInputStream();
        String suffix = path.substring(path.lastIndexOf("."));
        String thumbnailsSuffix = path.substring(thumbnailsPath.lastIndexOf("."));
        // 使用流去写入文件
        AImage image =
            aImageService.uploadFileAndDb(inputStream, UUID.randomUUID() + suffix, urlConnection.getContentLength(),
                thumbnailsInputStream, thumbnailsSuffix, thumbnailsConnection.getContentLength());
        AImageTag aImageTag;

        for (Integer tagId : tagIds) {
            aImageTag = new AImageTag();
            aImageTag.setImageId(image.getId());
            aImageTag.setTadId(tagId);
            aImageTagService.save(aImageTag);
        }
        log.info("写入完成 ！！！！");
        if (ObjectUtil.isNotNull(inputStream)) {
            inputStream.close();
        }
        if (ObjectUtil.isNotNull(thumbnailsInputStream)) {
            thumbnailsInputStream.close();
        }
        log.info("耗时为:" + (System.currentTimeMillis() - startTime) / 1000);
    }
}
