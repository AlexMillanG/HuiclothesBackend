package mx.edu.utez.huiclothes.models.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.products.ProductBean;

import java.util.List;

@Data
@Entity
@Table(name = "category")
public class CategoryBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCategory;

    @Column(name = "name", length = 45)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProductBean> productBeans;
}
