package com.rabbit.dayfilm.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rabbit.dayfilm.common.CodeSet;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SuccessResponse extends ResponseAbs{

    public SuccessResponse(CodeSet codeSet) {
        super(codeSet);
    }
}
