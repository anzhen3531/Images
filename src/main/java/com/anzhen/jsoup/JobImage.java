package com.anzhen.jsoup;

import com.anzhen.service.FileUploadService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.net.URL;

@Component
public class JobImage {

    @Resource
    AdvanceImage advanceImage;
    @Resource
    FileUploadService fileUploadService;

    public void execute() throws Exception {
//        for (int i = 15; i < 30; i++) {
        // 遍历
//        advanceImage.getThumbnail("https://wallhaven.cc/hot?page=" + 1);
//        }
        InputStream inputStream = new URL("https://w.wallhaven.cc/full/rd/wallhaven-rdeylw.jpg").openStream();
        fileUploadService.fileUpload("image", "wallhaven-rdeylw.jpg", inputStream);
    }
}
