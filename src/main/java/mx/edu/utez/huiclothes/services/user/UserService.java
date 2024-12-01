package mx.edu.utez.huiclothes.services.user;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.person.PersonBean;
import mx.edu.utez.huiclothes.models.rol.RoleBean;
import mx.edu.utez.huiclothes.models.rol.RoleRepository;
import mx.edu.utez.huiclothes.models.user.UserBean;
import mx.edu.utez.huiclothes.models.user.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
@Transactional(rollbackFor = SQLException.class)
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final RoleRepository roleRepository;


    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findAll(){
        return new ResponseEntity<>(new ApiResponse(repository.findAll(),HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> findById(Long id){
        Optional<UserBean> foundUser = repository.findById(id);
        if (foundUser.isEmpty())
            return new ResponseEntity<>(new ApiResponse("usuario no encontrado",true,HttpStatus.NOT_FOUND,null,null),HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new ApiResponse(repository.findById(id),HttpStatus.OK), HttpStatus.OK);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> delete(Long id){
        repository.deleteById(id);
        return new ResponseEntity<>(new ApiResponse("eliminado con exito",false,HttpStatus.OK,null), HttpStatus.OK);
    }

    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> saveUser(UserBean userBean) {
        // Validación de la longitud de la contraseña
        if (userBean.getPassword().length() < 8) {
            return new ResponseEntity<>(
                    new ApiResponse("Error, la contraseña es menor a 8 caracteres", true, HttpStatus.BAD_REQUEST, null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Validación del formato del email usando una expresión regular
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
        if (!userBean.getEmail().matches(emailRegex)) {
            return new ResponseEntity<>(
                    new ApiResponse("Error, el email tiene un formato inválido", true, HttpStatus.BAD_REQUEST, null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Validación de que el rol existe en la base de datos
        Optional<RoleBean> foundRole = roleRepository.findById(userBean.getRol().getId());
        if (foundRole.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse("Error, el rol insertado no existe", true, HttpStatus.NOT_FOUND, null),
                    HttpStatus.NOT_FOUND
            );
        }


        if (userBean.getPerson().getName().equals("")   || userBean.getPerson().getName().equals(null))
            return new ResponseEntity<>(new ApiResponse("inserta un nombre valido", true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);



        if (userBean.getPerson().getLastname().equals("")   || userBean.getPerson().getLastname().equals(null))
            return new ResponseEntity<>(new ApiResponse("inserta un apellido  valido", true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);




        // Si todas las validaciones pasan, se guarda el usuario
        userBean.setRol(foundRole.get());


        return new ResponseEntity<>(
                new ApiResponse("Usuario guardado exitosamente", false, HttpStatus.OK, repository.save(userBean)),
                HttpStatus.OK
        );
    }



    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> saveSeller(UserBean userBean) {
        // Validación de la longitud de la contraseña
        if (userBean.getPassword().length() < 8) {
            return new ResponseEntity<>(
                    new ApiResponse("Error, la contraseña es menor a 8 caracteres", true, HttpStatus.BAD_REQUEST, null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Validación del formato del email usando una expresión regular
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
        if (!userBean.getEmail().matches(emailRegex)) {
            return new ResponseEntity<>(
                    new ApiResponse("Error, el email tiene un formato inválido", true, HttpStatus.BAD_REQUEST, null),
                    HttpStatus.BAD_REQUEST
            );
        }



        if (userBean.getPerson().getName().equals("")   || userBean.getPerson().getName().equals(null))
            return new ResponseEntity<>(new ApiResponse("inserta un nombre valido", true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);



        if (userBean.getPerson().getLastname().equals("")   || userBean.getPerson().getLastname().equals(null))
            return new ResponseEntity<>(new ApiResponse("inserta un apellido  valido", true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);




        RoleBean roleBean = new RoleBean();
        roleBean.setId(2L);

        // Si todas las validaciones pasan, se guarda el usuario
        userBean.setRol(roleBean);


        return new ResponseEntity<>(
                new ApiResponse("Usuario guardado exitosamente", false, HttpStatus.OK, repository.save(userBean)),
                HttpStatus.OK
        );
    }


    @Transactional(rollbackFor = SQLException.class)
    public ResponseEntity<ApiResponse> updateUser(UserBean userBean) {

        Optional<UserBean> foundUser = repository.findById(userBean.getId());
        if (foundUser.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse("Error, el usuario que intentas actualizar no existe", true, HttpStatus.NOT_FOUND, null),
                    HttpStatus.NOT_FOUND
            );
        }

        UserBean existingUser = foundUser.get();

        // Validación de la contraseña
        if (userBean.getPassword().length() < 8) {
            return new ResponseEntity<>(
                    new ApiResponse("Error, la contraseña es menor a 8 caracteres", true, HttpStatus.BAD_REQUEST, null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Validación del formato del email usando una expresión regular
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
        if (!userBean.getEmail().matches(emailRegex)) {
            return new ResponseEntity<>(
                    new ApiResponse("Error, el email tiene un formato inválido", true, HttpStatus.BAD_REQUEST, null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Validar si el rol existe
        Optional<RoleBean> foundRole = roleRepository.findById(userBean.getRol().getId());
        if (foundRole.isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse("Error, el rol insertado no existe", true, HttpStatus.NOT_FOUND, null),
                    HttpStatus.NOT_FOUND
            );
        }

        // Validaciones para los campos de PersonBean
        if (userBean.getPerson().getName() == null || userBean.getPerson().getName().trim().isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse("Inserta un nombre válido", true, HttpStatus.BAD_REQUEST, null),
                    HttpStatus.BAD_REQUEST
            );
        }
        if (userBean.getPerson().getLastname() == null || userBean.getPerson().getLastname().trim().isEmpty()) {
            return new ResponseEntity<>(
                    new ApiResponse("Inserta un apellido válido", true, HttpStatus.BAD_REQUEST, null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Actualizar la información de UserBean
        existingUser.setEmail(userBean.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userBean.getPassword()));
        existingUser.setRol(foundRole.get());

        // Actualizar la información de PersonBean asociada
        PersonBean existingPerson = existingUser.getPerson();
        if (existingPerson != null) {
            existingPerson.setName(userBean.getPerson().getName());
            existingPerson.setLastname(userBean.getPerson().getLastname());
            existingPerson.setSurname(
                    userBean.getPerson().getSurname() != null ? userBean.getPerson().getSurname() : ""
            );
        }

        // Guardar el usuario actualizado
        repository.save(existingUser);

        return new ResponseEntity<>(
                new ApiResponse("Usuario actualizado exitosamente", false, HttpStatus.OK, existingUser),
                HttpStatus.OK
        );
    }




}
