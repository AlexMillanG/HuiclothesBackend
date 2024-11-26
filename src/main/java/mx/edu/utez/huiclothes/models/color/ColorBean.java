package mx.edu.utez.huiclothes.models.color;

import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.image.ImageBean;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "colors")
@Data
public class ColorBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String colorName;

    private String hex;

    @OneToMany(mappedBy = "colorBean")
    private Set<ImageBean> imageBeans = new HashSet<>();
}
