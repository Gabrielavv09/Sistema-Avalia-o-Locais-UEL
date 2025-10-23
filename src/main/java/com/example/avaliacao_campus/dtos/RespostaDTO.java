package com.example.avaliacao_campus.dtos;

import lombok.Data;

@Data // Gera Getters, Setters, toString, etc.
public class RespostaDTO {

    // ID da Questão que está sendo respondida
    private Long idQuestao;

    // O valor da resposta (nota, texto livre)
    private String valor;
}