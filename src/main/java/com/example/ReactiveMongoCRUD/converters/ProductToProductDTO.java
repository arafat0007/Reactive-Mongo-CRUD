package com.example.ReactiveMongoCRUD.converters;

import com.example.ReactiveMongoCRUD.domain.Product;
import com.example.ReactiveMongoCRUD.dtos.ProductDTO;
import lombok.Synchronized;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ProductToProductDTO implements Converter<Product, ProductDTO> {

//    public Mono<ProductDTO> convert(Mono<Product> source) {
//        return source.map(product -> {
//            ProductDTO productDTO = new ProductDTO();
//            if(product.getId() != null && !product.getId().isEmpty()){
//                BeanUtils.copyProperties(product, productDTO);
//        }
//        return productDTO;
//        });
//    }

    @Synchronized
    @Nullable
    @Override
    public ProductDTO convert(Product product) {
        ProductDTO productDTO = new ProductDTO();
//        if(product.getId() != null && !product.getId().isEmpty()){
//            BeanUtils.copyProperties(product, productDTO);
//        }
        BeanUtils.copyProperties(product, productDTO);
        return productDTO;
    }
}
