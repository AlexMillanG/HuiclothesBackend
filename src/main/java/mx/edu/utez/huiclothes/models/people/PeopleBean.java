package mx.edu.utez.huiclothes.models.people;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "person")
public class PeopleBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPerson;

    @Column(name = "name", length = 45)
    private String name;

    @Column(name = "lastname", length = 45)
    private String lastname;

    @Column(name = "surname", length = 45)
    private String surname;

    // Getters and Setters
}
