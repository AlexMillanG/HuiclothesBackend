package mx.edu.utez.huiclothes.models.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.category.CategoryBean;
import mx.edu.utez.huiclothes.models.image.ImageBean;
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
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column (nullable = false)
    private double price;
    @Column(columnDefinition = "DATE")
    private LocalDate entry_date;

    @Enumerated(EnumType.STRING)
    @Column(length = 10, nullable = false)
    private Gender gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryBean category;

    @OneToMany(mappedBy = "productBean")

    private List<ImageBean> images = new ArrayList<>();


}
