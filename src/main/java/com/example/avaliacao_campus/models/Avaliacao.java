package com.example.avaliacao_campus.models;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Avaliacao {

    private Long idAvaliacao;
    private Long idUsuario;
    private Long idLocal;
    private LocalDate dataAvaliacao;

}
