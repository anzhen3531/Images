package com.anzhen.common.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/** inputStream 工具类 */
public class InputStreamUtils {

  /**
   * 缓存 : input stream
   *
   * @return
   */
  public static ByteArrayOutputStream cacheInputStream(InputStream inputStream) throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    byte[] buffer = new byte[1024];
    int len;
    while ((len = inputStream.read(buffer)) > -1) {
      outputStream.write(buffer, 0, len);
    }
    outputStream.flush();
    return outputStream;
  }
}
