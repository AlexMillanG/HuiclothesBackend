package mx.edu.utez.huiclothes.controllers.order;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.order.OrderBean;
import mx.edu.utez.huiclothes.services.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin({"*"})
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> createOrder(@RequestBody OrderBean orderBean){
        return orderService.saveOrder(orderBean);
    }

    @GetMapping("/findByUser/{id}")
    public ResponseEntity<ApiResponse> findByUserId(@PathVariable Long id){
        return orderService.findByUserId(id);
    }

    @GetMapping("/findStats")
    public ResponseEntity<ApiResponse> findStats(){
        return orderService.getSalesStats();
    }

    @GetMapping("/findMostSoldProduct")
    public ResponseEntity<ApiResponse> mostSoldProduct(){
        return orderService.mostSoldProduct();
    }

    @GetMapping("/findHistoricalSale")
    public ResponseEntity<ApiResponse> historicalSale(){
        return orderService.historicalSale();
    }


}
