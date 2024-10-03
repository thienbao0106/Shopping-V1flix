package com.example.demo.Sale;

import com.example.demo.Image.Image;
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

    private Calendar convertToCalendar(int hours) {
        System.out.println("Sale hours: " + hours);
        Calendar cal = Calendar.getInstance();
        Date currentDate = new Date();
        cal.setTime(currentDate);
        cal.add(Calendar.HOUR_OF_DAY, hours);
        return cal;
    }

    public void convertDTOToSale(SaleDTO saleDTO) {
        Calendar dueDate = convertToCalendar(saleDTO.getHours());
        this.setName(saleDTO.getName());
        this.setDescription(saleDTO.getDescription());
        this.setDueDate(dueDate.getTime());
        this.setPercentage(saleDTO.getPercentage());
    }

}
