package almoxarifado.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Operacoes")
public class Operacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produto_id")
    private Produto produto;

    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    private TipoOperacao tipoOperacao;

    private LocalDateTime dataoperacao;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;


}
