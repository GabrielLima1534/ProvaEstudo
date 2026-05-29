package almoxarifado.service;

// Importa as entidades (objetos do sistema)
import almoxarifado.entity.Movimentacao;
import almoxarifado.entity.Produto;
import almoxarifado.entity.TipoMovimentacao;
import almoxarifado.entity.Usuario;

// Importa os repositories (acesso ao banco)
import almoxarifado.repository.MovimentacaoRepository;
import almoxarifado.repository.ProdutoRepository;

// Anotação que diz que isso é um Service (regra de negócio)
import org.springframework.stereotype.Service;

// Para trabalhar com data/hora
import java.time.LocalDateTime;

@Service
public class MovimentacaoService {

    // Repositório para salvar e buscar movimentações
    private MovimentacaoRepository movimentacaoRepository;

    // Repositório para acessar produtos
    private ProdutoRepository produtoRepository;

    // Construtor → Spring injeta os repositories automaticamente
    public MovimentacaoService(ProdutoRepository produtoRepository, MovimentacaoRepository movimentacaoRepository) {
        this.produtoRepository = produtoRepository;
        this.movimentacaoRepository = movimentacaoRepository;
    }

    // =========================
    // 📥 ENTRADA DE PRODUTO
    // =========================
    public void entrada(Long produtoId, Integer quantidade, Usuario usuario) {

        // 🔍 Busca o produto no banco pelo ID
        // Se não existir, lança erro
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // ➕ REGRA DE NEGÓCIO:
        // Soma a quantidade no estoque
        produto.setQuantidade(produto.getQuantidade() + quantidade);

        // 🧾 Cria um registro da movimentação
        Movimentacao mov = new Movimentacao();

        // Define qual produto foi movimentado
        mov.setProduto(produto);

        // Define quem fez a operação
        mov.setUsuario(usuario);

        // Define a quantidade movimentada
        mov.setQuantidade(quantidade);

        // Define o tipo da operação (ENTRADA)
        mov.setTipo(TipoMovimentacao.ENTRADA);

        // Define a data/hora atual
        mov.setData(LocalDateTime.now());

        // 💾 Salva a movimentação no banco
        movimentacaoRepository.save(mov);

        // 💾 Atualiza o produto com novo estoque
        produtoRepository.save(produto);
    }

    // =========================
    // 📤 SAÍDA DE PRODUTO
    // =========================
    public void saida(Long produtoId, Integer quantidade, Usuario usuario) {

        // 🔍 Busca o produto no banco
        Produto produto = produtoRepository.findById(produtoId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        // 🚫 VALIDAÇÃO:
        // Verifica se tem estoque suficiente
        if (produto.getQuantidade() < quantidade) {
            throw new RuntimeException("Estoque insuficiente");
        }

        // ➖ REGRA DE NEGÓCIO:
        // Subtrai a quantidade do estoque
        produto.setQuantidade(produto.getQuantidade() - quantidade);

        // 🧾 Cria o registro da movimentação
        Movimentacao mov = new Movimentacao();

        // Define o produto
        mov.setProduto(produto);

        // Define o usuário
        mov.setUsuario(usuario);

        // Define a quantidade
        mov.setQuantidade(quantidade);

        // Define o tipo (SAÍDA)
        mov.setTipo(TipoMovimentacao.SAIDA);

        // Define data/hora
        mov.setData(LocalDateTime.now());

        // 💾 Salva a movimentação
        movimentacaoRepository.save(mov);

        // 💾 Atualiza o produto no banco
        produtoRepository.save(produto);
    }

}
