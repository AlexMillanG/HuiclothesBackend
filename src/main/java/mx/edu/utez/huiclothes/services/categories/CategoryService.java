package mx.edu.utez.huiclothes.services.categories;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.category.CategoryBean;
import mx.edu.utez.huiclothes.models.category.CategoryRepository;
import mx.edu.utez.huiclothes.models.image.ImageBean;
import mx.edu.utez.huiclothes.models.imageCategory.ImageCategoryBean;
import mx.edu.utez.huiclothes.models.imageCategory.ImageCategoryRepository;
import mx.edu.utez.huiclothes.models.products.ProductBean;
import mx.edu.utez.huiclothes.models.products.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ImageCategoryRepository imageCategoryRepository;

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


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> findAll(){
        return new ResponseEntity<>(new ApiResponse(categoryRepository.findAll(),HttpStatus.OK),HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> findOne(Long id){
        Optional<CategoryBean> foundCategory = categoryRepository.findById(id);

        if (foundCategory.isEmpty())
            return new ResponseEntity<>(new ApiResponse("category not found",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);


        return new ResponseEntity<>(new ApiResponse("",false,HttpStatus.OK,foundCategory.get()),HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> updateCategory(Long id){
        Optional<CategoryBean> foundCategory = categoryRepository.findById(id);

        if (foundCategory.isEmpty())
            return  new ResponseEntity<>(new ApiResponse("category not found",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ApiResponse("",false,HttpStatus.OK,foundCategory.get()),HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> saveWithImage(String categoryName, MultipartFile file) throws IOException {

        if (categoryName.equals("") || categoryName.equals(null))
            return new ResponseEntity<>(new ApiResponse("ingresa un nombre valido",true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);
        if (file.isEmpty())
            return new ResponseEntity<>(new ApiResponse("ingresa una imagen valida", false,HttpStatus.BAD_REQUEST, null),HttpStatus.BAD_REQUEST);


        ImageCategoryBean image = new ImageCategoryBean();
        image.setImage(file.getBytes());  // Guardamos el archivo como un arreglo de bytes

        // Guardar la imagen en la base de datos
        image = imageCategoryRepository.save(image);

        // Crear la categoría y asociar la imagen
        CategoryBean category = new CategoryBean();
        category.setName(categoryName);
        category.setImage(image);

        return new ResponseEntity<>(new ApiResponse("categoría con imagen guardada correctamente",false,HttpStatus.OK,categoryRepository.save(category)),HttpStatus.OK);


    }
}
