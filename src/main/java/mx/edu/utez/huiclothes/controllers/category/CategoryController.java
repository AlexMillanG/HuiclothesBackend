package mx.edu.utez.huiclothes.controllers.category;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.category.CategoryBean;
import mx.edu.utez.huiclothes.services.categories.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
@CrossOrigin({"*"})

public class CategoryController {


    private final CategoryService service;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> findAll() {
        return service.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody CategoryBean categoryBean){
        return service.createCategory(categoryBean);
    }



}
