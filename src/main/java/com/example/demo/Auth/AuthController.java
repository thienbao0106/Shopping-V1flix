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
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Param("username") String username, @Param("password")String password) throws ServerException {
        // Validate user credentials
        // If valid, generate JWT token
        LoginResponseModel responseModel = authService.authenticate(username, password);
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.FETCH_SUCCESSFULLY.toString(),
                "Login successfully",
                responseModel
                ,
                ResponseType.SUCCESS.toString()
        );
        final ResponseEntity<Map<String, Object>> responseEntity = new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
        ResponseEntity.ok().header("Authorization", "Bearer " + responseModel.getToken());
        return responseEntity;
    }
}
