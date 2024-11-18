package mx.edu.utez.huiclothes.controllers.product;


import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.category.CategoryBean;
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
    public ResponseEntity<ApiResponse> save(@RequestBody ProductBean productBean){
        return service.save(productBean);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody ProductBean productBean){
        return service.updateProduct(productBean);
    }


}
