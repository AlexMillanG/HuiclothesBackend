package mx.edu.utez.huiclothes.controllers.StockControl;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.stockControl.StockControlBean;
import mx.edu.utez.huiclothes.services.stockControl.StockControlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stockControl")
@CrossOrigin({"*"})
@AllArgsConstructor
public class StockControlController {

    private final StockControlService service;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody StockControlBean stockControlBean){
        return service.save(stockControlBean);
    }

    @GetMapping("/findByProduct/{id}")
    public ResponseEntity<ApiResponse> findByProduct(@PathVariable Long id){
        return service.findByProduct(id);
    }

    @PutMapping("/updateStock")
    public ResponseEntity<ApiResponse> updateStock(@RequestBody StockControlBean stockControlBean){
        return service.updateStock(stockControlBean);
    }

    @DeleteMapping("/deleteStockControl/{id}")
    public ResponseEntity<ApiResponse> deleteStockControl(@PathVariable Long id){
        return service.delete(id);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> findAll() {
        return service.findAll();
    }

}
