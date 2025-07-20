package com.huysor.saas.api.product.service.implement;

import com.huysor.saas.api.product.dto.req.ProductReq;
import com.huysor.saas.api.product.mapper.ProductMapper;
import com.huysor.saas.api.product.model.ProductLang;
import com.huysor.saas.api.product.model.Products;
import com.huysor.saas.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductMapper productMapper;

    @Override
    public ResponseEntity<?> createProduct(ProductReq req) {
        Products products = req.toEntityProduct();
        productMapper.createProduct(products);
        if (req.toLangEntities() != null && !req.toLangEntities().isEmpty()) {
            List<ProductLang> lang = req.toLangEntities();
            productMapper.insertProductLang(lang, products.getId());
        }
        return ResponseEntity.ok("Product created successfully");
    }

    @Override
    public ResponseEntity<?> listProduct() {
        return ResponseEntity.ok(productMapper.listProduct());
    }
}
