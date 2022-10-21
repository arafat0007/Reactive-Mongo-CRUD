package com.example.ReactiveMongoCRUD.services;

import com.example.ReactiveMongoCRUD.domain.Product;
import com.example.ReactiveMongoCRUD.dtos.ProductDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface ProductService {
    Flux<Product> getProductList();

    Flux<Product> getProductListInRange(double min, double max);

    Mono<Product> getProductById(String id);

    Mono<Product> saveProduct(Product product);

    Mono<Void> deleteProductById(String id);

    Mono<Void> deleteAllProduct();
}
