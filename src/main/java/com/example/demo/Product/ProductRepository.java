package com.example.demo.Product;
import com.example.demo.Base.QueryRepository;
import com.example.demo.Base.UtilsRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>, QueryRepository<Product>, UtilsRepository<Product> {
    @Query("{'name': { $regex: ?0, $options: 'i' }}")
    List<Product> findProductByName(String name);

    @Query("{ 'id': ?0 }")
    @Update("{ '$set': { 'genre': null } }")
    void removeGenreInProduct(String productId);

}
