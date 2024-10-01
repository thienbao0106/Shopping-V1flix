package com.example.demo.Sale;

import com.example.demo.Base.ResponseHeader;
import com.example.demo.Enum.ResponseType;
import com.example.demo.Enum.SuccessType;
import com.example.demo.Product.ProductDTO;
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
@RequestMapping("/sales")
@AllArgsConstructor
@ControllerAdvice
public class SaleController {
    @Autowired
    private SaleService saleService;


    @Operation(summary = "Get all sales", description = "Get all sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(examples = {
                    @ExampleObject(name = "getAllSales", value = ExampleResponse.fetchAll)
            })),
            @ApiResponse(responseCode = "400", description = "Can't fetch the products")
    })
    @GetMapping(value = "")
    public ResponseEntity<?> fetchSales() {
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.FETCH_SUCCESSFULLY.toString(),
                "Fetch list of categories successfully",
                saleService.getAllSales(),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }


    @Operation(summary = "Get sale by id", description = "Get sale by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = @Content(examples = {
                    @ExampleObject(name = "getSaleById", value = ExampleResponse.fetchSaleById)
            })),
            @ApiResponse(responseCode = "400", description = "Can't fetch the sale")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> fetchProductById(@PathVariable("id")
                                              @Parameter(name = "id", description = "Id of the sale", example = "66f52e2081b3db7dedf7e33f") String id) throws ServerException {
        if (id == null || id.isEmpty()) throw new ServerException("Sale can't be emptied");
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.FETCH_SUCCESSFULLY.toString(),
                "Find sale with id " + id + " successfully",
                saleService.getSaleById(id),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }


    @Operation(summary = "Create a sale", description = "Create a sale", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created", content = @Content(examples = {
                    @ExampleObject(name = "createSale", value = ExampleResponse.createSale)
            })),
            @ApiResponse(responseCode = "400", description = "Can't create the sale")
    })
    @PostMapping(value = "/create",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<?> addSale(@ModelAttribute @Valid SaleDTO saleDTO) throws ServerException {
        if (saleDTO == null) throw new ServerException("Sale can't be emptied");
        SaleModel newSaleModel = saleService.createSale(saleDTO);
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.CREATE_SUCCESSFULLY.toString(),
                "Create sale " + saleDTO.getName() + " successfully",
                newSaleModel,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }

    @Operation(summary = "Edit a sale", description = "Edit a sale",  security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully edited", content = @Content(examples = {
                    @ExampleObject(name = "editSale", value = ExampleResponse.editSale)
            })),
            @ApiResponse(responseCode = "400", description = "Can't edit the sale")
    })
    @PutMapping(value = "/{id}/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editSale(@PathVariable(value = "id") String id, @ModelAttribute SaleDTO saleDTO) throws ServerException {
        if (saleDTO == null) throw new ServerException("Product can't be emptied");
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.EDIT_SUCCESSFULLY.toString(),
                "Edit product with id " + id + " successfully",
                saleService.editSale(saleDTO, id),
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a sale", description = "Delete a sale by id",  security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully deleted", content = @Content(examples = {
                    @ExampleObject(name = "createProduct", value = ExampleResponse.deleteSale)
            })),
            @ApiResponse(responseCode = "400", description = "Can't create the product")
    })
    @DeleteMapping(value = "/{id}/delete")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id")  @Parameter(name = "id", description = "Id of the sale", example = "66fbff6efc697354c82dc759") String id) throws ServerException {
        saleService.deleteSale(id);
        ResponseHeader responseHeader = new ResponseHeader(
                LocalDateTime.now(),
                SuccessType.DELETE_SUCCESSFULLY.toString(),
                "Delete sale with id " + id + " successfully",
                null,
                ResponseType.SUCCESS.toString()
        );
        return new ResponseEntity<>(responseHeader.convertToMap(), HttpStatus.OK);
    }
}
