package mx.edu.utez.huiclothes.models.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonBean, Long> {
    @Override
    Optional<PersonBean> findById(Long id);
}
