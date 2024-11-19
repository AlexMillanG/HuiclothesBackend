package mx.edu.utez.huiclothes.models.size;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sizes")
@Data
public class SizeBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sizeName;
}
