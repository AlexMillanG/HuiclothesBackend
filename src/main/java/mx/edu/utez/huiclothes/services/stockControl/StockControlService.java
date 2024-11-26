package mx.edu.utez.huiclothes.services.stockControl;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.color.ColorBean;
import mx.edu.utez.huiclothes.models.color.ColorRepository;
import mx.edu.utez.huiclothes.models.products.ProductBean;
import mx.edu.utez.huiclothes.models.products.ProductRepository;
import mx.edu.utez.huiclothes.models.size.SizeBean;
import mx.edu.utez.huiclothes.models.size.SizeRepository;
import mx.edu.utez.huiclothes.models.stockControl.StockControlBean;
import mx.edu.utez.huiclothes.models.stockControl.StockControlRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional(rollbackFor = SQLException.class)
@AllArgsConstructor
public class StockControlService {

    private final StockControlRepository repository;
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;


    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findByProduct(Long id){

        Optional<ProductBean> foundProduct = productRepository.findById(id);
        if (foundProduct.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, el producto del cual buscas stock, no existe",true,HttpStatus.NOT_FOUND,null), HttpStatus.NOT_FOUND);

        Optional<StockControlBean> foundStockControl = repository.findByProduct(foundProduct.get());
        return foundStockControl.map(stockControlBean -> new ResponseEntity<>(new ApiResponse(stockControlBean, HttpStatus.OK), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new ApiResponse("error, no se encontraron registros de ese producto", false, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND));

    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> save(StockControlBean stockControlBean){

        Optional<ProductBean> foundProduct = productRepository.findById(stockControlBean.getProduct().getId());
        if (foundProduct.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, el producto del cual intentas ingresar stock, no existe",true,HttpStatus.NOT_FOUND,null), HttpStatus.NOT_FOUND);

        Optional<ColorBean> foundColor = colorRepository.findById(stockControlBean.getColor().getId());
        if (foundColor.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, el color que intentas agregar al manejo de stock, no existe",true,HttpStatus.NOT_FOUND,null), HttpStatus.NOT_FOUND);

        Optional<SizeBean> foundSize = sizeRepository.findById(stockControlBean.getSize().getId());
        if (foundSize.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, la talla que intentas agregar al manejo de stock, no existe",true,HttpStatus.NOT_FOUND,null), HttpStatus.NOT_FOUND);

        if (stockControlBean.getStock()<0L)
            return new ResponseEntity<>(new ApiResponse("error, ingresa una cantidad valida de stock",true,HttpStatus.NOT_FOUND,null), HttpStatus.NOT_FOUND);

        StockControlBean savedStock = repository.save(stockControlBean);
        ColorBean colorBean = foundColor.get();
        SizeBean sizeBean = foundSize.get();
        ProductBean productBean = foundProduct.get();
        StockControlBean responseStock = new StockControlBean();
        responseStock.setColor(colorBean);
        responseStock.setProduct(productBean);
        responseStock.setSize(sizeBean);
        responseStock.setStock(stockControlBean.getStock());
        responseStock.setId(savedStock.getId());
        return new ResponseEntity<>(new ApiResponse("stock guardado con éxito",false,HttpStatus.OK,responseStock), HttpStatus.OK);
    }


    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> updateStock(StockControlBean stockControlBean){
        Optional<StockControlBean> foundStockControl = repository.findById(stockControlBean.getId());

        if (foundStockControl.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, los registros de stock, no existen",true,HttpStatus.NOT_FOUND,null), HttpStatus.NOT_FOUND);

        Optional<ProductBean> foundProduct = productRepository.findById(stockControlBean.getProduct().getId());
        if (foundProduct.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, el producto del cual intentas ingresar stock, no existe",true,HttpStatus.NOT_FOUND,null), HttpStatus.NOT_FOUND);

        Optional<ColorBean> foundColor = colorRepository.findById(stockControlBean.getColor().getId());
        if (foundColor.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, el color que intentas agregar al manejo de stock, no existe",true,HttpStatus.NOT_FOUND,null), HttpStatus.NOT_FOUND);

        Optional<SizeBean> foundSize = sizeRepository.findById(stockControlBean.getSize().getId());
        if (foundSize.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, la talla que intentas agregar al manejo de stock, no existe",true,HttpStatus.NOT_FOUND,null), HttpStatus.NOT_FOUND);

        if (stockControlBean.getStock()<0L)
            return new ResponseEntity<>(new ApiResponse("error, ingresa una cantidad valida de stock",true,HttpStatus.NOT_FOUND,null), HttpStatus.NOT_FOUND);


        if (foundStockControl.get().getStock()<stockControlBean.getStock())
            return new ResponseEntity<>(new ApiResponse("error, no hay suficiente stock para la compra",true,HttpStatus.NOT_FOUND,null), HttpStatus.NOT_FOUND);

        repository.saveAndFlush(stockControlBean);
        StockControlBean responseStock = new StockControlBean();

        ColorBean colorBean = foundColor.get();
        SizeBean sizeBean = foundSize.get();
        ProductBean productBean = foundProduct.get();

        responseStock.setStock(stockControlBean.getStock());
        responseStock.setProduct(productBean);
        responseStock.setColor(colorBean);
        responseStock.setSize(sizeBean);

        return new ResponseEntity<>(new ApiResponse("stock guardado con éxito",false,HttpStatus.OK,responseStock), HttpStatus.OK);
    }


    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> delete(Long id){
        Optional<StockControlBean> foundStockControl = repository.findById(id);

        if (foundStockControl.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, los registros de stock, no existen",true,HttpStatus.NOT_FOUND,null), HttpStatus.NOT_FOUND);
        StockControlBean stockControlBean = foundStockControl.get();

        repository.deleteById(id);
            return new ResponseEntity<>(new ApiResponse("registro de stock eliminado",false,HttpStatus.OK,stockControlBean),HttpStatus.OK);
    }


    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findAll(){
        return new ResponseEntity<>(new ApiResponse(repository.findAll(),HttpStatus.OK), HttpStatus.OK);
    }

}
