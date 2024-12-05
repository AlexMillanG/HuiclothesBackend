package mx.edu.utez.huiclothes.models.order;

import mx.edu.utez.huiclothes.models.order.OrderBean;
import mx.edu.utez.huiclothes.models.order.dto.OrderDto;
import mx.edu.utez.huiclothes.models.products.ProductBean;
import mx.edu.utez.huiclothes.models.user.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface OrderRepository extends JpaRepository<OrderBean, Long> {

    // Consulta personalizada para buscar órdenes por estado
    List<OrderBean> findByStatus(String status);

    List<OrderBean> findByUserBean(UserBean u);

    @Query("SELECT SUM(o.total) FROM OrderBean o WHERE o.date = CURRENT_DATE")
    Double findTodayTotalSales();

    @Query("SELECT SUM(o.total) FROM OrderBean o WHERE o.date BETWEEN :startOfMonth AND CURRENT_DATE")
    Double findMonthlyTotalSales(@Param("startOfMonth") LocalDate startOfMonth);

    @Query("SELECT SUM(o.total) FROM OrderBean o WHERE o.date BETWEEN :startOfYear AND CURRENT_DATE")
    Double findYearlyTotalSales(@Param("startOfYear") LocalDate startOfYear);

    @Query("SELECT p.name " +
            "FROM OrderBean o " +
            "JOIN o.stockControlBeans scb " +
            "JOIN scb.product p " +
            "GROUP BY p.name " +
            "ORDER BY COUNT(o.id) DESC")
    String findMostSoldProductName();


    @Query("SELECT p " +
            "FROM OrderBean o " +
            "JOIN o.stockControlBeans scb " +
            "JOIN scb.product p " +
            "GROUP BY p.id " +
            "ORDER BY COUNT(o.id) DESC")
    ProductBean findMostSoldProduct();

    @Query("SELECT SUM(o.total) FROM OrderBean o")
    Double findTotalSales();





}