package mx.edu.utez.huiclothes.controllers.product.dto;

import lombok.Data;
import mx.edu.utez.huiclothes.models.category.CategoryBean;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String gender;  // Recibe un String en lugar de Gender
    private CategoryBean category;
}
