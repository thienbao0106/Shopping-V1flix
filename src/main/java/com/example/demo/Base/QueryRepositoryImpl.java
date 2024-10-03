package com.example.demo.Base;

import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryRepositoryImpl<T> implements QueryRepository<T> {
    private final MongoTemplate mongoTemplate;

    public QueryRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public T editCurrentObject(String id, Map<String, T> editedObject, Class<T> type) {
        Query query = new Query(Criteria.where("id").is(id));
        // Create an Update object
        Update update = new Update();

        // Dynamically add non-null fields to the Update object
        editedObject.forEach((key, value) -> {
            if (value == null) return;
            if (value instanceof Number && (((Number) value).doubleValue() == 0)) return;
            if (value instanceof List<?> && ((List<?>) value).isEmpty()) return;
            update.set(key, value);
        });

        return mongoTemplate.findAndModify(query, update, new FindAndModifyOptions().returnNew(true), type);
    }
}
