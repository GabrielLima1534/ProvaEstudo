package almoxarifado.controller;

// Importa o service (onde está a lógica)
import almoxarifado.service.MovimentacaoService;

// Anotações do Spring para criar API REST
import org.springframework.web.bind.annotation.*;

@RestController // Diz que essa classe responde requisições HTTP
@RequestMapping("/movimentacoes") // URL base (rota principal)
public class MovimentacaoController {

    // Service que contém a regra de negócio
    private final MovimentacaoService service;

    // Construtor → Spring injeta automaticamente o service
    public MovimentacaoController(MovimentacaoService service) {
        this.service = service;
    }

    // =========================
    // 📥 ENDPOINT DE ENTRADA
    // =========================
    @PostMapping("/entrada") // URL: POST /movimentacoes/entrada
    public void entrada(@RequestParam Long produtoId,   // recebe id do produto
                        @RequestParam Integer quantidade) { // recebe quantidade

        // Chama o service para executar a lógica
        service.entrada(produtoId, quantidade, null); // usuário simplificado
    }

    // =========================
    // 📤 ENDPOINT DE SAÍDA
    // =========================
    @PostMapping("/saida") // URL: POST /movimentacoes/saida
    public void saida(@RequestParam Long produtoId,
                      @RequestParam Integer quantidade) {

        // Chama o service para saída de produto
        service.saida(produtoId, quantidade, null);
    }
}