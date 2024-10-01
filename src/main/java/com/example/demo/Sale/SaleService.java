package com.example.demo.Sale;


import com.example.demo.Category.CategoryModel;
import com.example.demo.Product.ProductModel;
import com.example.demo.Product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SaleService {
    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public List<SaleModel> getAllSales() {
        return saleRepository.findAll();
    }

    public SaleModel getSaleById(String id) throws ServerException {
        Optional<SaleModel> sale = saleRepository.findById(id);
        return sale.map(ResponseEntity::ok).orElseThrow(() -> new ServerException("Can't find this sale")).getBody();
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

    public SaleModel editSale(SaleDTO edittedSaleDTO, String saleId) throws ServerException {
        if (saleRepository.findById(saleId).isEmpty())
            throw new ServerException("Can't find the id of sale: " + saleId);
        if(edittedSaleDTO.getHours() == null) {
            throw new ServerException("Hour can't be null");
        }
        SaleModel saleModel = saleRepository.findById(saleId).get();

        saleModel.convertDTOToSale(edittedSaleDTO);
        Class<SaleModel> saleModelClass = SaleModel.class;
        return saleRepository.editCurrentObject(saleId, saleRepository.convertItemToMap(saleModel, saleModelClass.getName()), saleModelClass);

    }

    public void deleteSale(String saleId) throws ServerException {
        SaleModel saleModel = saleRepository.findById(saleId).map(ResponseEntity::ok).orElseThrow(() -> new ServerException("Can't find the sale of id: " + saleId)).getBody();
        assert saleModel != null;
        List<ProductModel> products = saleModel.getProducts();
        if (products != null) {
            products.forEach((product) -> {
                productRepository.removeGenreInProduct(String.valueOf(product));
            });
        }
    }
}
