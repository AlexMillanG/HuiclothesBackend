package mx.edu.utez.huiclothes.models.person;
//este es el bueno

import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.user.UserBean;

@Entity
@Table(name = "person")
@Data
public class PersonBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100, nullable = false)
    private String lastname;
    @Column(length = 100)
    private String surname;


    @OneToOne(mappedBy = "person")
    private UserBean user;

}
