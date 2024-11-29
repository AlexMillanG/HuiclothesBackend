package mx.edu.utez.huiclothes.models.imageCategory;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "category_images")
public class ImageCategoryBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

}
