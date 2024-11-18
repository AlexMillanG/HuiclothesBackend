package mx.edu.utez.huiclothes.models.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.category.CategoryBean;
import mx.edu.utez.huiclothes.models.order.OrderBean;
//este es el bueno
import java.time.LocalDate;
import java.util.*;

@Entity
@Table(name = "products")
@Data
public class ProductBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100, nullable = false)
    private String description;
    @Column (nullable = false)
    private double price;
    @Column(columnDefinition = "DATE", nullable = true)
    private LocalDate entry_date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryBean category;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "products_has_orders",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<OrderBean> orderBeans;

}
