package com.rabbit.dayfilm.item.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rabbit.dayfilm.common.CodeSet;
import com.rabbit.dayfilm.common.response.ResponseAbs;
import com.rabbit.dayfilm.item.dto.SelectAllItemsDto;
import com.rabbit.dayfilm.item.dto.SelectSearchItemsDto;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SelectSearchItemResponse extends ResponseAbs {
    private final List<SelectSearchItemsDto> data;

    public SelectSearchItemResponse(CodeSet codeSet, List<SelectSearchItemsDto> dto) {
        super(codeSet);
        this.data = dto;
    }
}
