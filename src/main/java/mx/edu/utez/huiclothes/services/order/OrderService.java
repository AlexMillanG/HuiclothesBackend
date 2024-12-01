package mx.edu.utez.huiclothes.services.order;

import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.controllers.address.dto.AddressDto;
import mx.edu.utez.huiclothes.models.address.AddressBean;
import mx.edu.utez.huiclothes.models.address.AddressRepository;
import mx.edu.utez.huiclothes.models.color.ColorRepository;
import mx.edu.utez.huiclothes.models.order.OrderBean;
import mx.edu.utez.huiclothes.models.order.OrderRepository;
import mx.edu.utez.huiclothes.models.order.dto.OrderDto;
import mx.edu.utez.huiclothes.models.order.dto.SalesDto;
import mx.edu.utez.huiclothes.models.products.ProductBean;
import mx.edu.utez.huiclothes.models.products.ProductRepository;
import mx.edu.utez.huiclothes.models.size.SizeRepository;
import mx.edu.utez.huiclothes.models.stockControl.StockControlBean;
import mx.edu.utez.huiclothes.models.stockControl.StockControlRepository;
import mx.edu.utez.huiclothes.models.user.UserBean;
import mx.edu.utez.huiclothes.models.user.UserRepository;
import mx.edu.utez.huiclothes.models.user.dto.UserDto;
import mx.edu.utez.huiclothes.services.email.EmailServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

@Transactional(rollbackFor = SQLException.class)
@AllArgsConstructor
@Service
public class OrderService {

    private final OrderRepository repository;
    private final StockControlRepository stockControlRepository;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final EmailServiceImpl emailService;
    private final ProductRepository productRepository;

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> saveOrder(OrderBean orderBean) throws MessagingException {

        Optional<UserBean> foundUser = userRepository.findById(orderBean.getUserBean().getId());
        if (foundUser.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, el usuario que intenta crear la orden, no existe", true, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND);

        UserDto userDto = new UserDto(foundUser.get());

        Optional<AddressBean> foundAddress = addressRepository.findById(orderBean.getAddressBean().getId());
        if (foundAddress.isEmpty())
            return new ResponseEntity<>(new ApiResponse("error, la dirección que intentas asociar a la orden no existe", true, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND);

        AddressDto addressDto = new AddressDto(foundAddress.get());

        Set<StockControlBean> updatedStockControls = new HashSet<>();
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
            updatedStockControls.add(existingStockControl);
        }

        // Actualizar las relaciones bidireccionales
        for (StockControlBean updatedStockControl : updatedStockControls) {
            updatedStockControl.getOrderBeans().add(orderBean); // Agregar la orden al stock
        }
        orderBean.setStockControlBeans(updatedStockControls); // Asignar el stock actualizado a la orden
        orderBean.setDate(LocalDate.now());
        orderBean.setTotal(total);
        orderBean.setStatus("COMMITED");

        // Guardar la orden
        OrderBean savedOrder = repository.save(orderBean);


        Set<ProductBean> productBeans = new HashSet<>();
        for (StockControlBean stockControlBean: orderBean.getStockControlBeans()){
            ProductBean productBean = new ProductBean();

            productBean = productRepository.getById(stockControlBean.getProduct().getId());
            productBeans.add(productBean);
        }


        List<Map<String, Object>> productos = productBeans.stream().map(product -> {
            Map<String, Object> producto = new HashMap<>();
            producto.put("name", product.getName());
            producto.put("description", product.getDescription());
            producto.put("price", product.getPrice());
            return producto;
        }).toList();




        emailService.sendMail(savedOrder, productos, userDto);


        // Guardar los cambios en stock_control
        for (StockControlBean updatedStockControl : updatedStockControls) {
            stockControlRepository.save(updatedStockControl);
        }

        // Crear el DTO para la respuesta
        OrderDto orderDto = new OrderDto(savedOrder, addressDto, userDto, savedOrder.getStockControlBeans());

        return new ResponseEntity<>(new ApiResponse(
                "Orden guardada con éxito.",
                false,
                HttpStatus.CREATED,
                orderDto
        ), HttpStatus.CREATED);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findByUserId(Long id){
        Optional<UserBean> foundUser = userRepository.findById(id);

        if (foundUser.isEmpty())
            return new ResponseEntity<>(new ApiResponse("no se econtró el usuario",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);

        List<OrderBean> foundOrders = repository.findByUserBean(foundUser.get());

        if (foundOrders.isEmpty())
            return new ResponseEntity<>(new ApiResponse("el usuario proporcionado no cuenta con ordenes",true,HttpStatus.OK,null),HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new ApiResponse(foundOrders,HttpStatus.OK),HttpStatus.OK);
    }

    @Transactional(rollbackFor = SQLException.class)
    public Double getTodayTotalSales() {
        return repository.findTodayTotalSales();
    }

    @Transactional(rollbackFor = SQLException.class)
    public Double getMonthlyTotalSales() {
        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        return repository.findMonthlyTotalSales(startOfMonth);
    }

    @Transactional(rollbackFor = SQLException.class)
    public Double getYearlyTotalSales() {
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);
        return repository.findYearlyTotalSales(startOfYear);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> getSalesStats(){

        LocalDate startOfMonth = LocalDate.now().withDayOfMonth(1);
        LocalDate startOfYear = LocalDate.now().withDayOfYear(1);

        SalesDto salesDto = new SalesDto();
        salesDto.setYear(repository.findYearlyTotalSales(startOfYear));
        salesDto.setMonth(repository.findMonthlyTotalSales(startOfMonth));
        salesDto.setToday(repository.findTodayTotalSales());

         return new ResponseEntity<>(new ApiResponse(salesDto,HttpStatus.OK),HttpStatus.OK);

    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> mostSoldProduct(){
        return new ResponseEntity<>(new ApiResponse(repository.findMostSoldProduct(),HttpStatus.OK),HttpStatus.OK);
    }


    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> historicalSale(){
        SalesDto salesDto = new SalesDto();
        salesDto.setTotal(repository.findTotalSales());;
        return new ResponseEntity<>(new ApiResponse(salesDto,HttpStatus.OK),HttpStatus.OK);
    }



    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> cancelOrder(Long id){

        Optional<OrderBean> foundOrder = repository.findById(id);

        if (foundOrder.isEmpty())
            return new ResponseEntity<>(new ApiResponse("no se encontró la orden que quieres cancelar",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);

        OrderBean orderBean = foundOrder.get();

        orderBean.setStatus("CANCELED");
        return new ResponseEntity<>(new ApiResponse("orden cancelada con exito",false,HttpStatus.OK,repository.saveAndFlush(orderBean)),HttpStatus.OK);
    }

}