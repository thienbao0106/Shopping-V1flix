package com.example.demo.Product;

import com.example.demo.Image;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class BaseProduct {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank
    private String name;

    @Min(value = 0, message = "Price can't lower than 0")
    private Double price;

    private String description;

    @Min(value = 0, message = "Quantity can't lower than 0")
    private Integer quantity;



    private LocalDateTime created;
}
