package com.example.demo.Product;

import com.example.demo.Image;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Data
@Document
public class Product {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank
    private String name;

    @Min(value = 0, message = "Price can't lower than 0")
    private Double price;

    private String description;

    @Min(value = 0,  message = "Quantity can't lower than 0")
    private Integer quantity;

    private List<Image> images;

    private LocalDateTime created;

    public Product(String name, Double price, String description, Integer quantity, List<Image> images, LocalDateTime created) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
        this.images = images;
        this.created = created;
    }

}
