package com.rabbit.dayfilm.item.response;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.response.ResponseAbs;
import com.rabbit.dayfilm.item.dto.SelectProductsDto;
import lombok.Getter;

import java.util.List;

@Getter
public class SelectProductResponse extends ResponseAbs {
    private List<SelectProductsDto> data;

    public SelectProductResponse(CodeSet codeSet) {
        super(codeSet);
    }

    public SelectProductResponse(CodeSet codeSet, List<SelectProductsDto> data) {
        super(codeSet);
        this.data = data;
    }
}
