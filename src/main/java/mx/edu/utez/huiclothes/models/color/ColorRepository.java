package mx.edu.utez.huiclothes.models.color;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<ColorBean,Long> {

    Optional<ColorBean> findByColorName(String s);
}
