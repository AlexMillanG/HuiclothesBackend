package mx.edu.utez.huiclothes.models.order;

import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.log.LogBean;
import mx.edu.utez.huiclothes.models.products.ProductBean;

import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "orders")
@Data
public class OrderBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "DATE", nullable = true)
    private LocalDate date;
    @Column (nullable = false)
    private double total;
    @Column(length = 100, nullable = false)
    private String ordercol;

    @ManyToMany(mappedBy = "orderBeans", cascade = CascadeType.MERGE)
    private Set<ProductBean> productBeans;

    @OneToMany(mappedBy = "orderBean", cascade = CascadeType.ALL)
    private Set<LogBean> logBeans;

}
