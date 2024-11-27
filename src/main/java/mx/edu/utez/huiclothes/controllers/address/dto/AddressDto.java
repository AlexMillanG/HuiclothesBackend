package mx.edu.utez.huiclothes.controllers.address.dto;

import lombok.Data;
import mx.edu.utez.huiclothes.models.address.AddressBean;
import mx.edu.utez.huiclothes.models.user.UserBean;

@Data
public class AddressDto {

    Long id;
    String street;
    String country;
    String state;
    String zipCode;
    String phoneNumber;
    String neighborhood;
    String fullName;
    String province;

    UserBean userBean;

    public AddressBean toEntity(){
        return  new AddressBean(id,street,country,state,zipCode,phoneNumber,neighborhood,fullName,province,userBean);
    }
}
