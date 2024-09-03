package com.example.demo.Product;

import com.example.demo.Category.CategoryModel;
import com.example.demo.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;

import java.util.List;


@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@Data
@Component
@Document(collection = "products")
public class ProductModel extends BaseProduct {

    @Id
    private String id;
    private List<Image> images;

    @DocumentReference(collection = "categories")
    @JsonIgnoreProperties("products")
    private CategoryModel category;

    public ProductModel() {
        super();
    }

    public void convertDTOToProduct(ProductDTO productDTO) {
        this.setName(productDTO.getName());
        this.setCreated(productDTO.getCreated());
        this.setQuantity(productDTO.getQuantity());
        this.setDescription(productDTO.getDescription());
        this.setPrice(productDTO.getPrice());


    }

}
