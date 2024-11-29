package mx.edu.utez.huiclothes.models.image;

import mx.edu.utez.huiclothes.models.products.ProductBean;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<ImageBean,Long> {

    List<ImageBean> findByProductBean(ProductBean productBean);
}
