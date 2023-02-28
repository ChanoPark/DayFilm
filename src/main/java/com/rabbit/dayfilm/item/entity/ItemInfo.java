package com.rabbit.dayfilm.item.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

@Getter
@Document(indexName = "items")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemInfo {
    @Id
    private Long id;

//    @Field(type = FieldType.Keyword)
//    private List<String> tags;
    @Field(type = FieldType.Text, analyzer = "standard")
    private String storeName;

    @Field(type = FieldType.Text, analyzer = "standard")
    private String title;

    @Transient
    private Method method;

    @Transient
    private Category category;

    @Transient
    private Integer pricePerOne;

    @Transient
    private String imagePath;
}
