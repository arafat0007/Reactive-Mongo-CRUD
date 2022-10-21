package com.example.ReactiveMongoCRUD.controllers;

import com.example.ReactiveMongoCRUD.domain.Product;
import com.example.ReactiveMongoCRUD.exceptions.ProductNotFoundException;
import com.example.ReactiveMongoCRUD.request.BadProductRequest;
import com.example.ReactiveMongoCRUD.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@WebFluxTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private ProductService productService;

    @Test
    void shouldGetProductById() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");

        when(productService.getProductById("1")).thenReturn(Mono.just(product1));

        webTestClient
                .get().uri("/product/1")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(product1.getId())
                .jsonPath("$.name").isEqualTo(product1.getName())
                .jsonPath("$.description").isEqualTo(product1.getDescription())
                .jsonPath("$.price").isEqualTo(product1.getPrice())
                .jsonPath("$.imageUrl").isEqualTo(product1.getImageUrl())
        ;
    }

    @Test
    void shouldNotGetProductByWrongId() {
        when(productService.getProductById("1")).thenThrow(new ProductNotFoundException(""));

        webTestClient
                .get().uri("/product/1")
                .exchange()
                .expectStatus()
                .isNotFound()
        ;
    }

    @Test
    void shouldGetAllProduct() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");
        Product product2 = new Product("2","Chair", "Nitori brand", 1000.50, "sampleURL2");

        when(productService.getProductList()).thenReturn(Flux.just(product1,product2));

        webTestClient
                .get().uri("/products")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(product1.getId())
                .jsonPath("$[0].name").isEqualTo(product1.getName())
                .jsonPath("$[0].description").isEqualTo(product1.getDescription())
                .jsonPath("$[0].price").isEqualTo(product1.getPrice())
                .jsonPath("$[0].imageUrl").isEqualTo(product1.getImageUrl())
                .jsonPath("$[1].id").isEqualTo(product2.getId())
                .jsonPath("$[1].name").isEqualTo(product2.getName())
                .jsonPath("$[1].description").isEqualTo(product2.getDescription())
                .jsonPath("$[1].price").isEqualTo(product2.getPrice())
                .jsonPath("$[1].imageUrl").isEqualTo(product2.getImageUrl())
        ;
    }

    @Test
    void shouldNotGetAnyProduct() {
        when(productService.getProductList()).thenReturn(Flux.empty());

        webTestClient
                .get().uri("/products")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(0)
        ;
    }

    @Test
    void shouldGetAllProductInRange() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");
        Product product2 = new Product("2","Chair", "Nitori brand", 1000.50, "sampleURL2");

        when(productService.getProductListInRange(1000,2001)).thenReturn(Flux.just(product1,product2));

        webTestClient
                .get().uri("/products?min=1000&max=2001")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(product1.getId())
                .jsonPath("$[0].name").isEqualTo(product1.getName())
                .jsonPath("$[0].description").isEqualTo(product1.getDescription())
                .jsonPath("$[0].price").isEqualTo(product1.getPrice())
                .jsonPath("$[0].imageUrl").isEqualTo(product1.getImageUrl())
                .jsonPath("$[1].id").isEqualTo(product2.getId())
                .jsonPath("$[1].name").isEqualTo(product2.getName())
                .jsonPath("$[1].description").isEqualTo(product2.getDescription())
                .jsonPath("$[1].price").isEqualTo(product2.getPrice())
                .jsonPath("$[1].imageUrl").isEqualTo(product2.getImageUrl())
        ;
    }

    @Test
    void shouldGetPartialProductInRange() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");
        Product product2 = new Product("2","Chair", "Nitori brand", 1000.50, "sampleURL2");
        Product product3 = new Product("3","Mouse", "Nitori brand", 5000.50, "sampleURL3");

        when(productService.getProductListInRange(1000,2001)).thenReturn(Flux.just(product1,product2));

        webTestClient
                .get().uri("/products?min=1000&max=2001")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(product1.getId())
                .jsonPath("$[0].name").isEqualTo(product1.getName())
                .jsonPath("$[0].description").isEqualTo(product1.getDescription())
                .jsonPath("$[0].price").isEqualTo(product1.getPrice())
                .jsonPath("$[0].imageUrl").isEqualTo(product1.getImageUrl())
                .jsonPath("$[1].id").isEqualTo(product2.getId())
                .jsonPath("$[1].name").isEqualTo(product2.getName())
                .jsonPath("$[1].description").isEqualTo(product2.getDescription())
                .jsonPath("$[1].price").isEqualTo(product2.getPrice())
                .jsonPath("$[1].imageUrl").isEqualTo(product2.getImageUrl())
        ;
    }

    @Test
    void shouldGetBadRequestForAllProductInRangePatternMin() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");
        Product product2 = new Product("2","Chair", "Nitori brand", 1000.50, "sampleURL2");

        when(productService.getProductListInRange(1000,2001)).thenReturn(Flux.just(product1,product2));

        webTestClient
                .get().uri("/products?min=asdasd&max=2001")
                .exchange()
                .expectStatus()
                .isBadRequest()
        ;
    }

    @Test
    void shouldGetBadRequestForAllProductInRangePatternMax() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");
        Product product2 = new Product("2","Chair", "Nitori brand", 1000.50, "sampleURL2");

        when(productService.getProductListInRange(1000,2001)).thenReturn(Flux.just(product1,product2));

        webTestClient
                .get().uri("/products?min=1000&max=asdasd")
                .exchange()
                .expectStatus()
                .isBadRequest()
        ;
    }

    @Test
    void shouldDeleteProductById() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");
        Product product2 = new Product("2","Chair", "Nitori brand", 1000.50, "sampleURL2");

        when(productService.deleteProductById("1")).thenReturn(Mono.empty());

        webTestClient
                .get().uri("/product/deleteAll")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().isEmpty()
        ;
    }

    @Test
    void shouldDeleteAllProduct() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");

        when(productService.deleteAllProduct()).thenReturn(Mono.empty());

        webTestClient
                .get().uri("/product/deleteAll")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody().isEmpty()
        ;
    }

    @Test
    void shouldCreateProduct() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");

        when(productService.saveProduct(product1)).thenReturn(Mono.just(product1));

        webTestClient
                .post().uri("/product/save")
                .bodyValue(product1)
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(product1.getId())
                .jsonPath("$.name").isEqualTo(product1.getName())
                .jsonPath("$.description").isEqualTo(product1.getDescription())
                .jsonPath("$.price").isEqualTo(product1.getPrice())
                .jsonPath("$.imageUrl").isEqualTo(product1.getImageUrl())
        ;
    }

    @Test
    void shouldNotCreateProductOnBadRequest() {
        Product product1 = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL1");

        when(productService.saveProduct(product1)).thenReturn(Mono.just(product1));

        webTestClient
                .post().uri("/product/save")
                .bodyValue(new BadProductRequest("1","Table", "Nitori brand", 2000.50))
                .exchange()
                .expectStatus()
                .isBadRequest()
        ;
    }
}