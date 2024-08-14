package com.example.demo.Genre;

import com.example.demo.Base.QueryRepository;

import com.example.demo.Product.Product;
import com.example.demo.Product.ProductInput;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;


public interface GenreRepository extends MongoRepository<Genre, String>, QueryRepository<Genre> {
    @Query("{ 'id': ?0 }")
    @Update("{ '$addToSet': { 'products': ?1 } }")
    void insertProduct(String id, Product product);

    @Query("{ 'id': ?0 }")
    @Update("{ '$pull': { 'products': ?1 } }")
    void removeProduct(String genreId, Product product);
}
