package com.example.demo.Category;

import com.example.demo.Base.QueryRepository;

import com.example.demo.Base.UtilsRepository;
import com.example.demo.Product.ProductModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;


public interface CategoryRepository extends MongoRepository<CategoryModel, String>, QueryRepository<CategoryModel>, UtilsRepository<CategoryModel> {
    @Query("{ 'id': ?0 }")
    @Update("{ '$addToSet': { 'products': ?1 } }")
    void insertProduct(String id, ProductModel productModel);

    @Query("{ 'id': ?0 }")
    @Update("{ '$pull': { 'products': ?1 } }")
    void removeProduct(String genreId, ProductModel productModel);
}
