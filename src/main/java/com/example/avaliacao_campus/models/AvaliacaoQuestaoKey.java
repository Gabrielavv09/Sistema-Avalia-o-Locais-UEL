package com.example.avaliacao_campus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable; // Essencial para chaves compostas

@Data
@NoArgsConstructor // Construtor sem argumentos
@AllArgsConstructor // Construtor com todos os argumentos
public class AvaliacaoQuestaoKey implements Serializable {

    // NOTA: Estes campos não precisam de @Id, eles são a chave composta.
    private Long idAvaliacao;
    private Long idQuestao;
}