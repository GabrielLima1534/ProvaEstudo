package almoxarifado.service;

import almoxarifado.entity.*;
import almoxarifado.repository.OperacaoRepository;
import almoxarifado.repository.ProdutoRepository;
import almoxarifado.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OperacaoService {

    private final OperacaoRepository operacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final UsuarioRepository usuarioRepository;

    public Operacao entrada(Long produtoId, Integer quantidade, Usuario usuario) {

        Produto produto = buscarProduto(produtoId);

        produto.setQuantidadeTotal(
                produto.getQuantidadeTotal() + quantidade
        );

        produtoRepository.save(produto);

        return registrarOperacao(produto, quantidade, TipoOperacao.ENTRADA, usuario);
    }

    public Operacao saida(Long produtoId, Integer quantidade, Usuario usuario) {

        Produto produto = buscarProduto(produtoId);

        if (produto.getQuantidadeTotal() < quantidade) {
            throw new RuntimeException("Estoque insuficiente");
        }

        produto.setQuantidadeTotal(
                produto.getQuantidadeTotal() - quantidade
        );

        produtoRepository.save(produto);

        return registrarOperacao(produto, quantidade, TipoOperacao.SAIDA, usuario);
    }

    public List<Operacao> listarHistorico() {
        return operacaoRepository.findAll();
    }

    public Operacao buscarPorId(Long id) {
        return operacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Operação não encontrada"));
    }

    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    private Produto buscarProduto(Long id) {
        return produtoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    private Operacao registrarOperacao(
            Produto produto,
            Integer quantidade,
            TipoOperacao tipo,
            Usuario usuario
    ) {
        Operacao operacao = new Operacao();

        operacao.setProduto(produto);
        operacao.setQuantidade(quantidade);
        operacao.setTipoOperacao(tipo);
        operacao.setUsuario(usuario);
        operacao.setDataoperacao(LocalDateTime.now());

        return operacaoRepository.save(operacao);
    }
}