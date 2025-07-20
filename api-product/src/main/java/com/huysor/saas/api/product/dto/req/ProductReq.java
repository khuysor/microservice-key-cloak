package com.huysor.saas.api.product.dto.req;

import com.huysor.saas.api.product.model.ProductLang;
import com.huysor.saas.api.product.model.Products;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductReq(
        Long id,
        BigDecimal price,
        Long categoryId,
        Integer stockQuantity,
        String barcode,
        String sku,
        String seoUrl,
        Boolean status,
        String imageUrl,
        LocalDateTime availableDate,
        List<ProductLangReq> langs
) {
    public Products toEntityProduct() {
        Products product = new Products();
        product.setId(id);
        product.setPrice(price);
        product.setImage(imageUrl);
        product.setAvailableDate(availableDate);
        product.setQuantity( stockQuantity);
        product.setBarcode(barcode);
        product.setStatus(status);
        product.setSeoUrl(seoUrl().replaceAll(" ", "-").toLowerCase());
        product.setSku(sku());
        return product;
    }
    public record ProductLangReq(
            Long id,
            Long langId,
            Long productId,
            String name,
            String description
    ) {
        public ProductLang toEntity() {
            ProductLang productLang = new ProductLang();
            productLang.setLangId(langId);
            productLang.setProductId(productId);
            productLang.setName(name);
            productLang.setDescription(description);
            return productLang;
        }

    }

    public List<ProductLang> toLangEntities() {
        if (langs == null) return List.of();
        return langs.stream()
                .map(ProductLangReq::toEntity)
                .toList();
    }
}

