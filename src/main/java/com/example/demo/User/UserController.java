package com.example.demo.User;

import com.example.demo.Base.ResponseHeader;
import com.example.demo.Enum.ResponseType;
import com.example.demo.Enum.SuccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserService userService;

    @Operation(summary = "Get all users", description = "Get all users", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(examples = {
                    @ExampleObject(name = "getAllUSers", value = ExampleResponse.fetchAll)
            })),
            @ApiResponse(responseCode = "400", description = "Can't fetch the users")
    })
    @GetMapping("")
    public ResponseEntity<?> fetchAllUsers() {
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.FETCH_SUCCESSFULLY.toString(),
                "Find list successfully",
                userService.getAllUsers(),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }


    @Operation(summary = "Get specific user by id", description = "Find an id of user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(examples = {
                    @ExampleObject(name = "getUserById", value = ExampleResponse.findUserById)
            })),
            @ApiResponse(responseCode = "400", description = "Can't fetch the product")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> fetchUserById(@PathVariable(value = "id") String id) throws ServerException {
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.FETCH_SUCCESSFULLY.toString(),
                "Find user " + id + "successfully",
                userService.getUserById(id),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }

    @Operation(summary = "Create an user", description = "create an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created", content = @Content(examples = {
                    @ExampleObject(name = "createUser", value = ExampleResponse.createUser)
            })),
            @ApiResponse(responseCode = "400", description = "Can't create the user")
    })
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> addUser(@ModelAttribute @Valid UserDTO userDTO) throws ServerException {
        if(userDTO == null) throw new ServerException("User can't be null!");
        UserModel newUserModel = userService.createUser(userDTO);
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.CREATE_SUCCESSFULLY.toString(),
                "Create user " + userDTO.getUsername() + " successfully",
                newUserModel,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully edited", content = @Content(examples = {
                    @ExampleObject(name = "createUser", value = ExampleResponse.editUser)
            })),
            @ApiResponse(responseCode = "400", description = "Can't edit the user")
    })
    public ResponseEntity<?> editUser(@PathVariable(value = "id") String id, @ModelAttribute UserDTO userDTO) throws ServerException {
        if(userDTO == null) throw new ServerException("User can't be null!");
        UserModel editedUserModel = userService.editUser(userDTO, id);
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.EDIT_SUCCESSFULLY.toString(),
                "Edit user " + id + " successfully",
                editedUserModel,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}/delete")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully edited", content = @Content(examples = {
                    @ExampleObject(name = "createUser", value = ExampleResponse.deleteUser)
            })),
            @ApiResponse(responseCode = "400", description = "Can't edit the user")
    })
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") String id) throws ServerException {
        userService.deleteUser(id);
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.DELETE_SUCCESSFULLY.toString(),
                "Delete user " + id + " successfully",
                null,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }
}
