package com.example.demo.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String>, CustomProductRepository {
    List<Product> findProductByName(String name);
//    Boolean createProduct(Product product);
}
