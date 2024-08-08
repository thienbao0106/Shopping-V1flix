package com.example.demo.Product;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@AllArgsConstructor
public class ProductController {
    @Autowired
    private final ProductService productService;

    @GetMapping("")
    public List<Product> fetchAllProducts(
            @RequestParam(required = false, name = "name") String name
    ) {
        if (name != null) return productService.findProductByName(name);
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product fetchProductById(@PathVariable("id") String id) {
        return productService.getProductById(id);
    }


    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product product) {
        try {
            if(product == null) throw new ServerException("Product can't be emptied");
            return productService.createProduct(product);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }
    }
}
