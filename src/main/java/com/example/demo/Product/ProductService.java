package com.example.demo.Product;

import com.example.demo.Genre.BaseGenre;
import com.example.demo.Genre.Genre;
import com.example.demo.Genre.GenreRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final GenreRepository genreRepository;

    private void setGenreToProduct(String genreId, Product product) {
        if (genreId == null || genreId.isEmpty()) return;
        Optional<Genre> genre = genreRepository.findById(genreId);
        genre.ifPresentOrElse((result) -> {
            BaseGenre baseGenre = result.covertToBaseGenre();
            product.setGenre(baseGenre);
        }, () -> {
            try {
                throw new ServerException("Can't find the genre of id: " + genreId);
            } catch (ServerException ignored) {

            }
        });

    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findProductByName(String name) {
        return productRepository.findProductByName(name);
    }

    //Fix the create product
    public Product createProduct(ProductInput productInput) throws ServerException {
        if (productInput.getImages() == null) {
            productInput.setImages(List.of());
        }
        Product product = new Product();
        product.convertInputToProduct(productInput);
        String genreId = productInput.getGenreId();
        setGenreToProduct(genreId, product);

        product.setCreated(LocalDateTime.now());

        Product newProduct = productRepository.save(product);
        if (productInput.getGenreId() != null) genreRepository.insertProduct(newProduct.getGenre().getId(), newProduct);
        return newProduct;
    }

    public Product getProductById(String id) throws ServerException {
        Optional<Product> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok)
                .orElseThrow(() -> new ServerException("Can't find the product of id: " + id)).getBody();
    }

    public Product editProduct(ProductInput editedProductInput, String id) throws ServerException {
        if (productRepository.findById(id).isEmpty()) throw new ServerException("Can't find the id");

        Product product = new Product();
        product.convertInputToProduct(editedProductInput);
        String genreId = editedProductInput.getGenreId();
        setGenreToProduct(genreId, product);

        Product resultProduct = productRepository.editCurrentObject(id, productRepository.convertItemToMap(product), Product.class);
        if (genreId != null) genreRepository.insertProduct(genreId, resultProduct);
        return resultProduct;
    }

    public void deleteProduct(String id) throws ServerException {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresentOrElse((result) -> {
            BaseGenre genre = result.getGenre();
            genreRepository.removeProduct(genre.getId(), result);
        }, () -> {
            try {
                throw new ServerException("Can't find the product of id: " + id);
            } catch (ServerException ignored) {

            }
        });
        productRepository.deleteById(id);
    }
}
