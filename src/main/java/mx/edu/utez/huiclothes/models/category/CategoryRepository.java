package mx.edu.utez.huiclothes.models.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryBean, Integer> {

    Optional<CategoryBean> findById(Long id);
    Optional<CategoryBean> findByName(String name); // Buscar categoría por nombre
}
