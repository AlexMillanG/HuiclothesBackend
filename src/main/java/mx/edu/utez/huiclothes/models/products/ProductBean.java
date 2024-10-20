package mx.edu.utez.huiclothes.models.products;

import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.image.ImageBean;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Data
public class ProductBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int quantity;
    private double price;
    private LocalDate entry_date;

    @OneToMany(mappedBy = "productBean")
    private Set<ImageBean> imagenes = new HashSet<>();

}
