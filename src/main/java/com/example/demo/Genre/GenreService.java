package com.example.demo.Genre;

import com.example.demo.Product.BaseProduct;
import com.example.demo.Product.Product;
import com.example.demo.Product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.List;

@AllArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepository;
    private final ProductRepository productRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre getGenreById(String id) throws ServerException {
        return genreRepository.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ServerException("Can't find the genre of id: " + id)).getBody();
    }

    public Genre createGenre(Genre genre) throws ServerException {
        if (genre.getProducts() == null) {
            genre.setProducts(List.of());
        }
        return genreRepository.save(genre);
    }

    public Genre editGenre(BaseGenre editedGenre, String genreId) throws ServerException {
        if (genreRepository.findById(genreId).isEmpty())
            throw new ServerException("Can't find the id of genre: " + genreId);
        return genreRepository.editCurrentObject(genreId, genreRepository.convertItemToMap((Genre) editedGenre), Genre.class);
    }

    public void deleteGenre(String genreId) throws ServerException {
        Genre genre = genreRepository.findById(genreId).map(ResponseEntity::ok).orElseThrow(() -> new ServerException("Can't find the genre of id: " + genreId)).getBody();
        assert genre != null;
        List<BaseProduct> products = genre.getProducts();
        if(products != null) {
            products.forEach((product) -> {
                productRepository.removeGenreInProduct(product.getId());
            });
        }
        genreRepository.deleteById(genreId);
    }
}
