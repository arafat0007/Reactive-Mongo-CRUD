package com.example.ReactiveMongoCRUD.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
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
