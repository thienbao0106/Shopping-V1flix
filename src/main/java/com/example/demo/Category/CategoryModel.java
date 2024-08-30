package com.example.demo.Category;

import com.example.demo.Product.BaseProduct;
import com.example.demo.Product.ProductDTO;
import com.example.demo.Product.ProductModel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
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

    private List<ProductModel> products;
    public void convertDTOToCategory(CategoryDTO categoryDTO) {
       this.setName(categoryDTO.getName());
       this.setImage(categoryDTO.getImage());
       this.setProducts(List.of());
    }
}
