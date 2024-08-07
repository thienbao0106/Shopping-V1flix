package com.example.demo.Product;

import com.example.demo.ResultNotification;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    public Product createProduct(Product product) throws ServerException {
        if(product.getName().isEmpty()) throw new ServerException("Product name can't be emptied");
        product.setImages(List.of());
        product.setCreated(LocalDateTime.now());
        return productRepository.save(product);
    }
}
