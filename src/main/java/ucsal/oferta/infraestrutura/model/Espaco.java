package ucsal.oferta.infraestrutura.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import ucsal.oferta.infraestrutura.dto.EspacoDTO;

@Getter
@Setter
@Entity
public class Espaco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sigla;
    private String descricao;
    private Integer capacidadeMaxima; // changed int to Integer
    private String localizacao;
    private String observacao;

    @OneToMany
    private List<Equipamento> equipamentos;

    @Enumerated(EnumType.STRING)
    private TipoEspaco tipoEspaco;

    public Espaco() {    }

    
    
    public Espaco(EspacoDTO dto) {
        this.id = dto.id();
        this.sigla = dto.sigla();
        this.descricao = dto.descricao();
        this.capacidadeMaxima = dto.capacidadeMaxima();
        this.equipamentos = dto.equipamentos();
        this.localizacao = dto.localizacao();
        this.tipoEspaco = dto.tipoEspaco();
    }

}
