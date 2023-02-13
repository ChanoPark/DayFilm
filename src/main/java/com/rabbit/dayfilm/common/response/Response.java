package com.rabbit.dayfilm.common.response;

import com.rabbit.dayfilm.common.CodeSet;

public interface Response {
    void setCode(CodeSet code);
    void setDetail(String detail);

    CodeSet getCode();
    String getMessage();
    String getDetail();
}
