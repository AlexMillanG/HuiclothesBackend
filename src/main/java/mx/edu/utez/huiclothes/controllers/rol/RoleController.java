package mx.edu.utez.huiclothes.controllers.rol;


import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController Controller
@RequestMapping g("api/huiclothes/rol")
@CrossOrigin n(origins = {"*"})
@AllArgsConstructor ructor
public class RoleController {
    private final RoleService roleService;

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAll(){
        return service.getAll();
    }
}
