package almoxarifado.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer quantidadeTotal;
}
