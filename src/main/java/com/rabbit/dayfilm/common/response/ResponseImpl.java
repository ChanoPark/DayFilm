package com.rabbit.dayfilm.common.response;

import com.rabbit.dayfilm.common.CodeSet;

public class ResponseImpl extends ResponseAbs{

    public ResponseImpl(CodeSet codeSet) {
        super(codeSet);
    }

    public ResponseImpl(CodeSet codeSet, Object jsonObject) {
        super(codeSet);
        super.setValue(jsonObject);
    }
}
