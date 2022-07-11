package com.anzhen.common.result;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 分页响应结构体
 *
 * @date 2022/2/18 23:29
 */
@Data
public class ApiPageResult<T> implements Serializable {

  private String code;

  private Data data;

  private String msg;

  public static <T> ApiPageResult<T> success(IPage<T> page) {
    ApiPageResult<T> result = new ApiPageResult<>();
    result.setCode(ApiResultCode.SUCCESS.getCode());

    Data data = new Data<T>();
    data.setList(page.getRecords());
    data.setTotal(page.getTotal());

    result.setData(data);
    result.setMsg(ApiResultCode.SUCCESS.getMsg());
    return result;
  }

  @lombok.Data
  public static class Data<T> {

    private List<T> list;

    private long total;
  }
}
