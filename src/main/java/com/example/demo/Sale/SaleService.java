package com.example.demo.Sale;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SaleService {
    private final SaleRepository saleRepository;

    public List<SaleModel> getAllSales() {
        return saleRepository.findAll();
    }

    public SaleModel createSale(SaleDTO saleDTO) {
        if (saleDTO.getImages() == null) {
            saleDTO.setImages(new ArrayList<>());
        }
        if (saleDTO.getProductIds() == null) {
            saleDTO.setProductIds(new ArrayList<>());
        }
        SaleModel saleModel = new SaleModel();
        saleModel.convertDTOToSale(saleDTO);
        return saleRepository.save(saleModel);
    }
}
