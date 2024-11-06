package mx.edu.utez.huiclothes.models.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressBean, Long> {
    Optional<AddressBean> findById(Long id);
    //Optional<AddressBean> findByCountry(String country);
    //Optional<AddressBean> findByState(String state);
    //Optional<AddressBean> findByZipcode(String zipcode);
}
