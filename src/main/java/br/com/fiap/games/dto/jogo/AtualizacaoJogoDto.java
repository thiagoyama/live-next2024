package br.com.fiap.games.dto.jogo;

import br.com.fiap.games.model.Plataforma;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class AtualizacaoJogoDto {
    @NotBlank
    @Size(max = 50)
    private String nome;
    @Size(max = 250, min = 5)
    private String descricao;
    private Plataforma plataforma;
    @DecimalMin("0")
    private Double valor;
    @Past
    private LocalDate dataLancamento;
    private Boolean multiplayer;
    private String imgUrl;
}
