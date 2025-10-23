package com.example.avaliacao_campus.repositories;

import com.example.avaliacao_campus.models.Questao;
import org.springframework.data.repository.ListCrudRepository;
import java.util.List;

public interface QuestaoRepository extends ListCrudRepository<Questao, Long> {

    // Lista questões 'padrão' ou 'personalizadas'.
    List<Questao> findByTipo(String tipo);

    // Lista as questões criadas por um usuário específico.
    List<Questao> findByIdUsuarioCriador(Long idUsuario);
}
