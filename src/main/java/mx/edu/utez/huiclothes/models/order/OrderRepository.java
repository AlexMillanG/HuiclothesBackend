package mx.edu.utez.huiclothes.models.order;

import mx.edu.utez.huiclothes.models.order.OrderBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderBean, Long> {

    // Consulta personalizada para buscar órdenes por estado
    List<OrderBean> findByStatus(String status);
}