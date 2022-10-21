package com.example.ReactiveMongoCRUD.services;

import com.example.ReactiveMongoCRUD.domain.Product;
import com.example.ReactiveMongoCRUD.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class ProductServiceImplTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void shouldGetProductList() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");
        Product product2 = new Product("2","Chair", "Nitori brand", 1000.50, "sampleURL2");

        Flux<Product> productList = Flux.just(product1,product2);

        when(productRepository.findAll()).thenReturn(productList);

        Flux<Product> productFlux = productService.getProductList();

        StepVerifier
                .create(productFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldNotGetAnyProduct() {
        when(productRepository.findAll()).thenReturn(Flux.empty());

        Flux<Product> productFlux = productService.getProductList();

        StepVerifier
                .create(productFlux)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldGetProductListInRange() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");
        Product product2 = new Product("2","Chair", "Nitori brand", 1000.50, "sampleURL2");

        Flux<Product> productList = Flux.just(product1,product2);

        when(productRepository.findAll()).thenReturn(productList);

        Flux<Product> productFlux = productService.getProductListInRange(1000,2100);

        StepVerifier
                .create(productFlux)
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void shouldNotGetProductListInWrongRange() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");
        Product product2 = new Product("2","Chair", "Nitori brand", 1000.50, "sampleURL2");

        Flux<Product> productList = Flux.just(product1,product2);

        when(productRepository.findAll()).thenReturn(productList);

        Flux<Product> productFlux = productService.getProductListInRange(0,1000);

        StepVerifier
                .create(productFlux)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldNotGetProductListInRangeOnEmptyDB() {
        when(productRepository.findAll()).thenReturn(Flux.empty());

        Flux<Product> productFlux = productService.getProductListInRange(0,1000);

        StepVerifier
                .create(productFlux)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldGetProductById() {
        Product product = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");

        when(productRepository.findById("1")).thenReturn(Mono.just(product));

        Mono<Product> productResult = productService.getProductById("1");

        StepVerifier
                .create(productResult)
                .consumeNextWith(p -> {
                    assertEquals(p.getId(), "1");
                    assertEquals(p.getName(),"Table");
                    assertEquals(p.getDescription(),"Nitori brand");
                    assertEquals(p.getPrice(),2000.50);
                    assertEquals(p.getImageUrl(),"sampleURL1");
                })
                .verifyComplete();
    }

    @Test
    void shouldNotGetProductByIdOnEmptyDB() {
        when(productRepository.findById("1")).thenReturn(Mono.empty());

        Mono<Product> productResult = productService.getProductById("1");

        StepVerifier
                .create(productResult)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldNotGetProductByIdOnWrongId() {
        Product product = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");

        when(productRepository.findById("2")).thenReturn(Mono.empty());

        Mono<Product> productResult = productService.getProductById("2");

        StepVerifier
                .create(productResult)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldSaveProduct() {
        Product product = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");

        when(productRepository.save(any())).thenReturn(Mono.just(product));

        Publisher<Product> find  = productService.saveProduct(product);

        StepVerifier
                .create(find)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    void shouldGetSameSaveProductData() {
        Product product = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");

        when(productRepository.save(any())).thenReturn(Mono.just(product));

        Publisher<Product> setup = productService.saveProduct(product);

        StepVerifier
                .create(setup)
                .consumeNextWith(p -> {
                    assertEquals(p.getId(), "1");
                    assertEquals(p.getName(),"Table");
                    assertEquals(p.getDescription(),"Nitori brand");
                    assertEquals(p.getPrice(),2000.50);
                    assertEquals(p.getImageUrl(),"sampleURL1");
                })
                .verifyComplete();
    }

    @Test
    void shouldDeleteProductById() {
        Product product = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");

        when(productRepository.deleteById("1")).thenReturn(Mono.empty());

        Mono<Void> setup = productService.deleteProductById("1");

        StepVerifier
                .create(setup)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void shouldDeleteAllProduct() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");
        Product product2 = new Product("2","Chair", "Nitori brand", 1000.50, "sampleURL2");

        when(productRepository.deleteAll()).thenReturn(Mono.empty());

        Mono<Void> setup = productService.deleteAllProduct();

        StepVerifier
                .create(setup)
                .expectNextCount(0)
                .verifyComplete();
    }
}