package com.huysor.saas.api.product.mapper;

import com.huysor.saas.api.product.dto.resp.ProductRes;
import com.huysor.saas.api.product.model.ProductLang;
import com.huysor.saas.api.product.model.Products;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProductMapper {
    void createProduct(@Param("param") Products products);
    void insertProductLang(@Param("param") List<ProductLang> lang,@Param("product_id") Long productId);
    List<ProductRes> listProduct();

}
