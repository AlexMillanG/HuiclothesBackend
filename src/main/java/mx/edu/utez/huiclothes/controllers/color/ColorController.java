package mx.edu.utez.huiclothes.controllers.color;


import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.color.ColorBean;
import mx.edu.utez.huiclothes.services.color.ColorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/color")
@CrossOrigin({"*"})
@AllArgsConstructor
public class ColorController {

    private final ColorService service;


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
    public ResponseEntity<ApiResponse> save(@RequestBody ColorBean colorBean){
        return service.save(colorBean);
    }


    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody ColorBean colorBean){
        return service.update(colorBean);
    }

}
