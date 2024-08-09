package com.example.demo.Base;

import java.util.Map;

public interface UtilsRepository<T> {
    Map<String, T> convertItemToMap(T item);
}
