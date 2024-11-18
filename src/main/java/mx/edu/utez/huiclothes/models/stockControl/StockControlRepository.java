package mx.edu.utez.huiclothes.models.stockControl;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockControlRepository extends JpaRepository<StockControlBean,Long> {
}
