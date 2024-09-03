package com.example.demo.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

@Setter
@Getter
public class BaseUser {
    @Indexed(unique = true)
    @NotBlank
    private String username;


    @NotBlank
    private String password;

    @JsonIgnore
    @JsonProperty(value = "password")
    public String getPassword() {
        return password;
    }

    @NotBlank
    private String fullName;

    @Indexed(unique = true)
    @NotBlank
    private String email;

    private String address;

    private String avatar;
}
