package com.example.demo.Auth;

import com.example.demo.Base.ResponseHeader;
import com.example.demo.Enum.ResponseType;
import com.example.demo.Enum.SuccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private final AuthService authService;

    @Operation(summary = "Login system", description = "Login system")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logged successfully", content = @Content(examples = {
                    @ExampleObject(name = "userLogin", value = ExampleResponse.login)
            })),
            @ApiResponse(responseCode = "400", description = "Can't fetch the products")
    })
    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@Param("username") @Parameter(name = "username", description = "Username", example = "bao123") String username,
                                          @Param("password") @Parameter(name = "password", description = "Password", example = "bao123") String password)
            throws ServerException {
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
