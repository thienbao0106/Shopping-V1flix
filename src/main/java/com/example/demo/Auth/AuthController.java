package com.example.demo.Auth;

import com.example.demo.Base.ResponseHeader;
import com.example.demo.Enum.ResponseType;
import com.example.demo.Enum.SuccessType;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.rmi.ServerException;
import java.time.LocalDateTime;

@AllArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Param("username") String username, String password) throws ServerException {
        // Validate user credentials
        // If valid, generate JWT token
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.FETCH_SUCCESSFULLY.toString(),
                "Login successfully",
                authService.authenticate(username, password),
                ResponseType.SUCCESS.toString()
        );

        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }
}
