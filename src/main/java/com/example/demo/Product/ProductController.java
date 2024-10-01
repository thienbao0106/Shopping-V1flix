package com.example.demo.Product;

import com.example.demo.Enum.ResponseType;
import com.example.demo.Base.ResponseHeader;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.rmi.ServerException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@ControllerAdvice
public class ProductController {
    @Autowired
    private final ProductService productService;


    @Operation(summary = "Get all products", description = "Get all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(examples = {
                    @ExampleObject(name = "getAllProducts", value = ExampleResponse.fetchAll)
            })),
            @ApiResponse(responseCode = "400", description = "Can't fetch the products")
    })
    @GetMapping("")
    public ResponseEntity<?> fetchAllProducts(
            @RequestParam(required = false, name = "name") @Parameter(name = "name", description = "Name needed to search", example = "Yuru Camp") String name,
            @RequestParam(required = false, name = "page", defaultValue = "0") @Parameter(name = "page", description = "Number of Page", example = "1") int page,
            @RequestParam(name = "pageSize") @Parameter(name = "pageSize", description = "Size of the page", example = "1") int pageSize
    ) {
        Pageable pageable = PageRequest.of(page, pageSize);
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.FETCH_SUCCESSFULLY.toString(),
                "Find list successfully",
                null,
                ResponseType.SUCCESS.toString()
        );
        if (name != null)
            responseHeader.setResponseData(productService.findProductByName(name, pageable));
        else
            responseHeader.setResponseData(productService.getAllProducts(pageable));


        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }


    @Operation(summary = "Get specific product by id", description = "Find an id of product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(examples = {
                    @ExampleObject(name = "getProductById", value = ExampleResponse.fetchProductById)
            })),
            @ApiResponse(responseCode = "400", description = "Can't fetch the product")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> fetchProductById(@PathVariable("id")
                                              @Parameter(name = "id", description = "Id of the product", example = "66d15c15dbb9cf59f73eb300") String id) throws ServerException {
        if (id == null || id.isEmpty()) throw new ServerException("Product can't be emptied");
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.FETCH_SUCCESSFULLY.toString(),
                "Find product with id " + id + " successfully",
                productService.getProductById(id),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }


    @Operation(summary = "Create a product", description = "Create a product", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created", content = @Content(examples = {
                    @ExampleObject(name = "createProduct", value = ExampleResponse.createProduct)
            })),
            @ApiResponse(responseCode = "400", description = "Can't create the product")
    })
    @PostMapping(value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> addProduct(@ModelAttribute @Valid ProductDTO productDTO) throws ServerException {
        if (productDTO == null) throw new ServerException("Product can't be emptied");
        ProductModel newProductModel = productService.createProduct(productDTO);

        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.CREATE_SUCCESSFULLY.toString(),
                "Create product" + newProductModel.getName() + " successfully",
                newProductModel,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);

    }


    @Operation(summary = "Edit a product", description = "Edit a product",  security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully edited", content = @Content(examples = {
                    @ExampleObject(name = "createProduct", value = ExampleResponse.editProduct)
            })),
            @ApiResponse(responseCode = "400", description = "Can't edit the product")
    })
    @PutMapping(value = "/{id}/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editProduct(@PathVariable(value = "id") String id, @ModelAttribute ProductDTO productDTO) throws ServerException {
        if (productDTO == null) throw new ServerException("Product can't be emptied");
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.EDIT_SUCCESSFULLY.toString(),
                "Edit product with id " + id + " successfully",
                productService.editProduct(productDTO, id),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.CREATED);

    }

    @Operation(summary = "Delete a product", description = "Delete a product by id",  security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully edited", content = @Content(examples = {
                    @ExampleObject(name = "createProduct", value = ExampleResponse.deleteProduct)
            })),
            @ApiResponse(responseCode = "400", description = "Can't create the product")
    })
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") String id) throws ServerException {
        productService.deleteProduct(id);
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.DELETE_SUCCESSFULLY.toString(),
                "Delete product with id " + id + " successfully",
                null,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }
}
