package com.anzhen.jsoup;

import com.anzhen.service.AImageService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 这个只是针对首页
 */
@Component
@Slf4j
public class AdvanceImage {

    /**
     * 创建默认大小的缓冲区
     */
    static ByteBuffer buffer = ByteBuffer.allocate(1024);

    @Resource
    AImageService aImageService;

    /**
     * 封装请求头
     */
    static HashMap<String, String> headers = new HashMap<>();

    /**
     * 文档对象
     */
    static Document document;

    // 封装请求头
    static {
        headers.put("Referer", "https://wallhaven.cc/");
        headers.put("sec-ch-ua", "\"Google Chrome\";v=\"95\", \"Chromium\";v=\"95\", \";Not A Brand\";v=\"99\"");
        headers.put("sec-ch-ua-mobile", "?0");
        headers.put("sec-ch-ua-platform", "Windows");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36");
    }

    /**
     * 获取缩略图List
     *
     * @param path 网址路径
     * @notice 可以采用异步优化效率
     */
    public void getThumbnail(String path) throws Exception {
        document = Jsoup.connect(path).headers(headers).get();
        System.out.println("解析文件成功");
        // 获取首页标签的缩略图
        List<String> tagThumbnail = getIndexImage("thumb-listing-page");
        System.out.println("获取图片选择器成功");
        for (String s : tagThumbnail) {
            // 代码优化 使用异步写入文件
            List<String> photoPath = getPhotoPath(Jsoup.connect(s).headers(headers).get());
            System.out.println(photoPath);
            // 写入本地文件
            // 可以采用线程池
            for (String s1 : photoPath) {
                // 写入一张17mb的文件 堵塞IO使用时间为33s
//                writePhoto(s1);
                // NIO 使用时间为27s  超过 阻塞IO 6秒时间
//                writeNioPhoto(s1);
                writePhotoByMinIO(s1);
                break;
            }
            // 线程睡眠3秒  如果不暂停的话，会出现请求发送过多的  429
            Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            break;
        }
    }

    /**
     * 通过类选择器获取首页的照片粗略图路径
     *
     * @param className 类选择器名称
     */
    public List<String> getIndexImage(String className) {
        List<String> hrefs = new ArrayList<>();
        Elements elements = document.getElementsByClass(className);
        for (Element element : elements) {
            Elements li = element.getElementsByTag("li");
            for (Element element1 : li) {
                Elements a = element1.getElementsByTag("a");
                for (Element element2 : a) {
                    String href = element2.attr("href");
                    if (!href.isBlank() && !href.isEmpty())
                        hrefs.add(href);
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
     * 遍历缩略图路径   获取大图路径
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
        // 获取后缀
        // 直接将流写入本地文件
        // 之后将本地文件转换为流
        // 之后删除本地文件
        // 上传Minio
//        String suffix = path.substring(path.lastIndexOf("."));
//        String fileName = path.substring(path.lastIndexOf("/"));
//        // 使用流去写入文件
//        aImageService.uploadFileAndDb(inputStream, fileName + suffix);
        // 写入本地文件  todo 使用NIO进行操作提高效率
        // 解决流 存入MINIO中图片失效的问题
        // 第一种方案   可以使用先保存本地文件之后上传MINIO之后删除的方案   今天使用NIO解决复用问题
        // 第二种方案   直接使用流  解决MinIO用流上传文件损坏的问题
        try (FileOutputStream fileOutputStream =
                     new FileOutputStream("D:\\Files\\" + UUID.randomUUID() + ".jpg")) {
            int temp;
            while ((temp = inputStream.read()) != -1)
                fileOutputStream.write(temp);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("写入完成 ！！！！");
        System.out.println("耗时为:" + (endTime - startTime) / 1000);
    }

    /**
     * 使用NIO写入文件
     *
     * @param path
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
        System.out.println("写入完成 ！！！！");
        System.out.println("耗时为:" + (endTime - startTime) / 1000);
    }

    /**
     * 通过MinIO接口进行上传
     */
    public void writePhotoByMinIO(String path) throws Exception {
        if (StringUtil.isBlank(path)) {
            return;
        }
        // 打开url 流
        URLConnection urlConnection = new URL(path).openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        log.info("inputStream available length :", inputStream.available());
        String suffix = path.substring(path.lastIndexOf("."));
        String fileName = path.substring(path.lastIndexOf("/"));
        // 使用流去写入文件
        aImageService.uploadFileAndDb(inputStream, fileName + suffix, urlConnection.getContentLength());
    }
}
