package com.example.demo.Sale;

import jakarta.validation.constraints.Max;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Setter
@Getter
public class BaseSale {
    @Indexed(unique = true)
    private String name;

    private String description;

    @Max(value = 100, message = "The percentage can't be over 100%")
    private float percentage;


}
