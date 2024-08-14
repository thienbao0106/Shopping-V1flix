package com.example.demo.Genre;

import com.example.demo.Image;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
public class BaseGenre {
    @Id
    private String id;

    @Indexed(unique = true)
    @NotBlank
    private String name;

    private Image image;


}
