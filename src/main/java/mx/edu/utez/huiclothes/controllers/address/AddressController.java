package mx.edu.utez.huiclothes.controllers.address;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.address.AddressBean;
import mx.edu.utez.huiclothes.services.address.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin({"*"})
@RequestMapping("/api/address")
@AllArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> findAll() {
        return addressService.findAll();
    }

    @GetMapping("/findByUserId/{id}")
    public ResponseEntity<ApiResponse> findByUserId(@PathVariable Long id) {
        return addressService.findByUserId(id);
    }

    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@RequestBody AddressBean addressBean) {
        return addressService.createAddress(addressBean);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> update(@RequestBody AddressBean addressBean) {
        return addressService.updateAddress(addressBean);
    }

    

}
