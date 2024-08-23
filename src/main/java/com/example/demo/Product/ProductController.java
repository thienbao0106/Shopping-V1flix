package com.example.demo.Product;

import com.example.demo.Enum.ResponseType;
import com.example.demo.Base.ResponseHeader;
import com.example.demo.Enum.SuccessType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@ControllerAdvice
public class ProductController {
    @Autowired
    private final ProductService productService;

    @GetMapping("")
    public ResponseEntity<?> fetchAllProducts(
            @RequestParam(required = false, name = "name") String name,
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "pageSize", defaultValue = "0") int pageSize
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

    @GetMapping("/{id}")
    public ResponseEntity<?> fetchProductById(@PathVariable("id") String id) throws ServerException {
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

    @PostMapping(value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> addProduct(@ModelAttribute @Valid ProductInput productInput) throws ServerException {
        if (productInput == null) throw new ServerException("Product can't be emptied");
        Product newProduct = productService.createProduct(productInput);

        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.CREATE_SUCCESSFULLY.toString(),
                "Create product" + newProduct.getName() + " successfully",
                newProduct,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);

    }

    @PutMapping(value = "/{id}/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editProduct(@PathVariable(value = "id") String id, @ModelAttribute ProductInput productInput) throws ServerException {
        if (productInput == null) throw new ServerException("Product can't be emptied");
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.EDIT_SUCCESSFULLY.toString(),
                "Edit product with id " + id + " successfully",
                productService.editProduct(productInput, id),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.CREATED);

    }

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
