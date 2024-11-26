package mx.edu.utez.huiclothes.services.order;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.color.ColorRepository;
import mx.edu.utez.huiclothes.models.order.OrderBean;
import mx.edu.utez.huiclothes.models.order.OrderRepository;
import mx.edu.utez.huiclothes.models.products.ProductRepository;
import mx.edu.utez.huiclothes.models.size.SizeRepository;
import mx.edu.utez.huiclothes.models.stockControl.StockControlBean;
import mx.edu.utez.huiclothes.models.stockControl.StockControlRepository;
import mx.edu.utez.huiclothes.models.user.UserBean;
import mx.edu.utez.huiclothes.models.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Transactional(rollbackFor = SQLException.class)
@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repository;
    private final StockControlRepository stockControlRepository;
    private final ProductRepository productRepository;
    private final ColorRepository colorRepository;
    private final SizeRepository sizeRepository;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> saveOrder(OrderBean orderBean) {


        Optional<UserBean> foundUser = userRepository.findById(orderBean.getUserBean().getId());
        if (foundUser.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, el usuario que intenta crear la orden, no existe",true,HttpStatus.NOT_FOUND,null), HttpStatus.NOT_FOUND);



        List<StockControlBean> updatedStockControls = new ArrayList<>();
        double total = 0;

        for (StockControlBean stockControlBean : orderBean.getStockControlBeans()) {
            Optional<StockControlBean> optionalStockControl = stockControlRepository.findById(stockControlBean.getId());
            if (optionalStockControl.isEmpty()) {
                return new ResponseEntity<>(new ApiResponse(
                        "Error, no se encontró el registro de stock para el ID " + stockControlBean.getId(),
                        true,
                        HttpStatus.NOT_FOUND,
                        null
                ), HttpStatus.NOT_FOUND);
            }

            StockControlBean existingStockControl = optionalStockControl.get();

            if (existingStockControl.getStock() < stockControlBean.getStock()) {
                return new ResponseEntity<>(new ApiResponse(
                        "Error, el stock disponible para el producto " + existingStockControl.getProduct().getName() +
                                " es insuficiente. Disponible: " + existingStockControl.getStock() +
                                ", Solicitado: " + stockControlBean.getStock(),
                        true,
                        HttpStatus.BAD_REQUEST,
                        null
                ), HttpStatus.BAD_REQUEST);
            }

            double productPrice = existingStockControl.getProduct().getPrice();
            total += productPrice * stockControlBean.getStock();

            existingStockControl.setStock(existingStockControl.getStock() - stockControlBean.getStock());
            updatedStockControls.add(stockControlRepository.save(existingStockControl));
        }


        orderBean.setDate(LocalDate.now());
        orderBean.setTotal(total);
        orderBean.setStatus("COMMITED");

        OrderBean savedOrder = repository.save(orderBean);

        return new ResponseEntity<>(new ApiResponse(
                "Orden guardada con éxito.",
                false,
                HttpStatus.CREATED,
                savedOrder
        ), HttpStatus.CREATED);
    }


}