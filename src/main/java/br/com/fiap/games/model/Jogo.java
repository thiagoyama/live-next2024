package br.com.fiap.games.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
public class Jogo {

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
