package almoxarifado.controller;

import almoxarifado.entity.Operacao;
import almoxarifado.entity.Usuario;
import almoxarifado.service.OperacaoService;
import almoxarifado.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/operacoes")
@RequiredArgsConstructor
public class OperacaoController {

    private final OperacaoService operacaoService;
    private final UsuarioService usuarioService;

    @PostMapping("/entrada")
    public ResponseEntity<Operacao> entrada(
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade,
            @RequestParam Long usuarioId
    ) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        Operacao operacao = operacaoService.entrada(produtoId, quantidade, usuario);

        return ResponseEntity.ok(operacao);
    }

    @PostMapping("/saida")
    public ResponseEntity<Operacao> saida(
            @RequestParam Long produtoId,
            @RequestParam Integer quantidade,
            @RequestParam Long usuarioId
    ) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);

        Operacao operacao = operacaoService.saida(produtoId, quantidade, usuario);

        return ResponseEntity.ok(operacao);
    }

    @GetMapping
    public List<Operacao> historico() {
        return operacaoService.listarHistorico();
    }
}