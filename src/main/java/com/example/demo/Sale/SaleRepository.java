package com.example.demo.Sale;

import com.example.demo.Base.QueryRepository;
import com.example.demo.Base.UtilsRepository;
import com.example.demo.Product.ProductModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleRepository extends MongoRepository<SaleModel, String>, QueryRepository<SaleModel>, UtilsRepository<SaleModel> {
    @Query("{ 'id': ?0 }")
    @Update("{ '$addToSet': { 'products': ?1 } }")
    void insertProduct(String id, ObjectId productId);

    @Query("{ 'id': ?0 }")
    @Update("{ '$pull': { 'products': ?1 } }")
    void removeProduct(String genreId, ProductModel productModel);
}
