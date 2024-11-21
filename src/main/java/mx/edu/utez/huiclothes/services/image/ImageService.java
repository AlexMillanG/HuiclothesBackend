package mx.edu.utez.huiclothes.services.image;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.color.ColorBean;
import mx.edu.utez.huiclothes.models.color.ColorRepository;
import mx.edu.utez.huiclothes.models.image.ImageBean;
import mx.edu.utez.huiclothes.models.image.ImageRepository;
import mx.edu.utez.huiclothes.models.products.ProductBean;
import mx.edu.utez.huiclothes.models.products.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = SQLException.class)
@AllArgsConstructor
public class ImageService {

    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final ImageRepository imageRepository;

    /**
     * Asociar una lista de imágenes a un producto y color.
     *
     * @param productId ID del producto.
     * @param colorId ID del color.
     * @param imageBeans Lista de objetos ImageBean (imágenes a guardar).
     * @return Respuesta con la lista de imágenes asociadas.
     */
    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> associateImages(Long productId, Long colorId, List<ImageBean> imageBeans) {
        // Verificar que el producto exista
        Optional<ProductBean> foundProduct = productRepository.findById(productId);
        if (foundProduct.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse("Error: El producto no existe", true, HttpStatus.NOT_FOUND, null),
                    HttpStatus.NOT_FOUND
            );
        }

        // Verificar que el color exista y esté asociado al producto
        Optional<ColorBean> foundColor = colorRepository.findById(colorId);
        if (foundColor.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse("Error: El color no existe o no pertenece al producto especificado", true, HttpStatus.BAD_REQUEST, null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Asociar cada imagen al color
        List<ImageBean> savedImages = imageBeans.stream().map(imageBean -> {
            imageBean.setColorBean(foundColor.get());
            return imageRepository.save(imageBean);
        }).toList();

        return new ResponseEntity<>(
                new ApiResponse("Imágenes asociadas con éxito", false, HttpStatus.OK, savedImages),
                HttpStatus.OK
        );
    }
}
