package mx.edu.utez.huiclothes.controllers.order;

import lombok.AllArgsConstructor;
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
public class  OrderController {

    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderBean>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderBean> getOrderById(@PathVariable Long id) {
        Optional<OrderBean> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderBean> saveOrder(@RequestBody OrderBean order) {
        return ResponseEntity.ok(orderService.saveOrder(order));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        if (orderService.deleteOrder(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderBean>> getOrdersByStatus(@PathVariable String status) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }
}
