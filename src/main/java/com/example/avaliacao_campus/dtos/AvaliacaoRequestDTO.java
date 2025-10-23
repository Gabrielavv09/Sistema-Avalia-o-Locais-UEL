package com.example.avaliacao_campus.dtos;

import lombok.Data;
import java.util.List;

@Data
public class AvaliacaoRequestDTO {

    // ID do Usuário que está fazendo a avaliação (Aluno ou Professor)
    private Long idUsuario;

    // ID do Local do Campus que está sendo avaliado (RU, Biblioteca, etc.)
    private Long idLocal;

    // A lista de respostas para as questões da avaliação
    private List<RespostaDTO> respostas;
}