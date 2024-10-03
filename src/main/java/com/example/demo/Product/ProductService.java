package com.example.demo.Product;

import com.example.demo.Category.CategoryModel;
import com.example.demo.Category.CategoryRepository;
import com.example.demo.Image.Image;
import com.example.demo.Sale.SaleModel;
import com.example.demo.Sale.SaleRepository;
import lombok.AllArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.rmi.ServerException;
import java.time.LocalDateTime;
import java.util.*;


@AllArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SaleRepository saleRepository;

    private void setCategoryToProduct(String categoryId, ProductModel productModel) {
        if (categoryId == null || categoryId.isEmpty()) return;
        Optional<CategoryModel> category = categoryRepository.findById(categoryId);
        category.ifPresentOrElse(productModel::setCategory, () -> {
            try {
                throw new ServerException("Can't find the genre of id: " + categoryId);
            } catch (ServerException ignored) {

            }
        });
    }

    private void setSaleToProduct(String saleId, ProductModel productModel) {
        if (saleId == null || saleId.isEmpty()) return;
        Optional<SaleModel> sale = saleRepository.findById(saleId);
        sale.ifPresentOrElse(productModel::setSale, () -> {
            try {
                throw new ServerException("Can't find the genre of id: " + saleId);
            } catch (ServerException ignored) {

            }
        });
    }

    private void uploadImages(ProductDTO productDTO, ProductModel productModel) {
        List<Image> images = new ArrayList<>();
        if (productDTO.getImages() != null && !productDTO.getImages().isEmpty()) {
            List<MultipartFile> inputImages = productDTO.getImages();
            inputImages.forEach((image) -> {
                Map<?, ?> result = productRepository.uploadImageToCloudinary(image, productModel.getName(), "cover");
                System.out.println("Custom image: ");
                System.out.println(result);
                Image img = new Image((String) result.get("url"), Objects.requireNonNull(image.getOriginalFilename()).split("\\.")[0], "cover");
                images.add(img);
            });
        }
        productModel.setImages(images);
    }

    public Page<ProductModel> getAllProducts(Pageable pageable) {

        return productRepository.findAll(pageable);
    }

    public Page<ProductModel> findProductByName(String name, Pageable pageable) {
        return productRepository.findProductByName(name, pageable);
    }


    public ProductModel createProduct(ProductDTO productDTO) throws ServerException {
        if (productDTO.getImages() == null) {
            productDTO.setImages(List.of());
        }
        ProductModel productModel = new ProductModel();
        productModel.convertDTOToProduct(productDTO);
        productModel.setCreated(LocalDateTime.now());

        String categoryId = productDTO.getCategoryId();
        String saleId = productDTO.getSaleId();
        setCategoryToProduct(categoryId, productModel);
        setSaleToProduct(saleId, productModel);
        uploadImages(productDTO, productModel);

        ProductModel newProductModel = productRepository.insert(productModel);
        if (categoryId != null)
            categoryRepository.insertProduct(newProductModel.getCategory().getId(), new ObjectId(newProductModel.getId()));
        if(saleId != null)
            saleRepository.insertProduct(newProductModel.getSale().getId(), new ObjectId(newProductModel.getId()));
        return newProductModel;
    }

    public ProductModel getProductById(String id) throws ServerException {
        Optional<ProductModel> product = productRepository.findById(id);
        return product.map(ResponseEntity::ok)
                .orElseThrow(() -> new ServerException("Can't find the product of id: " + id)).getBody();
    }

    public ProductModel editProduct(ProductDTO editedProductDTO, String id) throws ServerException {
        if (productRepository.findById(id).isEmpty()) throw new ServerException("Can't find the id");

        ProductModel productModel = productRepository.findById(id).get();
        uploadImages(editedProductDTO, productModel);

        productModel.convertDTOToProduct(editedProductDTO);

        String categoryId = editedProductDTO.getCategoryId();
        String saleId = editedProductDTO.getSaleId();
        System.out.println("Sale id: " + saleId);
        setCategoryToProduct(categoryId, productModel);
        setSaleToProduct(saleId, productModel);
        Class<ProductModel> productModelClass = ProductModel.class;
        ProductModel resultProductModel = productRepository.editCurrentObject(id, productRepository.convertItemToMap(productModel, productModelClass.getName()), productModelClass);

        if (categoryId != null) categoryRepository.insertProduct(categoryId, new ObjectId(resultProductModel.getId()));
        if (saleId != null) saleRepository.insertProduct(saleId, new ObjectId(resultProductModel.getId()));
        return resultProductModel;
    }


    public void deleteProduct(String id) throws ServerException {
        Optional<ProductModel> product = productRepository.findById(id);
        product.ifPresentOrElse((result) -> {
            CategoryModel category = result.getCategory();
            categoryRepository.removeProduct(category.getId(), result);
        }, () -> {
            try {
                throw new ServerException("Can't find the product of id: " + id);
            } catch (ServerException ignored) {

            }
        });
        productRepository.deleteById(id);
    }
}
