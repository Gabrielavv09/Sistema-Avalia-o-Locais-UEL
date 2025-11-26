package com.example.avaliacao_campus.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable; // Essencial para chaves compostas

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvaliacaoQuestaoKey implements Serializable {

    private Long idAvaliacao;
    private Long idQuestao;
}