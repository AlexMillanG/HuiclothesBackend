package mx.edu.utez.huiclothes.models.user;
//este es el bueno
import jakarta.persistence.*;
import lombok.*;
import mx.edu.utez.huiclothes.models.address.AddressBean;
import mx.edu.utez.huiclothes.models.log.LogBean;
import mx.edu.utez.huiclothes.models.person.PersonBean;
import mx.edu.utez.huiclothes.models.rol.RoleBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"addressBeans"})

public class UserBean implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;

    @OneToMany(mappedBy = "userBean", cascade = CascadeType.ALL)
    private Set<AddressBean> addressBeans;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "rol_id")
    private RoleBean rol;

    @OneToOne
    @JoinColumn(name = "person_id")
    private PersonBean person;



    //chingaderas de seguridad
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rol.getName()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
