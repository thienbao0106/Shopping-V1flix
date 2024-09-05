package com.example.demo.User;

import com.example.demo.Base.ResponseHeader;
import com.example.demo.Enum.ResponseType;
import com.example.demo.Enum.SuccessType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@ControllerAdvice
public class UserController {
    private UserService userService;
    @GetMapping("")
    public ResponseEntity<?> fetchAllProducts() {

        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.FETCH_SUCCESSFULLY.toString(),
                "Find list successfully",
                userService.getAllUsers(),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }



    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> addUser(@ModelAttribute @Valid UserDTO userDTO) throws ServerException {
        if(userDTO == null) throw new ServerException("User can't be null!");
        UserModel newUserModel = userService.createUser(userDTO);
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.CREATE_SUCCESSFULLY.toString(),
                "Create category " + userDTO.getUsername() + " successfully",
                newUserModel,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }

}
