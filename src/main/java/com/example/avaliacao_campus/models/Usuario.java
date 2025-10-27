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
    
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getCursoNome() { return cursoNome; }
    public void setCursoNome(String cursoNome) { this.cursoNome = cursoNome; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }
}