package mx.edu.utez.huiclothes.models.log;
//este es el bueno

import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.order.OrderBean;

import java.time.LocalDate;

@Entity
@Table(name = "log")
@Data
public class LogBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "id_order", nullable = false)
    private OrderBean orderBean;

}
