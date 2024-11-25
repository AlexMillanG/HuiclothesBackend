package mx.edu.utez.huiclothes.services.image;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.color.ColorBean;
import mx.edu.utez.huiclothes.models.color.ColorRepository;
import mx.edu.utez.huiclothes.models.image.ImageBean;
import mx.edu.utez.huiclothes.models.image.ImageRepository;
import mx.edu.utez.huiclothes.models.products.ProductBean;
import mx.edu.utez.huiclothes.models.products.ProductRepository;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(rollbackFor = SQLException.class)
@AllArgsConstructor
public class ImageService {

    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final ImageRepository imageRepository;


    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> associateImagesWithFiles(Long productId, Long colorId, List<MultipartFile> files) {
        Optional<ProductBean> foundProduct = productRepository.findById(productId);
        if (foundProduct.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse("Error: El producto no existe", true, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND);
        }

        Optional<ColorBean> foundColor = colorRepository.findById(colorId);
        if (foundColor.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse("Error: El color no existe o no pertenece al producto especificado", true, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);
        }

        List<ImageBean> savedImages = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                ImageBean imageBean = new ImageBean();
                imageBean.setColorBean(foundColor.get());
                imageBean.setProductBean(foundProduct.get());
                imageBean.setImage(file.getBytes());
                savedImages.add(imageRepository.save(imageBean));
            } catch (IOException e) {
                throw new RuntimeException("Error al procesar el archivo: " + file.getOriginalFilename(), e);
            }
        }

        return new ResponseEntity<>(
                new ApiResponse("Imágenes asociadas con éxito", false, HttpStatus.OK, savedImages),
                HttpStatus.OK
        );
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findAll(){
        return new ResponseEntity<ApiResponse>(new ApiResponse(imageRepository.findAll(),HttpStatus.OK),HttpStatus.OK);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> deleteImage(Long id){
        Optional<ImageBean> foundImage = imageRepository.findById(id);

        if (foundImage.isEmpty())
            return new ResponseEntity<>(new ApiResponse("no se econtró la imágen que quieres eliminar",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);
        ImageBean imageBean = foundImage.get();
        imageRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse("eliminado exitosamete",false,HttpStatus.OK,imageBean),HttpStatus.OK);

    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findOne(Long id) {

        Optional<ImageBean> foundImage = imageRepository.findById(id);
        if (foundImage.isEmpty())
            return new ResponseEntity<>(new ApiResponse("no se econtró la imágen", true, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ApiResponse(foundImage.get(),HttpStatus.OK),HttpStatus.OK);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> updateImage(){
        return null;
    }
}
