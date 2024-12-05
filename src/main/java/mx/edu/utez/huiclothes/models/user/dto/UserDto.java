package mx.edu.utez.huiclothes.models.user.dto;

import lombok.Data;
import mx.edu.utez.huiclothes.models.user.UserBean;

@Data
public class UserDto {
    private  String roleName;
    private Long id;
    private String email;
    private String personName;
    private String personLastName;

    public UserDto(UserBean user) {
        this.id = user.getId();
        this.email = user.getEmail();
        // Asumiendo que 'rol' no es null y tiene el nombre
        if (user.getRol() != null) {
            this.roleName = user.getRol().getName();
        }
        if (user.getPerson() != null) {
            this.personName = user.getPerson().getName();
            this.personLastName = user.getPerson().getLastname();
        }
    }


}
