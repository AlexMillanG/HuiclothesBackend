package mx.edu.utez.huiclothes.controllers.auth;


import lombok.RequiredArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.models.auth.AuthResponse;
import mx.edu.utez.huiclothes.models.auth.LoginRequest;
import mx.edu.utez.huiclothes.models.auth.RegisterRequest;
import mx.edu.utez.huiclothes.services.auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin({"*"})
public class AuthController {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request){
        return ResponseEntity.ok(service.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request){
        return service.register(request);
    }
}
