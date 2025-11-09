package com.atrastones.shop.api.create;

import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Value
public class ProductCreate {

    String name;
    BigDecimal price;
    Long categoryId;
    String description;
    List<MultipartFile> media;

}
