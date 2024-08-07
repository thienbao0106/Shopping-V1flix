package com.example.demo;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class Image {
    private String url;
    private String name;
    private String type;

}
