package com.example.demo.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponseModel {
    private String token;
    private String userId;
    private String username;
    private Date expiredAt;
}
