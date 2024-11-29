package mx.edu.utez.huiclothes.models.order;

import mx.edu.utez.huiclothes.models.order.OrderBean;
import mx.edu.utez.huiclothes.models.order.dto.OrderDto;
import mx.edu.utez.huiclothes.models.user.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderBean, Long> {

    // Consulta personalizada para buscar órdenes por estado
    List<OrderBean> findByStatus(String status);

    List<OrderBean> findByUserBean(UserBean u);




}