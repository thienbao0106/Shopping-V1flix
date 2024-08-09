package com.example.demo.Base;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UtilsRepositoryImpl<T> implements UtilsRepository<T>{
    @SuppressWarnings("unchecked")
    public Map<String, T> convertItemToMap(T product) {
        Map<String, T> itemMap = new HashMap<>();
        if (product != null) {
            // Use reflection to get all fields
            Field[] fields = product.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true); // Allows access to private fields
                try {
                    Object value = field.get(product);
                    itemMap.put(field.getName(), (T) value);
                } catch (IllegalAccessException e) {
                    e.printStackTrace(); // Handle exception properly
                }
            }
        }
        return itemMap;
    }
}
