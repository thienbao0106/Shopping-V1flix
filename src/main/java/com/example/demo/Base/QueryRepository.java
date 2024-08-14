package com.example.demo.Base;

import java.util.Map;

public interface QueryRepository<T> {
    T editCurrentObject(String id, Map<String, T> editedObject, Class<T> type);

}
