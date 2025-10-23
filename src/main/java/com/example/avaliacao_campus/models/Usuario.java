package com.example.avaliacao_campus.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("USUARIO")
public class Usuario {

    @Id
    private Long idUsuario;

    private String nome;
    private String email;
    private String tipo;
    private String cursoNome;
    private String departamento;
}