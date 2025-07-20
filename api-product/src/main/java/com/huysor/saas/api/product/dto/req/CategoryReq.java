package com.huysor.saas.api.product.dto.req;

public record CategoryReq(
        String image,
        String seoUrl,
        Boolean status,
        Long parentId
) {
}
