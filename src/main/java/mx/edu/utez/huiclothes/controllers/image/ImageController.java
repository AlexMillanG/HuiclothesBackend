package mx.edu.utez.huiclothes.controllers.image;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.image.ImageBean;
import mx.edu.utez.huiclothes.services.image.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/api/images")
@AllArgsConstructor
public class ImageController {


    private final ImageService service;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> findAll() {
        return service.findAll();
    }




    @PostMapping("/associate/{productId}/{colorId}")
    public ResponseEntity<ApiResponse> associateImages(
            @PathVariable Long productId,
            @PathVariable Long colorId,
            @RequestParam("images") List<MultipartFile> images) {
        return service.associateImagesWithFiles(productId, colorId, images);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id){
        return service.deleteImage(id);
    }

    @GetMapping("/findOne{id}")
    public ResponseEntity<ApiResponse> findOne(@PathVariable Long id){
        return service.findOne(id);
    }

    @GetMapping("findByProductId/{id}")
    public ResponseEntity<ApiResponse> findByProductId(@PathVariable Long id){
        return service.findByProductId(id);
    }

}