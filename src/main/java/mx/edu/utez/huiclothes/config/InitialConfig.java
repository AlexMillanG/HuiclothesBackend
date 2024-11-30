package mx.edu.utez.huiclothes.config;


import mx.edu.utez.huiclothes.models.category.CategoryBean;
import mx.edu.utez.huiclothes.models.category.CategoryRepository;
import mx.edu.utez.huiclothes.models.imageCategory.ImageCategoryBean;
import mx.edu.utez.huiclothes.models.imageCategory.ImageCategoryRepository;
import mx.edu.utez.huiclothes.models.rol.RoleBean;
import mx.edu.utez.huiclothes.models.rol.RoleRepository;
import mx.edu.utez.huiclothes.models.user.UserBean;
import mx.edu.utez.huiclothes.models.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Component
public class InitialConfig {

    @Autowired
    private RoleRepository rolRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ImageCategoryRepository imageCategoryRepository;

    @Bean
    public CommandLineRunner initData(CategoryRepository categoryRepository) {
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

            Optional<CategoryBean> foundCategory = categoryRepository.findByName("sneackers");

            if (foundCategory.isEmpty()){
                CategoryBean categoryBean = new CategoryBean();
                categoryBean.setName("sneackers");
                repository.saveAndFlush(categoryBean);
                System.err.println("SE HA INSERTADO LA CATEGORÍA SNEACKERS");

            }


            String blusa = "src/main/resources/categoryImages/blusa.jpg";
            String pantalon = "src/main/resources/categoryImages/cargo.jpg";
            String sudadera = "src/main/resources/categoryImages/hoddie.jpg";
            String tenis = "src/main/resources/categoryImages/jumpman-jack.jpg";
            String shorts = "src/main/resources/categoryImages/short.jpg";
            String vestido = "src/main/resources/categoryImages/vestido.jpg";
            try {

                // Categoría blusa con imagen
                byte[] blusaBytes = convertImageToBytes(blusa);
                ImageCategoryBean imageCategoryBeanBlusa = new ImageCategoryBean();
                imageCategoryBeanBlusa.setImage(blusaBytes);
                ImageCategoryBean savedImagesBlusa = imageCategoryRepository.save(imageCategoryBeanBlusa);
                CategoryBean categoryBeanBlusa = new CategoryBean();
                categoryBeanBlusa.setImage(savedImagesBlusa);
                categoryBeanBlusa.setName("blusas");
                categoryRepository.save(categoryBeanBlusa);
                System.err.println("categoría blusas con imagenes insertados");


                // Categoría pantalón con imagen
                byte[] pantalonBytes = convertImageToBytes(pantalon);
                ImageCategoryBean imageCategoryBeanPantalon = new ImageCategoryBean();
                imageCategoryBeanPantalon.setImage(pantalonBytes);
                ImageCategoryBean savedImagesPantalon = imageCategoryRepository.save(imageCategoryBeanPantalon);
                CategoryBean categoryBeanPantalon = new CategoryBean();
                categoryBeanPantalon.setImage(savedImagesPantalon);
                categoryBeanPantalon.setName("pantalones");
                categoryRepository.save(categoryBeanPantalon);
                System.err.println("categoría pantalones con imagenes insertados");


                // Categoría sudadera con imagen
                byte[] sudaderaBytes = convertImageToBytes(sudadera);
                ImageCategoryBean imageCategoryBeanSudadera = new ImageCategoryBean();
                imageCategoryBeanSudadera.setImage(sudaderaBytes);
                ImageCategoryBean savedImagesSudadera = imageCategoryRepository.save(imageCategoryBeanSudadera);
                CategoryBean categoryBeanSudadera = new CategoryBean();
                categoryBeanSudadera.setImage(savedImagesSudadera);
                categoryBeanSudadera.setName("sudaderas");
                categoryRepository.save(categoryBeanSudadera);
                System.err.println("categoría sudadera con imagenes insertados");


                // Categoría tenis con imagen
                byte[] tenisBytes = convertImageToBytes(tenis);
                ImageCategoryBean imageCategoryBeanTenis = new ImageCategoryBean();
                imageCategoryBeanTenis.setImage(tenisBytes);
                ImageCategoryBean savedImagesTenis = imageCategoryRepository.save(imageCategoryBeanTenis);
                CategoryBean categoryBeanTenis = new CategoryBean();
                categoryBeanTenis.setImage(savedImagesTenis);
                categoryBeanTenis.setName("tenis");
                categoryRepository.save(categoryBeanTenis);
                System.err.println("categoría tenis con imagenes insertados");


                // Categoría shorts con imagen
                byte[] shortsBytes = convertImageToBytes(shorts);
                ImageCategoryBean imageCategoryBeanShorts = new ImageCategoryBean();
                imageCategoryBeanShorts.setImage(shortsBytes);
                ImageCategoryBean savedImagesShorts = imageCategoryRepository.save(imageCategoryBeanShorts);
                CategoryBean categoryBeanShorts = new CategoryBean();
                categoryBeanShorts.setImage(savedImagesShorts);
                categoryBeanShorts.setName("shorts");
                categoryRepository.save(categoryBeanShorts);
                System.err.println("categoría shorts con imagenes insertados");


                // Categoría vestido con imagen
                byte[] vestidoBytes = convertImageToBytes(vestido);
                ImageCategoryBean imageCategoryBeanVestido = new ImageCategoryBean();
                imageCategoryBeanVestido.setImage(vestidoBytes);
                ImageCategoryBean savedImagesVestido = imageCategoryRepository.save(imageCategoryBeanVestido);
                CategoryBean categoryBeanVestido = new CategoryBean();
                categoryBeanVestido.setImage(savedImagesVestido);
                categoryBeanVestido.setName("vestidos");
                categoryRepository.save(categoryBeanVestido);
                System.err.println("categoría vestidos con imagenes insertados");


            } catch (IOException e) {
                e.printStackTrace();
            }

        };
    }

    public byte[] convertImageToBytes(String imagePath) throws IOException {
        Path path = Paths.get(imagePath);
        return Files.readAllBytes(path);
    }


}
