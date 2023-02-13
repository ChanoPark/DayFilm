package com.rabbit.dayfilm.item.response;

import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.response.ResponseAbs;
import com.rabbit.dayfilm.item.dto.SelectAllItemsDto;

public class SelectAllItemsResponse extends ResponseAbs {
    private SelectAllItemsDto dto;

    public SelectAllItemsResponse(CodeSet codeSet) {
        super(codeSet);
    }

    public SelectAllItemsResponse(CodeSet codeSet, SelectAllItemsDto dto) {
        super(codeSet);
        this.dto = dto;
    }
}
