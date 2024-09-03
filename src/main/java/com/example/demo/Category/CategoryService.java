package com.example.demo.Category;

import com.example.demo.Product.BaseProduct;
import com.example.demo.Product.ProductModel;
import com.example.demo.Product.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.List;

@AllArgsConstructor
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public List<CategoryModel> getAllGenres() {
        return categoryRepository.findAll();
    }

    public CategoryModel getGenreById(String id) throws ServerException {
        return categoryRepository.findById(id).map(ResponseEntity::ok)
                .orElseThrow(() -> new ServerException("Can't find the genre of id: " + id)).getBody();
    }

    public CategoryModel createCategory(CategoryDTO categoryDTO) throws ServerException {
        if (categoryDTO.getProducts() == null) {
            categoryDTO.setProducts(List.of());
        }
        CategoryModel newCategory = new CategoryModel();
        newCategory.convertDTOToCategory(categoryDTO);
        return categoryRepository.save(newCategory);
    }

    public CategoryModel editCategory(BaseCategory editedGenre, String genreId) throws ServerException {
        if (categoryRepository.findById(genreId).isEmpty())
            throw new ServerException("Can't find the id of genre: " + genreId);
        Class<CategoryModel> categoryModelClass = CategoryModel.class;
        return categoryRepository.editCurrentObject(genreId, categoryRepository.convertItemToMap((CategoryModel) editedGenre, categoryModelClass.getName()), categoryModelClass);
    }

    public void deleteCategory(String genreId) throws ServerException {
        CategoryModel categoryModel = categoryRepository.findById(genreId).map(ResponseEntity::ok).orElseThrow(() -> new ServerException("Can't find the genre of id: " + genreId)).getBody();
        assert categoryModel != null;
        List<ProductModel> products = categoryModel.getProducts();
        if (products != null) {
            products.forEach((product) -> {
                productRepository.removeGenreInProduct(String.valueOf(product));
            });
        }
        categoryRepository.deleteById(genreId);
    }
}
