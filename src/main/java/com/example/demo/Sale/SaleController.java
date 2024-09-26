package com.example.demo.Sale;

import com.example.demo.Base.ResponseHeader;
import com.example.demo.Enum.ResponseType;
import com.example.demo.Enum.SuccessType;
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
}
