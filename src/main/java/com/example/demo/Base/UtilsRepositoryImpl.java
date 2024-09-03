package com.example.demo.Base;

import com.cloudinary.*;

import com.cloudinary.utils.*;
import com.example.demo.CustomCloudinary;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UtilsRepositoryImpl<T> implements UtilsRepository<T> {


    @SuppressWarnings("unchecked")
    public Map<String, T> convertItemToMap(T item, String className)  {
        try {
            Map<String, T> itemMap = new HashMap<>();
            Class<?> itemClass = Class.forName(className);
            while(itemClass != null) {
                if (item != null) {
                    // Use reflection to get all fields
                    Field[] fields = itemClass.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true); // Allows access to private fields
                        try {
                            Object value = field.get(item);
                            itemMap.put(field.getName(), (T) value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace(); // Handle exception properly
                        }
                    }
                }
                itemClass = itemClass.getSuperclass();
            }

            return itemMap;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        // Create a temporary file
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        // Transfer content from MultipartFile to the file
        multipartFile.transferTo(file);

        return file;
    }

    private String getFileExtension(MultipartFile multipartFile) {
        // Extract file extension from MultipartFile original filename
        String originalFilename = multipartFile.getOriginalFilename();
        return originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".") + 1) : "";
    }

    public Map<?, ?> uploadImageToCloudinary(MultipartFile imageFile, String productName, String imageType) {
        CustomCloudinary customCloudinary = new CustomCloudinary();
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", CustomCloudinary.cloudName,
                "image_upload", CustomCloudinary.imageUpload,
                "api_key", CustomCloudinary.apiKey,
                "api_secret", CustomCloudinary.apiSecret,
                "secure", customCloudinary));
        System.out.println(CustomCloudinary.imageUpload);
        Map<?, ?> obj = null;
        try {
            File image = convertMultipartFileToFile(imageFile);
            obj = cloudinary.uploader().upload(image, ObjectUtils.asMap("resource_type", "image",
                    "public_id", "shopping/" + productName + "/" + productName + "_" + imageType));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return obj;
    }
}
