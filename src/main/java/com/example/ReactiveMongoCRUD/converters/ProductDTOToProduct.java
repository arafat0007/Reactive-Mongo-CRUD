package com.example.ReactiveMongoCRUD.converters;

import com.example.ReactiveMongoCRUD.domain.Product;
import com.example.ReactiveMongoCRUD.dtos.ProductDTO;
import lombok.Synchronized;
import org.springframework.beans.BeanUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ProductDTOToProduct implements Converter<ProductDTO, Product> {

//    public Mono<Product> convert(Mono<ProductDTO> source) {
//        return source.map(productDTO -> {
//            Product product = new Product();
//            if(productDTO.getId() != null && !productDTO.getId().isEmpty()){
//                BeanUtils.copyProperties(productDTO, product);
//            }
//            return product;
//        });
//    }

    @Synchronized
    @Nullable
    @Override
    public Product convert(ProductDTO productDTO) {
        Product product = new Product();
//        if(productDTO.getId() != null && !productDTO.getId().isEmpty()){
//            BeanUtils.copyProperties(productDTO, product);
//        }
        BeanUtils.copyProperties(productDTO, product);
        return product;
    }
}
