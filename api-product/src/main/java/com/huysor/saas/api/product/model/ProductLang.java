package com.huysor.saas.api.product.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductLang {
    private Long langId;
    private Long productId;
    private String name;
    private String description;
}
