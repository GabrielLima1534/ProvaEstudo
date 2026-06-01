package almoxarifado.service;

import almoxarifado.entity.Role;
import almoxarifado.entity.Usuario;
import almoxarifado.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public Usuario criarAdministrador(
            String nome,
            String senha) {

        Usuario usuario = new Usuario();

        usuario.setNome(nome);
        usuario.setSenha(senha);
        usuario.setRole(Role.ADMIN);

        return usuarioRepository.save(usuario);
    }

    public Usuario criarOperador(
            String nome,
            String senha) {

        Usuario usuario = new Usuario();

        usuario.setNome(nome);
        usuario.setSenha(senha);
        usuario.setRole(Role.OPERADOR);

        return usuarioRepository.save(usuario);
    }
}