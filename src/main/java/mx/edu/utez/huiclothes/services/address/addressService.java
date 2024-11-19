package mx.edu.utez.huiclothes.services.address;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.address.AddressBean;
import mx.edu.utez.huiclothes.models.address.AddressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class addressService {

    private final AddressRepository addressRepository;

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> createAddress(AddressBean addressBean) {
        AddressBean savedAddress = addressRepository.save(addressBean);
        return new ResponseEntity<>(new ApiResponse("Address created", false, HttpStatus.OK, savedAddress), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> getAddressById(Long id) {
        return addressRepository.findById(id)
                .map(address -> new ResponseEntity<>(new ApiResponse("Address found", false, HttpStatus.OK, address), HttpStatus.OK))
                .orElse(new ResponseEntity<>(new ApiResponse("Address not found", true, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND));
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> updateAddress(Long id, AddressBean updatedAddress) {
        Optional<AddressBean> optionalAddress = addressRepository.findById(id);
        if (optionalAddress.isEmpty()) {
            return new ResponseEntity<>(new ApiResponse("Address not found", true, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND);
        }

        AddressBean address = optionalAddress.get();
        if (!address.equals(updatedAddress)) {
            address.setStreet(updatedAddress.getStreet());
            address.setCountry(updatedAddress.getCountry());
            address.setState(updatedAddress.getState());
            address.setZipCode(updatedAddress.getZipCode());
            address.setUserBean(updatedAddress.getUserBean());
            addressRepository.save(address);
        }

        return new ResponseEntity<>(new ApiResponse("Address updated", false, HttpStatus.OK, address), HttpStatus.OK);
    }

    @Transactional(rollbackFor = {SQLException.class})
    public ResponseEntity<ApiResponse> deleteAddress(Long id) {
        if (!addressRepository.existsById(id)) {
            return new ResponseEntity<>(new ApiResponse("Address not found", true, HttpStatus.NOT_FOUND, null), HttpStatus.NOT_FOUND);
        }

        addressRepository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse("Address deleted", false, HttpStatus.OK, null), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> getAddressesByCountry(String country) {
        List<AddressBean> addresses = addressRepository.findByCountry(country);
        return new ResponseEntity<>(new ApiResponse("Addresses found", false, HttpStatus.OK, addresses), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> getAddressesByState(String state) {
        List<AddressBean> addresses = addressRepository.findByState(state);
        return new ResponseEntity<>(new ApiResponse("Addresses found", false, HttpStatus.OK, addresses), HttpStatus.OK);
    }

    public ResponseEntity<ApiResponse> getAddressesByZipCode(String zipCode) {
        List<AddressBean> addresses = addressRepository.findByZipCode(zipCode);
        return new ResponseEntity<>(new ApiResponse("Addresses found", false, HttpStatus.OK, addresses), HttpStatus.OK);
    }
}
