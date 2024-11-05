package mx.edu.utez.huiclothes.models.address;
//este es el bueno
import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.user.UserBean;

@Data
@Entity
@Table(name = "address")
public class AddressBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "street", length = 50)
    private String street;

    @Column(name = "country", length = 45)
    private String country;

    @Column(name = "state", length = 45)
    private String state;

    @Column(name = "zip_code", length = 6)
    private String zipCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserBean userBean;
}
