package com.anzhen.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 这个只是针对首页
 */
public class AdvanceImage {

    static HashMap<String, String> headers = new HashMap<>();
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
     * 网站路径
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        getThumbnail("https://wallhaven.cc/");
        for (int i = 10; i < 15; i++) {
            getThumbnail("https://wallhaven.cc/hot?page=" + i);
        }
    }

    /**
     * 获取缩略图List
     *
     * @param path 网址路径
     */
    public static void getThumbnail(String path) throws Exception {
        document = Jsoup.connect(path).headers(headers).get();
        // 获取首页标签的缩略图
//        List<String> tagThumbnail = getTagThumbnail("more-feat");
        List<String> tagThumbnail = getIndexImage("thumb-listing-page");
        System.out.println("粗略图的数量" + tagThumbnail.size());
        for (String s : tagThumbnail) {
            // 代码优化 使用异步写入文件
            List<String> photoPath = getPhotoPath(Jsoup.connect(s).headers(headers).get());
            System.out.println(photoPath);
            for (String s1 : photoPath) {
                writePhoto(s1);
            }
            // 线程睡眠3秒  如果不暂停的话，会出现请求发送过多的  429
            Thread.sleep(TimeUnit.SECONDS.toMillis(3));
        }
    }


    /**
     * 通过类选择器获取首页的照片粗略图路径
     *
     * @param className 类选择器名称
     * @return
     */
    public static List<String> getIndexImage(String className) {
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
     * @return
     */
    public static List<String> getTagThumbnail(String className) {
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
    public static List<String> getPhotoPath(Document document) {
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
    public static void writePhoto(String path) throws Exception {
        URL url = new URL(path);
        URLConnection urlConnection = url.openConnection();
        InputStream inputStream = urlConnection.getInputStream();
        // 写入本地文件
        FileOutputStream fileOutputStream =
                new FileOutputStream("D:\\Files\\pachong\\" + UUID.randomUUID().toString() + ".jpg");
        int temp;
        while ((temp = inputStream.read()) != -1)
            fileOutputStream.write(temp);
        System.out.println("图片写入成功");
    }
}
