package com.example.avaliacao_campus.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Embedded;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("AVALIACAO_QUESTAO")
public class AvaliacaoQuestao {

    @Id
    @Embedded.Nullable // Permite que a chave seja salva quando for nova
    private AvaliacaoQuestaoKey id;

    private String valor; // Mapeia para valor (a resposta em si)

}