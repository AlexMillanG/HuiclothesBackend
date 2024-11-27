package mx.edu.utez.huiclothes.models.address;
//este es el bueno
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.huiclothes.models.user.UserBean;

@Data
@Entity
@Table(name = "address")
@NoArgsConstructor
public class AddressBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street", length = 50)
    private String street;

    @Column(name = "country", length = 45)
    private String country;

    @Column(name = "state", length = 45)
    private String state;

    @Column(name = "zip_code", length = 6)
    private String zipCode;


    public AddressBean(Long id, String street, String country, String state, String zipCode, UserBean userBean) {
        this.id = id;
        this.street = street;
        this.country = country;
        this.state = state;
        this.zipCode = zipCode;
        this.userBean = userBean;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserBean userBean;
}
