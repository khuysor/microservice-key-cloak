package com.huysor.saas.api.product.dto.resp;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProductRes {
    Long id;
    BigDecimal price;
    Long categoryId;
    Integer stockQuantity;
    String barcode;
    String sku;
    String seoUrl;
    Boolean status;
    String imageUrl;
    LocalDateTime availableDate;
    List<ProductLangRes> langs;

    @Getter
    @Setter
    static class ProductLangRes {
        Long id;
        Long langId;
        Long productId;
        String name;
        String description;

    }
}
