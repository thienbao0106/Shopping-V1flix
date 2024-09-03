package com.example.demo.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO extends BaseUser {



    public UserDTO() {
        super();

    }
}
