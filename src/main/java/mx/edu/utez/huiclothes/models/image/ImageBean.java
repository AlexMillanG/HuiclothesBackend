package mx.edu.utez.huiclothes.models.image;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.color.ColorBean;
import mx.edu.utez.huiclothes.models.products.ProductBean;

@Data
@Entity
@Table(name = "images")
public class ImageBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImages;


    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "products_id_products")
    private ProductBean productBean;


    @ManyToOne
    @JoinColumn(name = "colors_id_colors")
    private ColorBean colorBean;


}
