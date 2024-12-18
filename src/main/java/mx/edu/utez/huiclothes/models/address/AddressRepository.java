package mx.edu.utez.huiclothes.models.address;

import mx.edu.utez.huiclothes.models.user.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressBean, Long> {
    List<AddressBean> findByCountry(String country);
    List<AddressBean> findByState(String state);
    List<AddressBean> findByZipCode(String zipCode);

    List<AddressBean> findByUserBean(UserBean userBean);
}
