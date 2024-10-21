package br.com.fiap.games.dto.jogo;

import br.com.fiap.games.model.Plataforma;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class DetalhesJogoDto {

    private Integer codigo;
    private String nome;
    private String descricao;
    private Plataforma plataforma;
    private Double valor;
    private LocalDate dataLancamento;
    private Boolean multiplayer;
    private String imgUrl;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
}
