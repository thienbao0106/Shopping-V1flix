package com.example.demo.Base;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Map;

public interface UtilsRepository<T> {
    Map<String, T> convertItemToMap(T item, String className);
    Map<?, ?> uploadImageToCloudinary(MultipartFile imageFile, String productName, String imageType);
}
