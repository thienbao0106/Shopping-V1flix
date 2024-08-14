package com.example.demo.Genre;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.List;

@AllArgsConstructor
@Service
public class GenreService {
    private final GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Genre createGenre(Genre genre) throws ServerException {
        if (genre.getProducts() == null) {
            genre.setProducts(List.of());
        }
        return genreRepository.save(genre);
    }
}
