package com.example.demo.Product;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class ProductInput extends BaseProduct implements Serializable {
    private List<MultipartFile> images;

    private String genreId;

    public ProductInput() {

    }
}
