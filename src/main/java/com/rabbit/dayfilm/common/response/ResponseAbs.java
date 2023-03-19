package com.rabbit.dayfilm.common.response;

import com.rabbit.dayfilm.common.CodeSet;
import lombok.Getter;
import lombok.Setter;

public abstract class ResponseAbs implements Response{
    private CodeSet code;
    private String message;
    private String detail;

    public ResponseAbs(CodeSet codeSet) {
        this.code = codeSet;
        this.detail = codeSet.getMessage();
    }


    @Override
    public void setCode(CodeSet code) {
        this.code = code;
        this.message = code.getMessage();
    }

    @Override
    public void setDetail(String detail) {
        this.detail = detail;
    }


    @Override
    public CodeSet getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public String getDetail() {
        return this.detail;
    }

}
