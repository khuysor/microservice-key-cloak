package com.huysor.saas.api.product.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryLang {
    private Long id;
    private Long langId;
    private Long categoryId;
    private String name;
    private String description;
}
