package mx.edu.utez.huiclothes.services.user;

import lombok.AllArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
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
    public ResponseEntity<ApiResponse> updateUser(UserBean userBean){

        Optional<UserBean> foundUser = repository.findById(userBean.getId());

        if (foundUser.isEmpty())
            return new ResponseEntity<>(new ApiResponse("Error, el usuario que intentas actualizar no existe",true,HttpStatus.NOT_FOUND,null),HttpStatus.NOT_FOUND);

        UserBean existingUser = foundUser.get();

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



        // Actualizar la información de UserBean
        existingUser.setName(userBean.getName());
        existingUser.setLastname(userBean.getLastname());
        existingUser.setEmail(userBean.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userBean.getPassword()));

        // Actualizar la información de PersonBean asociada
        if (existingUser.getPerson() != null) {
            existingUser.getPerson().setName(userBean.getPerson().getName());
            existingUser.getPerson().setLastname(userBean.getPerson().getLastname());
            existingUser.getPerson().setSurname(userBean.getPerson().getSurname());
        }

        existingUser.setRol(foundRole.get());

        return new ResponseEntity<>( new ApiResponse("Usuario guardado exitosamente", false, HttpStatus.OK, repository.save(existingUser)), HttpStatus.OK);

    }




}
