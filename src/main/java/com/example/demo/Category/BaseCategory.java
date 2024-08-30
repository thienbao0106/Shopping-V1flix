package com.example.demo.Category;

import com.example.demo.Image;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Setter
@Getter
public class BaseCategory {


    @Indexed(unique = true)
    @NotBlank
    private String name;

    private Image image;


}
