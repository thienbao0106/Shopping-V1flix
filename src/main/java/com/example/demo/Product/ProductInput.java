package com.example.demo.Product;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductInput extends BaseProduct{

    private String genreId;

    public ProductInput() {

    }
}
