package com.example.demo.Category;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class CategoryDTO extends BaseCategory {
    private List<String> products;

    public CategoryDTO() {

    }


}
