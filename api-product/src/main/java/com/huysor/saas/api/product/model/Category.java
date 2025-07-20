package com.huysor.saas.api.product.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Category {
    private Long id;
    private String image;
    private String seoUrl;
    private Boolean status;
    private Long parentId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
