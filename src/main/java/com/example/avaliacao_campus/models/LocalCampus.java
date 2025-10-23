package com.example.avaliacao_campus.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("LOCALCAMPUS")
public class LocalCampus {

    @Id
    private Long idLocal;

    private String nome;
    private String descricao;
    private String localizacao;
    private String urlImage;
}
