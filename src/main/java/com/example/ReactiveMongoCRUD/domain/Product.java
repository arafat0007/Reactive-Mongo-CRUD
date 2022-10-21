package com.example.ReactiveMongoCRUD.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Document(collection = "Products")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    @Id
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @DecimalMin("0.0")
    private double price;
    @NotBlank
    private String imageUrl;
}
