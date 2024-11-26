package mx.edu.utez.huiclothes.services.order;

import mx.edu.utez.huiclothes.models.order.OrderBean;
import mx.edu.utez.huiclothes.models.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderBean> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<OrderBean> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public OrderBean saveOrder(OrderBean order) {
        return orderRepository.save(order);
    }

    public boolean deleteOrder(Long id) {
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<OrderBean> getOrdersByStatus(String status) {
        return orderRepository.findByStatus(status);
    }
}
