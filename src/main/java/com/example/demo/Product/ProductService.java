package com.example.demo.Product;

import com.example.demo.RecordNotFound;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        if (product.getImages() == null) {
            product.setImages(List.of());
        }
        product.setCreated(LocalDateTime.now());
        return productRepository.save(product);
    }

    public Product getProductById(String id) {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok)
                .orElseThrow(() -> new RecordNotFound("Can't find the product of id: " + id)).getBody();
    }

    public Product editProduct(Product editedProduct, String id) {
        if (productRepository.findById(id).isEmpty()) throw new RecordNotFound("Can't find the product id" + id);
        return productRepository.editCurrentObject(id, productRepository.convertItemToMap(editedProduct), Product.class);
    }

    public void deleteProduct(String id) {
        if (productRepository.findById(id).isEmpty()) throw new RecordNotFound("Can't find the product id" + id);
        productRepository.deleteById(id);
    }
}
