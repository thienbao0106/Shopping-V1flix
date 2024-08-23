package com.example.demo.Product;

import com.example.demo.Genre.BaseGenre;
import com.example.demo.Genre.Genre;
import com.example.demo.Genre.GenreRepository;
import com.example.demo.Image;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.io.File;
import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.*;


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

    private void uploadImages(ProductInput productInput, Product product) {
        List<Image> images = new ArrayList<>();
        if (productInput.getImages() != null && !productInput.getImages().isEmpty()) {
            List<MultipartFile> inputImages = productInput.getImages();
            inputImages.forEach((image) -> {
                Map<?, ?> result = productRepository.uploadImageToCloudinary(image, product.getName(), "cover");
                System.out.println("Custom image: ");
                System.out.println(result);
                Image img = new Image((String) result.get("url"), Objects.requireNonNull(image.getOriginalFilename()).split("\\.")[0], "cover");
                images.add(img);
            });
        }
        product.setImages(images);
    }

    public Page<Product> getAllProducts(Pageable pageable) {

        return productRepository.findAll(pageable);
    }

    public Page<Product> findProductByName(String name, Pageable pageable) {
        return productRepository.findProductByName(name, pageable);
    }


    public Product createProduct(ProductInput productInput) throws ServerException {
        if (productInput.getImages() == null) {
            productInput.setImages(List.of());
        }
        Product product = new Product();
        product.convertInputToProduct(productInput);
        product.setCreated(LocalDateTime.now());

        String genreId = productInput.getGenreId();
        setGenreToProduct(genreId, product);
        uploadImages(productInput, product);

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

        Product product = productRepository.findById(id).get();
        uploadImages(editedProductInput, product);

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
