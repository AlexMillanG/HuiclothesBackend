package mx.edu.utez.huiclothes.services.address;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.controllers.address.dto.AddressDto;
import mx.edu.utez.huiclothes.models.address.AddressBean;
import mx.edu.utez.huiclothes.models.address.AddressRepository;
import mx.edu.utez.huiclothes.models.user.UserBean;
import mx.edu.utez.huiclothes.models.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> createAddress(AddressBean addressBean) {
        System.err.println("en el save del service");
        Optional<UserBean> foundUser = userRepository.findById(addressBean.getUserBean().getId());
        if (foundUser.isEmpty())
        return new ResponseEntity<>(new ApiResponse("no existe el usuario al que intentas asociar la  dirección", false, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND);

        if (addressBean.getCountry().equals("") || addressBean.getCountry().equals(null))
            return new ResponseEntity<>(new ApiResponse("ingresa un país valido", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        if (addressBean.getStreet().equals("") || addressBean.getStreet().equals(null))
            return new ResponseEntity<>(new ApiResponse("ingresa una calle valida", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        if (addressBean.getState().equals("") || addressBean.getState().equals(null))
            return new ResponseEntity<>(new ApiResponse("ingresa un estado valido", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        if (addressBean.getZipCode().length()<5 || addressBean.getZipCode().length()>5) //62460
            return new ResponseEntity<>(new ApiResponse("ingresa un codigo postal valido", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        if (addressBean.getNeighborhood().equals("") || addressBean.getNeighborhood().equals(null))
            return new ResponseEntity<>(new ApiResponse("ingresa un estado valido", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        if (addressBean.getPhoneNumber().equals("") || addressBean.getPhoneNumber().equals(null)  || addressBean.getPhoneNumber().length()>10 || addressBean.getPhoneNumber().length()<10)
            return new ResponseEntity<>(new ApiResponse("ingresa un telefono valido", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        if (addressBean.getFullName().equals("") || addressBean.getFullName().equals(null))
            return new ResponseEntity<>(new ApiResponse("ingresa un nombre completo para la orden, valido", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);


        if (addressBean.getProvince().equals("") || addressBean.getProvince().equals(null))
            return new ResponseEntity<>(new ApiResponse("ingresa una provincia (municipio) valido", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        AddressBean savedAddress = addressRepository.save(addressBean);

        AddressDto  addressDto = new AddressDto(savedAddress);
        System.err.println(addressDto);

        System.err.println("antes de la response");
        return new ResponseEntity<>(new ApiResponse("Address created", false, HttpStatus.OK, savedAddress,foundUser.get().getId()), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> getAddressById(Long id) {
        return addressRepository.findById(id)
                .map(address -> new ResponseEntity<>(new ApiResponse("Address found", false, HttpStatus.OK, address), HttpStatus.OK))
                .orElse(new ResponseEntity<>(new ApiResponse("Address not found", true, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND));
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> updateAddress( AddressBean addressBean) {
        Optional<AddressBean> foundAddress = addressRepository.findById(addressBean.getId());

        if (foundAddress.isEmpty())
            return new ResponseEntity<>(new ApiResponse("no existe la dirección que intentas actualizar", false, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND);

        Optional<UserBean> foundUser = userRepository.findById(addressBean.getUserBean().getId());
        if (foundUser.isEmpty())
            return new ResponseEntity<>(new ApiResponse("no existe el usuario al que intentas asociar la  dirección", false, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND);

        if (addressBean.getCountry().equals("") || addressBean.getCountry().equals(null))
            return new ResponseEntity<>(new ApiResponse("ingresa un país valido", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        if (addressBean.getStreet().equals("") || addressBean.getStreet().equals(null))
            return new ResponseEntity<>(new ApiResponse("ingresa una calle valida", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        if (addressBean.getState().equals("") || addressBean.getState().equals(null))
            return new ResponseEntity<>(new ApiResponse("ingresa un estado valido", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        if (addressBean.getZipCode().length()<5 || addressBean.getZipCode().length()>5) //62460
            return new ResponseEntity<>(new ApiResponse("ingresa un codigo postal valido", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        if (addressBean.getNeighborhood().equals("") || addressBean.getNeighborhood().equals(null))
            return new ResponseEntity<>(new ApiResponse("ingresa un estado valido", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        if (addressBean.getPhoneNumber().equals("") || addressBean.getPhoneNumber().equals(null)  || addressBean.getPhoneNumber().length()>10 || addressBean.getPhoneNumber().length()<10)
            return new ResponseEntity<>(new ApiResponse("ingresa un telefono valido (10 caractéres)", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        if (addressBean.getFullName().equals("") || addressBean.getFullName().equals(null))
            return new ResponseEntity<>(new ApiResponse("ingresa un nombre completo para la orden, valido", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        if (addressBean.getProvince().equals("") || addressBean.getProvince().equals(null))
            return new ResponseEntity<>(new ApiResponse("ingresa una provincia (municipio) valido", false, HttpStatus.BAD_REQUEST, null), HttpStatus.BAD_REQUEST);

        AddressBean savedAddress = addressRepository.save(addressBean);
        return new ResponseEntity<>(new ApiResponse("Address created", false, HttpStatus.OK, savedAddress,foundUser.get().getId()), HttpStatus.OK);

    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            return new ResponseEntity<>(new ApiResponse("Address not found", true, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND);
        }

        addressRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse("Address deleted", false, HttpStatus.OK, null), HttpStatus.OK);
    }



    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> findByUserId(Long id){
        Optional<UserBean> foundUser = userRepository.findById(id);

        if (foundUser.isEmpty())
            return new ResponseEntity<>(new ApiResponse("no existe el usuario por el que intentas buscar direcciones", false, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND);

        List<AddressBean> foundAddress = addressRepository.findByUserBean(foundUser.get());

        if (foundAddress.isEmpty())
            return new ResponseEntity<>(new ApiResponse("el usuario " + foundUser.get().getPerson().getName()+  " " + foundUser.get().getPerson().getLastname() +" no tiene direcciones registradas", false, HttpStatus.OK, null), HttpStatus.OK);

        return new ResponseEntity<>(new ApiResponse(foundAddress,HttpStatus.OK),HttpStatus.OK);
    }


    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> findAll(){
        return new ResponseEntity<>(new ApiResponse(addressRepository.findAll(),HttpStatus.OK),HttpStatus.OK);
    }







    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> getAddressesByCountry(String country) {
        List<AddressBean> addresses = addressRepository.findByCountry(country);
        return new ResponseEntity<>(new ApiResponse("Addresses found", false, HttpStatus.OK, addresses), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> getAddressesByState(String state) {
        List<AddressBean> addresses = addressRepository.findByState(state);
        return new ResponseEntity<>(new ApiResponse("Addresses found", false, HttpStatus.OK, addresses), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> getAddressesByZipCode(String zipCode) {
        List<AddressBean> addresses = addressRepository.findByZipCode(zipCode);
        return new ResponseEntity<>(new ApiResponse("Addresses found", false, HttpStatus.OK, addresses), HttpStatus.OK);
    }

}

