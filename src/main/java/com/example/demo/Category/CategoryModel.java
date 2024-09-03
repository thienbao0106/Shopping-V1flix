package com.example.demo.Category;

import com.example.demo.Product.BaseProduct;
import com.example.demo.Product.ProductDTO;
import com.example.demo.Product.ProductModel;
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
@Component
@Data
@Document(collection = "categories")
public class CategoryModel extends BaseCategory {
    @Id
    private String id;
    public CategoryModel() {
        super();
    }
    @DocumentReference(collection = "products")
    @JsonIgnoreProperties("category")
    private List<ProductModel> products;
    public void convertDTOToCategory(CategoryDTO categoryDTO) {
       this.setName(categoryDTO.getName());
       this.setImage(categoryDTO.getImage());
       this.setProducts(List.of());
    }
}
