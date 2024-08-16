package com.example.demo.Genre;

import com.example.demo.Product.BaseProduct;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;


import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@Component
@Data
@Document
public class Genre extends BaseGenre {

    public Genre() {
        super();
    }

    private List<BaseProduct> products;
    public BaseGenre covertToBaseGenre() {
        BaseGenre baseGenre = new BaseGenre();
        baseGenre.setId(this.getId());
        baseGenre.setImage(this.getImage());
        baseGenre.setName(this.getName());
        return baseGenre;
    }
}
