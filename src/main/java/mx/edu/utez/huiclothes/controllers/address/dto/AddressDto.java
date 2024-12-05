package mx.edu.utez.huiclothes.controllers.address.dto;

import lombok.Data;
import mx.edu.utez.huiclothes.models.address.AddressBean;
import mx.edu.utez.huiclothes.models.user.UserBean;
import mx.edu.utez.huiclothes.models.user.dto.UserDto;

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

    public AddressDto() {

    }


    UserDto userDto;  // Cambié UserBean a UserDto

    // Constructor que convierte la entidad AddressBean a AddressDto
    public AddressDto(AddressBean addressBean) {
        this.id = addressBean.getId();
        this.street = addressBean.getStreet();
        this.country = addressBean.getCountry();
        this.state = addressBean.getState();
        this.zipCode = addressBean.getZipCode();
        this.phoneNumber = addressBean.getPhoneNumber();
        this.neighborhood = addressBean.getNeighborhood();
        this.fullName = addressBean.getFullName();
        this.province = addressBean.getProvince();
    }


}
