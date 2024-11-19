package mx.edu.utez.huiclothes.models.image;

import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.products.ProductBean;

@Data
@Entity
@Table(name = "images")
public class ImageBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImages;


    @Column(name = "image")
    private byte[] image;

    @ManyToOne
    @JoinColumn(name = "products_id_products")
    private ProductBean productBean;
}
