package mx.edu.utez.huiclothes.models.stockControl;


import jakarta.persistence.*;
import lombok.Data;
import mx.edu.utez.huiclothes.models.color.ColorBean;
import mx.edu.utez.huiclothes.models.products.ProductBean;
import mx.edu.utez.huiclothes.models.size.SizeBean;

@Entity
@Table(name = "stock_control")
@Data
public class StockControlBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long stock;

    @ManyToOne
    @JoinColumn(name = "products_id_products")
    private ProductBean product;

    @ManyToOne
    @JoinColumn(name = "size_id_size")
    private SizeBean size;

    @ManyToOne
    @JoinColumn(name = "colors_id_colors")
    private ColorBean color;


}
