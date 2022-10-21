package com.example.ReactiveMongoCRUD.controllers;

import com.example.ReactiveMongoCRUD.converters.ProductDTOToProduct;
import com.example.ReactiveMongoCRUD.converters.ProductToProductDTO;
import com.example.ReactiveMongoCRUD.domain.Product;
import com.example.ReactiveMongoCRUD.dtos.ProductDTO;
import com.example.ReactiveMongoCRUD.exceptions.ProductNotFoundException;
import com.example.ReactiveMongoCRUD.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@Slf4j
@RestController
public class ProductController {
    private final ProductService productService;
    private final ProductDTOToProduct productDTOToProduct;
    private final ProductToProductDTO productToProductDTO;

    public ProductController(ProductService productService, ProductDTOToProduct productDTOToProduct, ProductToProductDTO productToProductDTO) {
        this.productService = productService;
        this.productDTOToProduct = productDTOToProduct;
        this.productToProductDTO = productToProductDTO;
    }

    @GetMapping("/product/{id}")
    public Mono<ProductDTO> getProduct(@PathVariable String id){
        return productService.getProductById(id)
                .switchIfEmpty(Mono.error(new ProductNotFoundException("product is not found.")))
                .mapNotNull(productToProductDTO::convert);

    }

    @GetMapping("/products")
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductDTO> getAllProduct(){
        return productService.getProductList().mapNotNull(productToProductDTO::convert);
    }

    @GetMapping(value = "/products", params = {"min", "max"})
    @ResponseStatus(HttpStatus.OK)
    public Flux<ProductDTO> getAllProductInRange(@Valid @RequestParam double min, @Valid @RequestParam double max){
        return productService.getProductListInRange(min, max).mapNotNull(productToProductDTO::convert);
    }

    @GetMapping("/product/delete/{id}")
    public Mono<Void> deleteProductById(@PathVariable String id){
//        return productService.getProductById(id)
//                .flatMap(product -> {
//                    return productService.deleteProductById(id).then(Mono.just(new ResponseEntity<Void>(HttpStatus.OK)));
//                })
//                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

//        var a = productService.getProductById(id)
//                .switchIfEmpty(Mono.error(new ProductNotFoundException("product is not found.")))
//                //.thenEmpty(productService.deleteProductById(id));
//                .
//                ;
//
//                return a;

        return productService.deleteProductById(id);
    }

    @GetMapping("/product/deleteAll")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Void> deleteAllProduct(){
        return productService.deleteAllProduct();
    }

    @PostMapping("/product/save")
    public Mono<ResponseEntity<ProductDTO>> createProduct(@Valid @RequestBody ProductDTO productDTO){
        return productService.saveProduct(productDTOToProduct.convert(productDTO))
                .map(product -> {
                    ProductDTO resultProduct = productToProductDTO.convert(product);
                    return new ResponseEntity<>(resultProduct, HttpStatus.CREATED);
                })
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }
}
