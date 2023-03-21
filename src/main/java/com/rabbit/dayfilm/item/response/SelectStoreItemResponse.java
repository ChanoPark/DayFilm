package com.rabbit.dayfilm.item.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.response.ResponseAbs;
import com.rabbit.dayfilm.item.dto.SelectStoreDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelectStoreItemResponse extends ResponseAbs {
    private List<SelectStoreDto> data;

    public SelectStoreItemResponse(CodeSet codeSet) {
        super(codeSet);
    }

    public SelectStoreItemResponse(CodeSet codeSet, List<SelectStoreDto> item) {
        super(codeSet);
        this.data = item;
    }
}
