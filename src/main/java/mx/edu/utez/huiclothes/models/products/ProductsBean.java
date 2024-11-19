package mx.edu.utez.huiclothes.models.products;


import lombok.Data;

@Entity
@Table(name = "products")
@Data
public class ProductsBean {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int quantity;
    private double price;
    private LocalDate entry_date;

}
