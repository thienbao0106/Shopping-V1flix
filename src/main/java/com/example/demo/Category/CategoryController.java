package com.example.demo.Category;

import com.example.demo.Base.ResponseHeader;
import com.example.demo.Enum.ResponseType;
import com.example.demo.Enum.SuccessType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/categories")
@AllArgsConstructor
@ControllerAdvice
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Operation(summary = "Get all categories", description = "Get all categories")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(examples = {
                    @ExampleObject(name = "getAllCategories", value = ExampleResponse.fetchAll)
            })),
            @ApiResponse(responseCode = "400", description = "Can't fetch the products")
    })
    @GetMapping(value = "")
    public ResponseEntity<?> fetchCategories() {
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.FETCH_SUCCESSFULLY.toString(),
                "Fetch list of categories successfully",
                categoryService.getAllGenres(),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }


    @Operation(summary = "Fetch a category", description = "Fetch a category by id",  security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully edited", content = @Content(examples = {
                    @ExampleObject(name = "fetchCategoryById", value = ExampleResponse.fetchCategoryById)
            })),
            @ApiResponse(responseCode = "400", description = "Can't create the product")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> fetchCategoryById(@PathVariable("id")  @Parameter(name = "id", description = "Category Id", example = "66ea4f298da7e4359c97218b") String id) throws ServerException {
        if (id == null || id.isEmpty()) throw new ServerException("Product can't be emptied");
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.FETCH_SUCCESSFULLY.toString(),
                "Find category with id " + id + " successfully",
                categoryService.getGenreById(id),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }

    @Operation(summary = "Create a category", description = "Create a category",  security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully created", content = @Content(examples = {
                    @ExampleObject(name = "createCategory", value = ExampleResponse.createCategory)
            })),
            @ApiResponse(responseCode = "400", description = "Can't create the category")
    })
    @PostMapping(value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> addCategory(@ModelAttribute @Valid CategoryDTO categoryDTO) throws ServerException {
        if (categoryDTO == null) throw new ServerException("Category can't be emptied");
        CategoryModel newCategoryModel = categoryService.createCategory(categoryDTO);
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.CREATE_SUCCESSFULLY.toString(),
                "Create category " + categoryDTO.getName() + " successfully",
                newCategoryModel,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }

    @Operation(summary = "Edit a product", description = "Edit a product by id",  security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully edited", content = @Content(examples = {
                    @ExampleObject(name = "editCategory", value = ExampleResponse.editCategory)
            })),
            @ApiResponse(responseCode = "400", description = "Can't edit the category")
    })
    @PutMapping(value = "/{id}/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editCategory(@PathVariable(value = "id")  @Parameter(name = "id", description = "Category Id", example = "66ea4f298da7e4359c97218b") String id, @ModelAttribute CategoryDTO categoryDTO) throws ServerException {
        if (categoryDTO == null) throw new ServerException("Category can't be emptied");
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.EDIT_SUCCESSFULLY.toString(),
                "Edit category with id " + id + " successfully",
                categoryService.editCategory(categoryDTO, id),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a category", description = "Delete a category by id",  security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted", content = @Content(examples = {
                    @ExampleObject(name = "deleteCategory", value = ExampleResponse.deleteCategory)
            })),
            @ApiResponse(responseCode = "400", description = "Can't delete the category")
    })
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<?> removeCategory(@PathVariable(value = "id") @Parameter(name = "id", description = "Category Id", example = "66ea4f298da7e4359c97218b")  String id) throws ServerException {
        categoryService.deleteCategory(id);
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.DELETE_SUCCESSFULLY.toString(),
                "Delete category with id " + id + " successfully",
                null,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }

}
