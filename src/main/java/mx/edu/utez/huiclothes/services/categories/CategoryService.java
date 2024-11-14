package mx.edu.utez.huiclothes.services.categories;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.category.CategoryBean;
import mx.edu.utez.huiclothes.models.category.CategoryRepository;
import mx.edu.utez.huiclothes.models.products.ProductBean;
import mx.edu.utez.huiclothes.models.products.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    // Método para crear una categoría
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> createCategory(CategoryBean categoryBean) {
        // Verificar si la categoría ya existe
        Optional<CategoryBean> existingCategory = categoryRepository.findByName(categoryBean.getName());
        if (existingCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse("Category already exists", true, HttpStatus.CONFLICT, null), HttpStatus.CONFLICT);
        }
        // Guardar nueva categoría
        CategoryBean savedCategory = categoryRepository.save(categoryBean);
        return new ResponseEntity<>(new ApiResponse("Category created", false, HttpStatus.OK, savedCategory), HttpStatus.OK);
    }

    // Método para encontrar productos por categoría
    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> findProductsByCategory(Long idCategory) {
        // Buscar la categoría por su ID
        Optional<CategoryBean> optionalCategory = categoryRepository.findById(idCategory);
        if (optionalCategory.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse("Category not found", true, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND);
        }

        // Obtener productos asociados a la categoría
        List<ProductBean> foundProducts = productRepository.findByCategory(optionalCategory.get());
        return new ResponseEntity<>(new ApiResponse("Products found", false, HttpStatus.OK, foundProducts), HttpStatus.OK);
    }
}
