package com.example.demo.Product;
import com.example.demo.Image;
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
    private String name;
    private Double price;
    private String description;
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
