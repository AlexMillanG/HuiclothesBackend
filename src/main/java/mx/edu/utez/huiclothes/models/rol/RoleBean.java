package mx.edu.utez.huiclothes.models.rol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.user.UserBean;

import java.util.Set;

@Entity
@Table(name = "role")
@Data
public class RoleBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "rol",fetch = FetchType.EAGER)
    private Set<UserBean> users;



}
