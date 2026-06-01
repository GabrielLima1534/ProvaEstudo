package almoxarifado.controller;

import almoxarifado.entity.Usuario;
import almoxarifado.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/admin")
    public ResponseEntity<Usuario> criarAdmin(
            @RequestBody Usuario usuario) {

        return ResponseEntity.ok(
                usuarioService.criarAdministrador(
                        usuario.getNome(),
                        usuario.getSenha()
                )
        );
    }

    @PostMapping("/operador")
    public ResponseEntity<Usuario> criarOperador(
            @RequestBody Usuario usuario) {

        return ResponseEntity.ok(
                usuarioService.criarOperador(
                        usuario.getNome(),
                        usuario.getSenha()
                )
        );
    }
}
