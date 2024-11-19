package mx.edu.utez.huiclothes.models.color;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.context.annotation.Bean;

@Entity
@Table(name = "colors")
@Data
public class ColorBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String colorName;
}
