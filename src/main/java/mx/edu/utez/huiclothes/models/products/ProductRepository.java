package mx.edu.utez.huiclothes.models.products;

import mx.edu.utez.huiclothes.models.category.CategoryBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<ProductBean, Long> {
    Optional<ProductBean> findById(Long id);
    List<ProductBean> findByCategory(CategoryBean category);
}
