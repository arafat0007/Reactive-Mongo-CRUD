package com.example.ReactiveMongoCRUD.repositories;

import com.example.ReactiveMongoCRUD.domain.Product;
import com.example.ReactiveMongoCRUD.dtos.ProductDTO;
import lombok.Builder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(properties = "spring.mongodb.embedded.version=3.5.5")
class ProductRepositoryTest {
    @Autowired
    ProductRepository productRepository;

    @Test
    public void shouldSaveProduct(){
        Product product = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL");

        Publisher<Product> setup = productRepository.deleteAll().then(productRepository.save(product));

        StepVerifier
                .create(setup)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void shouldGetSameSavedProductData(){
        Product product = new Product("1","Table", "Nitori brand", 2000.50, "sampleURL");

        Publisher<Product> setup = productRepository.deleteAll().then(productRepository.save(product));

        Mono<Product> find = productRepository.findById("1");

        Publisher<Product> composite = Mono
                .from(setup)
                .then(find);

        StepVerifier
                .create(composite)
                .consumeNextWith(p -> {
                    assertNotNull(p.getId());
                    assertEquals(p.getName(), "Table");
                    assertEquals(p.getDescription(), "Nitori brand");
                    assertEquals(p.getPrice(), 2000.50);
                    assertEquals(p.getImageUrl(), "sampleURL");
                })
                .verifyComplete();
    }
}