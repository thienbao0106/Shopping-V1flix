package com.example.demo.Product;

import com.example.demo.Genre.BaseGenre;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.stereotype.Component;


@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@Data
@Component
@Document
public class Product extends BaseProduct {


    @DocumentReference( collection = "genre")
    @JsonIgnoreProperties("products")
    private BaseGenre genre;

    public Product() {
        super();
    }

    public void convertInputToProduct(ProductInput productInput) {
        this.setName(productInput.getName());
        this.setImages(productInput.getImages());
        this.setCreated(productInput.getCreated());
        this.setName(productInput.getName());
        this.setQuantity(productInput.getQuantity());
        this.setDescription(productInput.getDescription());
        this.setName(productInput.getName());
    }

}
