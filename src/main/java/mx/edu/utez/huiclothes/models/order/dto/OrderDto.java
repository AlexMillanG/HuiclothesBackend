package mx.edu.utez.huiclothes.models.order.dto;

import lombok.Data;
import mx.edu.utez.huiclothes.controllers.address.dto.AddressDto;
import mx.edu.utez.huiclothes.models.order.OrderBean;
import mx.edu.utez.huiclothes.models.stockControl.StockControlBean;
import mx.edu.utez.huiclothes.models.stockControl.StockControlRepository;
import mx.edu.utez.huiclothes.models.user.dto.UserDto;

import java.time.LocalDate;
import java.util.Set;

@Data
public class OrderDto {
    private Long id;
    private Double total;
    private String status;
    private LocalDate date;
    private AddressDto address;  // DTO para la dirección asociada
    private UserDto userDto;
    private Set<StockControlBean> stockControlBeans;
    private StockControlRepository stockControlRepository;

    // Constructor que convierte la entidad OrderBean a OrderDto
    public OrderDto(OrderBean order, AddressDto addressDto, UserDto userDto, Set<StockControlBean> stockControlBeans) {

        for (StockControlBean stockControlBean : stockControlBeans){
            stockControlBean = stockControlRepository.findById(stockControlBean.getId()).get();
        }


        this.id = order.getId();
        this.total = order.getTotal();
        this.status = order.getStatus();
        this.date = order.getDate();
        this.userDto = userDto;
        this.address = addressDto;
        this.stockControlBeans = stockControlBeans;
    }
}
