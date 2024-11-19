package mx.edu.utez.huiclothes.services.auth;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.auth.AuthResponse;
import mx.edu.utez.huiclothes.models.auth.LoginRequest;
import mx.edu.utez.huiclothes.models.auth.RegisterRequest;
import mx.edu.utez.huiclothes.models.person.PersonBean;
import mx.edu.utez.huiclothes.models.person.PersonRepository;
import mx.edu.utez.huiclothes.models.rol.RoleBean;
import mx.edu.utez.huiclothes.models.user.UserBean;
import mx.edu.utez.huiclothes.models.user.UserRepository;
import mx.edu.utez.huiclothes.services.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PersonRepository personRepository;

    public AuthResponse login(LoginRequest request){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        UserDetails user = repository.findByEmail(request.getEmail()).orElseThrow();


        UserBean foundUser = repository.findByEmail(request.getEmail()).get();
        String token = jwtService.getToken(user);
        return AuthResponse.builder().token(token).role(foundUser.getRol().getName()).password(foundUser.getPassword()).username(foundUser.getEmail()).build();
    }

    public ResponseEntity<ApiResponse> register(RegisterRequest request){

        if (request.getPassword().length() < 8) {
            return new ResponseEntity<>(
                    new ApiResponse("Error, la contraseña es menor a 8 caracteres", true, HttpStatus.BAD_REQUEST, null),
                    HttpStatus.BAD_REQUEST
            );
        }

        // Validación del formato del email usando una expresión regular
        String emailRegex = "^[\\w-\\.]+@[\\w-]+\\.[a-zA-Z]{2,}$";
        if (!request.getEmail().matches(emailRegex)) {
            return new ResponseEntity<>(
                    new ApiResponse("Error, el email tiene un formato inválido", true, HttpStatus.BAD_REQUEST, null),
                    HttpStatus.BAD_REQUEST
            );
        }

        if (request.getName().equals("")   || request.getName().equals(null))
            return new ResponseEntity<>(new ApiResponse("inserta un nombre valido", true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);

        if (request.getLastname().equals("")   || request.getLastname().equals(null))
            return new ResponseEntity<>(new ApiResponse("inserta un apellido  valido", true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);

        Optional<UserBean> foundEmail = repository.findByEmail(request.getEmail());

        if (foundEmail.isPresent())
            return new ResponseEntity<>(new ApiResponse("ya existe un usuario registrado con ese email", true,HttpStatus.BAD_REQUEST,null),HttpStatus.BAD_REQUEST);

        RoleBean roleBean = new RoleBean();
        roleBean.setId(3L);

        PersonBean personBean = new PersonBean();
        personBean.setName(request.getName());
        personBean.setLastname(request.getLastname());
        personBean.setSurname(request.getSurname());

        PersonBean savedPerson = personRepository.save(personBean);


        UserBean user = new UserBean();
        user.setRol(roleBean);
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPerson(savedPerson);

        repository.saveAndFlush(user);

        return new ResponseEntity<>(new ApiResponse("usuario registrado con exito",false,HttpStatus.OK,jwtService.getToken(user),foundEmail.get().getId()),HttpStatus.OK);
    }
}
