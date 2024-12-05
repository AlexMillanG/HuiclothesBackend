package mx.edu.utez.huiclothes.models.stockControl;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import mx.edu.utez.huiclothes.models.color.ColorBean;
import mx.edu.utez.huiclothes.models.order.OrderBean;
import mx.edu.utez.huiclothes.models.products.ProductBean;
import mx.edu.utez.huiclothes.models.size.SizeBean;

import java.util.Set;

@Entity
@Table(name = "stock_control")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class StockControlBean {

    @Id
    @EqualsAndHashCode.Include

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long stock;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "products_id_products")
    private ProductBean product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "size_id_size")
    private SizeBean size;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "colors_id_colors")
    private ColorBean color;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "stock_control_has_orders",
            joinColumns = @JoinColumn(name = "stock_control_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<OrderBean> orderBeans;

}
