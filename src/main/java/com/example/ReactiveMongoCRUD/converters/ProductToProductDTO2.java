package com.example.ReactiveMongoCRUD.converters;

import com.example.ReactiveMongoCRUD.domain.Product;
import com.example.ReactiveMongoCRUD.dtos.ProductDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class ProductToProductDTO2 {

    public Mono<ProductDTO> convert(Mono<Product> source) {
        return source.map(product -> {
            ProductDTO productDTO = new ProductDTO();
            if(product.getId() != null && !product.getId().isEmpty()){
                BeanUtils.copyProperties(product, productDTO);
        }
        return productDTO;
        });
    }
}
