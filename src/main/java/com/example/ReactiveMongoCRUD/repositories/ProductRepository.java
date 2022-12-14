package com.example.ReactiveMongoCRUD.repositories;

import com.example.ReactiveMongoCRUD.domain.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product,String> {
}
