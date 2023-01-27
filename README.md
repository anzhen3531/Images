# 图片后端项目 

## 1、项目描述

一个图片网站，定时从 https://wallhaven.cc 中爬取图片，之后进行展示。 



## 2、使用技术 

| 所用技术名称             | 版本   |
| ------------------------ | ------ |
| `SpringBoot`             | 2.5.2  |
| `JDK`                    | 17     |
| `spring-security-oauth2` | 2.1.2  |
| `redis`                  |        |
| `jsoup`                  | 1.10.2 |
| `mybatis`                | 3.4.3  |
| `mysql`                  | 8      |
| `minio`                  |        |
| `httpclient`             |        |



## 3、 未完成功能

- 爬取图片的类型
- 完成图片类型搜索
- redis作为缓冲 减少数据库的查询次数 
- 增加分片上传提高上传速度

