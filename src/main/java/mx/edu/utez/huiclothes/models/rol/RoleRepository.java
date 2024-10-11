package mx.edu.utez.huiclothes.models.rol;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository <RoleBean,Long> {
    Optionaltional<RolBean> findByRol (String rol);
}
