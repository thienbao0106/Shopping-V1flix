package com.example.demo.Product;

import com.example.demo.Enum.ResponseType;
import com.example.demo.ResponseHeader;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@AllArgsConstructor
@ControllerAdvice
public class ProductController {
    @Autowired
    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> fetchAllProducts(
            @RequestParam(required = false, name = "name") String name
    ) {
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                "FETCH_LIST_SUCCESSFULLY",
                "Find list successfully",
                null,
                ResponseType.SUCCESS.toString()
        );
        if (name != null) responseHeader.setResponseData(productService.findProductByName(name));
        else
            responseHeader.setResponseData(productService.getAllProducts());
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchProductById(@PathVariable("id") String id) throws ServerException {
        if (id == null || id.isEmpty()) throw new ServerException("Product can't be emptied");
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                "FETCH_LIST_SUCCESSFULLY",
                "Find product with id " + id + " successfully",
                productService.getProductById(id),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }

    @PostMapping(value = "/create",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product) throws ServerException {
        if (product == null) throw new ServerException("Product can't be emptied");
        Product newProduct = productService.createProduct(product);

        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                "CREATE_SUCCESSFULLY",
                "Create product" + product.getName() + " successfully",
                newProduct,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> editProduct(@PathVariable(value = "id") String id, @RequestBody @Valid Product product) {
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                "EDIT_SUCCESSFULLY",
                "Edit product with it" + id + " successfully",
                productService.editProduct(product, id),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);

    }

    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") String id) throws ServerException {
        productService.deleteProduct(id);
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                "DELETE_SUCCESSFULLY",
                "Delete product with it" + id + " successfully",
                null,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }
}
