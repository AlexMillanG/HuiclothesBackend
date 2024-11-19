package mx.edu.utez.huiclothes.services.color;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.color.ColorBean;
import mx.edu.utez.huiclothes.models.color.ColorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = SQLException.class)
public class ColorService {

    private final ColorRepository repository;

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findAll(){
        return new ResponseEntity<>(new ApiResponse(repository.findAll(),HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findOne(Long id){
        Optional<ColorBean> foundColor = repository.findById(id);
        if (foundColor.isEmpty())
            return new ResponseEntity<>(new ApiResponse("Error, el color que buscas no existe",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(new ApiResponse(foundColor.get(),HttpStatus.OK),HttpStatus.OK);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> deleteById(Long id){
        Optional<ColorBean> foundColor = repository.findById(id);
        if (foundColor.isEmpty())
            return new ResponseEntity<>(new ApiResponse("Error, el color que intentas eliminar no existe",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);
            repository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse("color elinado con exito",HttpStatus.OK),HttpStatus.OK);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> save(ColorBean colorBean){
        if (colorBean.getColorName().equals("") || colorBean.getColorName().equals(null))
            return new ResponseEntity<>(new ApiResponse("Error, inserta un nombre de color valido",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(new ApiResponse("Color guardado con exito",false,HttpStatus.OK,repository.saveAndFlush(colorBean)),HttpStatus.OK);
    }


    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> update(ColorBean colorBean){

        Optional<ColorBean> foundColor = repository.findById(colorBean.getId());
        if (foundColor.isEmpty())
            return new ResponseEntity<>(new ApiResponse("Error, el color que intentas eliminar no existe",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);

        if (colorBean.getColorName().equals("") || colorBean.getColorName().equals(null))
        return new ResponseEntity<>(new ApiResponse("Error, inserta un nombre de color valido",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ApiResponse("Color actualizado con exito",false,HttpStatus.OK,repository.saveAndFlush(colorBean)),HttpStatus.OK);
    }

}
