package com.anzhen.jsoup;

import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class JobImage {

    @Resource
    AdvanceImage advanceImage;

    public void execute() throws Exception {
//        for (int i = 15; i < 30; i++) {
        // 遍历
        advanceImage.getThumbnail("https://wallhaven.cc/hot?page=" + 1);
//        }
    }
}
