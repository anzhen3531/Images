package com.anzhen.common.utils;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/** inputStream 工具类 */
public class InputStreamCacher {

  private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(InputStreamCacher.class);

  /**
   * 将InputStream中的字节保存到ByteArrayOutputStream中。
   */
  private ByteArrayOutputStream byteArrayOutputStream = null;

  public InputStreamCacher(InputStream inputStream) {
    if (ObjectUtils.isNull(inputStream))
      return;

    byteArrayOutputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len;
    try {
      while ((len = inputStream.read(buffer)) > -1 ) {
        byteArrayOutputStream.write(buffer, 0, len);
      }
      byteArrayOutputStream.flush();
    } catch (IOException e) {
      logger.error(e.getMessage(), e);
    }
  }

  public InputStream getInputStream() {
    if (ObjectUtils.isNull(byteArrayOutputStream))
      return null;

    return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
  }
}
