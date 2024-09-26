package com.example.demo.Sale;

import com.example.demo.Image;
import com.example.demo.Product.ProductDTO;
import com.example.demo.Product.ProductModel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode(callSuper = true)
@Data
@Component
@Document(collection = "sales")
public class SaleModel extends BaseSale {
    private List<Image> images;

    @DocumentReference(collection = "products")
    @JsonIgnoreProperties("sale")
    private List<ProductModel> products;

    @Id
    private String id;
    private Date dueDate;

    public SaleModel() {
        super();
    }

    public void convertDTOToSale(SaleDTO saleDTO) {
        System.out.println("Sale hours: " + saleDTO.getHours().toString());
        Calendar cal = Calendar.getInstance();
        Date currentDate = new Date();
        cal.setTime(currentDate);
        cal.add(Calendar.HOUR_OF_DAY, saleDTO.getHours());
        this.setName(saleDTO.getName());
        this.setDescription(saleDTO.getDescription());
        this.setDueDate(cal.getTime());
//        this.setProducts(List.of());
//        this.setImages(List.of());
        this.setPercentage(saleDTO.getPercentage());
    }

}
