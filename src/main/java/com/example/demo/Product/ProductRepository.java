package com.example.demo.Product;
import com.example.demo.Base.QueryRepository;
import com.example.demo.Base.UtilsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<ProductModel, String>, QueryRepository<ProductModel>, UtilsRepository<ProductModel> {
    @Query("{'name': { $regex: ?0, $options: 'i' }}")
    Page<ProductModel> findProductByName(String name, Pageable pageable);

    @Query("{ 'id': ?0 }")
    @Update("{ '$set': { 'genre': null } }")
    void removeGenreInProduct(String productId);

}
