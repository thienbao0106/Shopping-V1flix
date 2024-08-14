package com.example.demo.Genre;

import com.example.demo.Base.ResponseHeader;
import com.example.demo.Enum.ResponseType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/genres")
@AllArgsConstructor
@ControllerAdvice
public class GenreController {
    @Autowired
    private GenreService genreService;

    @GetMapping(value = "")
    public ResponseEntity<?> fetchGenres() {
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                "FETCH_SUCCESSFULLY",
                "Fetch list of genres successfully",
                genreService.getAllGenres(),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> addProduct(@RequestBody @Valid Genre genre) throws ServerException {
        if (genre == null) throw new ServerException("Genre can't be emptied");
        Genre newGenre = genreService.createGenre(genre);
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                "CREATE_SUCCESSFULLY",
                "Create genre " + genre.getName() + " successfully",
                newGenre,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }
}
