package mx.edu.utez.huiclothes.models.address;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class AddressBean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", length = 50)
    private String street;
    @Column(name = "name", length = 45)
    private String country;
    @Column(name = "name", length = 45)
    private String state;
    @Column(name = "name", length = 6)
    private String zip_code;


}
