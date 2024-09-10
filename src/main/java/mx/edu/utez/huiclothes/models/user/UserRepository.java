package mx.edu.utez.huiclothes.models.user;

import mx.edu.utez.huiclothes.models.rol.RoleBean;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<RoleBean, Long> {
}
