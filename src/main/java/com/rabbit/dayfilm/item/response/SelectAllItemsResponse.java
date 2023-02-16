package com.rabbit.dayfilm.item.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.response.ResponseAbs;
import com.rabbit.dayfilm.item.dto.SelectAllItemsDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelectAllItemsResponse extends ResponseAbs {
    private final Page<SelectAllItemsDto> data;

    public SelectAllItemsResponse(CodeSet codeSet, Page<SelectAllItemsDto> dto) {
        super(codeSet);
        this.data = dto;
    }
}
