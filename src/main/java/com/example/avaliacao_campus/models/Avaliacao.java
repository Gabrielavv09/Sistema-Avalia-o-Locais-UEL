package com.example.avaliacao_campus.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("AVALIACAO")
public class Avaliacao {

    @Id
    private Long idAvaliacao;

    private Long idUsuario;
    private Long idLocal;

    private LocalDate dataAvaliacao;
}
