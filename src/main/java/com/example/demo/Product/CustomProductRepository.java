package com.example.demo.Product;

import java.util.List;

public interface CustomProductRepository   {
    List<Product> findProductByName(String name);
}
