package mx.edu.utez.huiclothes.models.adrress;

import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.user.UserBean;

@Data
@Entity
@Table(name = "address")
public class AddressBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAddress;

    @Column(name = "street", length = 45)
    private String street;

    @Column(name = "country", length = 45)
    private String country;

    @Column(name = "state", length = 45)
    private String state;

    @Column(name = "zip_code", length = 45)
    private String zipCode;

    @ManyToOne
    @JoinColumn(name = "user_iduser")
    private UserBean user;
}

