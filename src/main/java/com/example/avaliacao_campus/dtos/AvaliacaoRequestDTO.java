package com.example.avaliacao_campus.dtos;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class AvaliacaoRequestDTO {
    private Long idUsuario;
    private String nome;
    private String email;
    private String tipo;
    private String cursoNome;
    private String departamento;

    private Long idLocal;

    private Map<Long, String> respostas;

    private List<NovaQuestaoDTO> novasQuestoes;
}
