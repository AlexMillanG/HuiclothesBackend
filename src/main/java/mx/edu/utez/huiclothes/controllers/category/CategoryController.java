package mx.edu.utez.huiclothes.controllers.category;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.category.CategoryBean;
import mx.edu.utez.huiclothes.services.categories.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


    @PostMapping("/saveWithImg")
    public ResponseEntity<ApiResponse> saveWithImage( @RequestParam("name") String name,
                                                      @RequestParam("image") MultipartFile imageFile) throws IOException {
        System.err.println(name);

        return service.saveWithImage(name, imageFile);
    }

}
