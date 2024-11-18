package mx.edu.utez.huiclothes.models.color;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<ColorBean,Long> {
}
