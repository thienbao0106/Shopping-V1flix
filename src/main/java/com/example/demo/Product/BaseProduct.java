package com.example.demo.Product;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

@Setter
@Getter
public class BaseProduct {


    @Indexed(unique = true)
    @NotBlank
    private String name;


    @Min(value = 0, message = "Price can't lower than 0")
    private Integer price;

    private String description;

    @Min(value = 0, message = "Quantity can't lower than 0")
    private Integer quantity;



    private LocalDateTime created;
}
