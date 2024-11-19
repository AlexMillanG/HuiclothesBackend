package mx.edu.utez.huiclothes.models.product;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "products")
public class ProductBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProducts;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "description", length = 45)
    private String description;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price", precision = 10, scale = 2)
    private Double price;

    @Column(name = "entry_date")
    private LocalDateTime entryDate;

    @ManyToOne
    @JoinColumn(name = "category_id_category")
    private Category category;
}
