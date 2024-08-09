package com.example.demo.Product;
import com.example.demo.Base.QueryRepository;
import com.example.demo.Base.UtilsRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String>, QueryRepository<Product>, UtilsRepository<Product> {
    @Query("{'name': { $regex: ?0, $options: 'i' }}")
    List<Product> findProductByName(String name);

}
