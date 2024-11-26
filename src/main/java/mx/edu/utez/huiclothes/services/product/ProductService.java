package mx.edu.utez.huiclothes.services.product;

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
import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional(rollbackFor = SQLException.class)
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final CategoryRepository categoryRepository;


    //findAll
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findAll(){
        return new ResponseEntity<>(new ApiResponse(repository.findAll(),HttpStatus.OK), HttpStatus.OK);
    }

    //findOne por Id
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findOne(Long id){

        Optional<ProductBean> foundProduct = repository.findById(id);
        if (foundProduct.isEmpty())
        return new ResponseEntity<>(new ApiResponse("error, producto no econtrado", true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ApiResponse(foundProduct.get(),HttpStatus.OK),HttpStatus.OK);
    }

    //delete por id
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> deleteById(Long id){
        Optional<ProductBean> foundProduct = repository.findById(id);
        if (foundProduct.isEmpty())
        return new ResponseEntity<>(new ApiResponse("error, el producto que intentas eliminar no exite", true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);

        ProductBean productBean = foundProduct.get();
        repository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse("producto " +productBean.getName() + " eliminado con éxito",false ,HttpStatus.OK,productBean),HttpStatus.OK);
    }

    //save
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> save(ProductBean productBean){

        productBean.setEntry_date(LocalDate.now());
        if (productBean.getPrice()<0L)
            return new ResponseEntity<>(new ApiResponse("error, ingresa un precio valido para el producto", true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);

        Optional<CategoryBean> foundCategory = categoryRepository.findById(productBean.getCategory().getIdCategory());

        if (foundCategory.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, la categoría que intentas asociar al producto, no existe", true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new ApiResponse("producto Guaradado con exito",false,HttpStatus.OK, repository.saveAndFlush(productBean)),HttpStatus.OK);
    }

    //update
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> updateProduct(ProductBean productBean){

        Optional<ProductBean> foundProduct = repository.findById(productBean.getId());

        if (foundProduct.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, el producto que intentas actualizar, no existe", true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);

        productBean.setEntry_date(LocalDate.now());
        if (productBean.getPrice()<0L)
            return new ResponseEntity<>(new ApiResponse("error, ingresa un precio valido para el producto", true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);

        Optional<CategoryBean> foundCategory = categoryRepository.findById(productBean.getCategory().getIdCategory());

        if (foundCategory.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, la categoría que intentas asociar al producto, no existe", true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(new ApiResponse("producto Guaradado con exito",false,HttpStatus.OK, repository.saveAndFlush(productBean)),HttpStatus.OK);

    }


    //encontrar por categoria
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findByCategory(CategoryBean categoryBean){

        Optional<CategoryBean> foundCategory = categoryRepository.findById(categoryBean.getIdCategory());

        if (foundCategory.isEmpty())
        return new ResponseEntity<>(new ApiResponse("error, la categoría por la que intentas buscar productos, no existe", true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(new ApiResponse(repository.findByCategory(categoryBean),HttpStatus.OK),HttpStatus.OK);

    }






}
