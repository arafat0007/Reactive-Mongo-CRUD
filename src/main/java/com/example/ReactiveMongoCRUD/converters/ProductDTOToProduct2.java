package com.example.ReactiveMongoCRUD.converters;

import com.example.ReactiveMongoCRUD.domain.Product;
import com.example.ReactiveMongoCRUD.dtos.ProductDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProductDTOToProduct2 {

    public Mono<Product> convert(Mono<ProductDTO> source) {
        return source.map(productDTO -> {
            Product product = new Product();
            if(productDTO.getId() != null && !productDTO.getId().isEmpty()){
                BeanUtils.copyProperties(productDTO, product);
            }
            return product;
        });
    }
}
