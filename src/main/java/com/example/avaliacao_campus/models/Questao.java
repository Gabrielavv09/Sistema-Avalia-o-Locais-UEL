package com.example.avaliacao_campus.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("QUESTAO")
public class Questao {

    @Id
    private Long idQuestao;

    private String texto;
    private String tipo;
    private String opcoes;
    private Long idUsuarioCriador;
}
