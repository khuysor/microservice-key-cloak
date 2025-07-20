package com.huysor.saas.api.product.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class Products {
    private Long id;
    private Integer quantity;
    private String sku;
    private String barcode;
    private String image;
    private String seoUrl;
    private Boolean status;
    private BigDecimal price;
    private LocalDateTime availableDate;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
