package com.example.ReactiveMongoCRUD.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BadProductRequest {
    private String id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @DecimalMin("0.0")
    private double price;
}
