package mx.edu.utez.huiclothes.controllers.product;

import mx.edu.utez.huiclothes.controllers.product.dto.ProductDto;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.category.CategoryBean;
import mx.edu.utez.huiclothes.models.products.Gender;
import mx.edu.utez.huiclothes.models.products.ProductBean;
import mx.edu.utez.huiclothes.services.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
@CrossOrigin({"*"})
public class ProductController {

    private final ProductService service;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> findAll(){
        return service.findAll();
    }

    @GetMapping("/findByCategory")
    public ResponseEntity<ApiResponse> findByCategory(@RequestBody CategoryBean categoryBean){
        return service.findByCategory(categoryBean);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        return service.findOne(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
        return service.deleteById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody ProductDto dto){


        Gender gender = null;
        switch (dto.getGender().toUpperCase()) {
            case "MALE":
                gender = Gender.MALE;
                break;
            case "FEMALE":
                gender = Gender.FEMALE;
                break;
            case "UNISEX":
                gender = Gender.UNISEX;
                break;
            default:
                return new ResponseEntity<>(new ApiResponse("Error: El valor de 'gender' es inválido. Los valores válidos son: MALE, FEMALE, UNISEX.", true, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);
        }

        ProductBean productBean = new ProductBean();
        productBean.setName(dto.getName());
        productBean.setDescription(dto.getDescription());
        productBean.setPrice(dto.getPrice());
        productBean.setGender(gender); 
        productBean.setCategory(dto.getCategory());


        return service.save(productBean);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody ProductBean productBean){
        return service.updateProduct(productBean);
    }




}
