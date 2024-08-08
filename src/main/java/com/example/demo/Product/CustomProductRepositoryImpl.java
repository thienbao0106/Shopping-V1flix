package com.example.demo.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;


import java.util.List;

public class CustomProductRepositoryImpl implements CustomProductRepository {
    @Autowired
    private final MongoTemplate mongoTemplate;

    public CustomProductRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<Product> findProductByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(name));
        System.out.println(mongoTemplate.find(query, Product.class).size());
        return mongoTemplate.find(query, Product.class);
    }
}
