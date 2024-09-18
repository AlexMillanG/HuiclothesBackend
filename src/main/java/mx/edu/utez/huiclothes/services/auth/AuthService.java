package mx.edu.utez.huiclothes.services.auth;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.huiclothes.models.auth.AuthResponse;
import mx.edu.utez.huiclothes.models.auth.LoginRequest;
import mx.edu.utez.huiclothes.models.auth.RegisterRequest;
import mx.edu.utez.huiclothes.models.rol.RoleBean;
import mx.edu.utez.huiclothes.models.user.UserBean;
import mx.edu.utez.huiclothes.models.user.UserRepository;
import mx.edu.utez.huiclothes.services.jwt.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        UserDetails user = repository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder().token(token).build();
    }

    public AuthResponse register(RegisterRequest request){
        RoleBean roleBean = new RoleBean();
        roleBean.setId(2L);

        UserBean user = UserBean.builder()
                .email(request.getEmail())
                .name(request.getName())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .rol(roleBean)
                .build();
        repository.saveAndFlush(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }
}
