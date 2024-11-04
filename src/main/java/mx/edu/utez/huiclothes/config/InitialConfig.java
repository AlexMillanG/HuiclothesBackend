package mx.edu.utez.huiclothes.config;


import mx.edu.utez.huiclothes.models.rol.RoleBean;
import mx.edu.utez.huiclothes.models.rol.RoleRepository;
import mx.edu.utez.huiclothes.models.user.UserBean;
import mx.edu.utez.huiclothes.models.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
public class InitialConfig {

    @Autowired
    private RoleRepository rolRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            // Crear rol ADMIN con ID 1 si no existe
            if (!rolRepository.existsById(1L)) {
                RoleBean adminRole = new RoleBean();
                adminRole.setId(1L); // Establece el ID como 1
                adminRole.setName("ADMIN");
                rolRepository.save(adminRole);
            }

            // Crear rol VENDEDOR con ID 2 si no existe
            if (!rolRepository.existsById(2L)) {
                RoleBean userRole = new RoleBean();
                userRole.setId(2L); // Establece el ID como 2
                userRole.setName("SELLER");
                rolRepository.save(userRole);
            }



            if (!rolRepository.existsById(3L)) {
                RoleBean userRole = new RoleBean();
                userRole.setId(3L); // Establece el ID como 2
                userRole.setName("USER");
                rolRepository.save(userRole);
            }


            Optional<UserBean> foundAdmin = userRepository.findByEmail("admin@admin.com");

            if (foundAdmin.isEmpty()){
                UserBean userBean = new UserBean();
                userBean.setEmail("admin@admin.com");
                userBean.setPassword(passwordEncoder.encode("1234"));
                RoleBean roleBean = new RoleBean();
                roleBean.setId(1L);

                userBean.setRol(roleBean);

                userRepository.saveAndFlush(userBean);
            }

        };
    }


}
