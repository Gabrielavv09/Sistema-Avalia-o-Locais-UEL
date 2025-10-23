package com.example.avaliacao_campus.repositories;

import com.example.avaliacao_campus.models.Usuario;
import org.springframework.data.repository.ListCrudRepository;
import java.util.Optional;
import java.util.List;


public interface UsuarioRepository extends ListCrudRepository<Usuario, Long> {
    // Para verificar se o e-mail (UNIQUE) já existe antes de cadastrar.
    Optional<Usuario> findByEmail(String email);

    // Para listar apenas 'alunos' ou apenas 'professores'.
    List<Usuario> findByTipo(String tipo);
}
