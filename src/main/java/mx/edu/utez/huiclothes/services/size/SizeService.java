package mx.edu.utez.huiclothes.services.size;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.size.SizeBean;
import mx.edu.utez.huiclothes.models.size.SizeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional(rollbackFor = SQLException.class)

public class SizeService {

    private final SizeRepository repository;

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findAll(){
        return new ResponseEntity<>(new ApiResponse(repository.findAll(), HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findOne(Long id){
        Optional<SizeBean> foundSize = repository.findById(id);
        if (foundSize.isEmpty())
        return new ResponseEntity<>(new ApiResponse("Error, la talla que buscas no existe",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ApiResponse(foundSize.get(),HttpStatus.OK),HttpStatus.OK);
    }


    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> deleteById(Long id){
        Optional<SizeBean> foundSize = repository.findById(id);
        if (foundSize.isEmpty())
        return new ResponseEntity<>(new ApiResponse("Error, la talla que intentas eliminar no existe",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);
        repository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse("talla eliminado con exito",HttpStatus.OK),HttpStatus.OK);
    }


    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> save(SizeBean sizeBean){
        if (sizeBean.getSizeName().equals("") || sizeBean.getSizeName().equals(null))
        return new ResponseEntity<>(new ApiResponse("Error, inserta un nombre de talla valido",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ApiResponse("talla guardada con exito",false,HttpStatus.OK,repository.saveAndFlush(sizeBean)),HttpStatus.OK);
    }


    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> update(SizeBean sizeBean){

        Optional<SizeBean> foundSize = repository.findById(sizeBean.getId());
        if (foundSize.isEmpty())
            return new ResponseEntity<>(new ApiResponse("Error, la talla que intentas eliminar no existe",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);

        if (sizeBean.getSizeName().equals("") || sizeBean.getSizeName().equals(null))
        return new ResponseEntity<>(new ApiResponse("Error, inserta un nombre de talla valido",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ApiResponse("talla actualizado con exito",false,HttpStatus.OK,repository.saveAndFlush(sizeBean)),HttpStatus.OK);
    }
}
