package mx.edu.utez.huiclothes.models.category;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.products.ProductBean;

import java.util.Set;

@Data
@Entity
@Table(name = "category")
public class CategoryBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCategory;

    @Column(name = "name", length = 45)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "categoryBean",fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    private Set<ProductBean> productBeans;

    @ManyToOne
    @JoinColumn(name = "idUser")
    private ProductBean productBean;
}

