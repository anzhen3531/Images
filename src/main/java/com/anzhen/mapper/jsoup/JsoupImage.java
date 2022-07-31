package com.anzhen.mapper.jsoup;

import org.jsoup.Connection;
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
import java.util.stream.Collectors;

/** json 爬虫学习 */
public class JsoupImage {
  public static void main(String[] args) throws Exception {
    //        jsoupImage2("https://wallhaven.cc/");
    jsoupImage("https://wallhaven.cc/");
  }

  // 案例二
  public static void jsoupImage2(String path) throws Exception {
    Connection connect = Jsoup.connect(path);
    // 添加请求头
    HashMap<String, String> headers = new HashMap<>();
    headers.put(
        "User-Agent",
        "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.69 Safari/537.36");
    Document document = connect.headers(headers).get();
    System.out.println(document);
  }

  // 反爬虫伪装请求头

  // 案例一

  /**
   * 使用爬虫进行爬取
   *
   * @param path 网站路径
   */
  public static void jsoupImage(String path) throws Exception {
    // 解析路径
    Document parse = Jsoup.parse(new URL(path), 10000);
    //  wallpaperBg 图片所在的class
    Elements wallpaperBg = parse.getElementsByClass("wallpaperList");
    List<Elements> elements = new ArrayList<>();
    for (Element element : wallpaperBg) {
      // 图片存储dev
      Elements wallpaperBgImage = element.getElementsByClass("wallpaperBgImage");
      for (Element e : wallpaperBgImage) {
        Elements img = e.getElementsByTag("img");
        elements.add(img);
      }
    }
    // 取值属性
    List<String> imagePath = new ArrayList<>();
    for (Elements imgs : elements) {
      for (Element img : imgs) {
        imagePath.add(img.attr("src"));
      }
    }
    imagePath = imagePath.stream().map(s -> path + s).collect(Collectors.toList());

    int id = 1;
    // todo 使用NIO进行改造 提高IO效率
    for (String s : imagePath) {
      URL url = new URL(s);
      URLConnection urlConnection = url.openConnection();
      InputStream inputStream = urlConnection.getInputStream();
      // 写入本地文件
      // 直接写入到 minio中  将这个爬虫改为定时任务  每天晚上准时拉去图片 todo
      FileOutputStream fileOutputStream =
          new FileOutputStream("D:\\Files\\pachong\\" + id + ".jpg");
      id++;
      int temp;
      while ((temp = inputStream.read()) != -1) fileOutputStream.write(temp);
      System.out.println("图片写入成功");
      inputStream.close();
      fileOutputStream.close();
    }
  }
}
