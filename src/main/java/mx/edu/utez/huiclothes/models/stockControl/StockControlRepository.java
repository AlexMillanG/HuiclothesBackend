package mx.edu.utez.huiclothes.models.stockControl;

import mx.edu.utez.huiclothes.models.products.ProductBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockControlRepository extends JpaRepository<StockControlBean,Long> {

    Optional<StockControlBean> findByProduct(ProductBean productBean);
}
