package mx.edu.utez.huiclothes.controllers.size;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.size.SizeBean;
import mx.edu.utez.huiclothes.services.size.SizeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/size")
@CrossOrigin({"*"})
@AllArgsConstructor
public class SizeController {

    private final SizeService service;


    @GetMapping("/")
    public ResponseEntity<ApiResponse> findAll(){
        return service.findAll();
    }

    @GetMapping("/findOne/{id}")
    public ResponseEntity<ApiResponse> findOne(@PathVariable Long id){
        return service.findOne(id);
    }

    @DeleteMapping("/deleteById/{id}")
    public  ResponseEntity<ApiResponse> deleteById(@PathVariable Long id){
        return service.deleteById(id);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody SizeBean sizeBean){
        return service.save(sizeBean);
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody SizeBean sizeBean){
        return service.update(sizeBean);
    }

}
