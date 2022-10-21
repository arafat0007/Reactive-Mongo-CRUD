package com.example.ReactiveMongoCRUD.services;

import com.example.ReactiveMongoCRUD.converters.ProductDTOToProduct;
import com.example.ReactiveMongoCRUD.converters.ProductToProductDTO;
import com.example.ReactiveMongoCRUD.domain.Product;
import com.example.ReactiveMongoCRUD.dtos.ProductDTO;
import com.example.ReactiveMongoCRUD.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Flux<Product> getProductList() {
        return productRepository.findAll();
    }

    @Override
    public Flux<Product> getProductListInRange(double min, double max) {
        return productRepository.findAll().filter(product -> product.getPrice()>=min && product.getPrice()<=max);
    }

    @Override
    public Mono<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Mono<Product> saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Mono<Void> deleteProductById(String id) {
        return productRepository.deleteById(id);
    }

    @Override
    public Mono<Void> deleteAllProduct() {
        return productRepository.deleteAll();
    }
}
