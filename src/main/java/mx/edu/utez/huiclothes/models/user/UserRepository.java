package mx.edu.utez.huiclothes.models.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserBean, Long> {

    Optional<UserBean> findByEmail(String email);
}
