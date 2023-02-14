package com.rabbit.dayfilm.item.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.response.ResponseAbs;
import com.rabbit.dayfilm.item.dto.SelectDetailImageDto;
import com.rabbit.dayfilm.item.dto.SelectDetailItemDto;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelectDetailItemResponse extends ResponseAbs {
    private SelectDetailItemDto item;
    private List<SelectDetailImageDto> image;

    public SelectDetailItemResponse(CodeSet codeSet) {
        super(codeSet);
    }

    public SelectDetailItemResponse(CodeSet codeSet, SelectDetailItemDto item, List<SelectDetailImageDto> image) {
        super(codeSet);
        this.item = item;
        this.image = image;
    }
}
