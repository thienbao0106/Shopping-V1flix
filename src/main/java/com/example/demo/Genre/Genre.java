package com.example.demo.Genre;

import com.example.demo.Product.BaseProduct;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;



import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
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
