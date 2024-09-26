package com.example.demo.Product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class ProductDTO extends BaseProduct implements Serializable {
    private List<MultipartFile> images;

    private String categoryId;

    private String saleId;

    public ProductDTO() {

    }
}
