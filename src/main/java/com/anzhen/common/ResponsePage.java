package com.anzhen.common;

import lombok.Data;

import java.util.List;

/**
 * @author
 * @date 2022/5/13
 */
@Data
public class ResponsePage {

    private List<?> data;
    private Long total;
}
