package com.example.demo.Sale;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class SaleDTO extends BaseSale implements Serializable {
    private List<MultipartFile> images;
    private List<String> productIds;
    @Min(value = 24, message = "Can't be less than one day.")
    private Integer hours;

    public SaleDTO() {

    }
}
