package com.example.demo.Product;

import com.example.demo.Genre.BaseGenre;
import com.example.demo.Genre.Genre;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@Data
@Document
public class Product extends BaseProduct {



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
