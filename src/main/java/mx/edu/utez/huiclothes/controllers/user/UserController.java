package mx.edu.utez.huiclothes.controllers.user;

import lombok.RequiredArgsConstructor;
import mx.edu.utez.huiclothes.config.ApiResponse;
import mx.edu.utez.huiclothes.services.user.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<ApiResponse> findAll(){
        return userService.findAll();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/findById/{id}")
    public ResponseEntity<ApiResponse> findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping ("/delete/{id}")
    public ResponseEntity<ApiResponse> delete (@PathVariable Long id){
        return userService.delete(id);
    }


}
