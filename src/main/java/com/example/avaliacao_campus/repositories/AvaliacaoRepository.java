package com.example.avaliacao_campus.repositories;

import com.example.avaliacao_campus.models.Avaliacao;
import org.springframework.data.repository.ListCrudRepository;
import java.time.LocalDate;
import java.util.List;

public interface AvaliacaoRepository extends ListCrudRepository<Avaliacao, Long> {

    // Busca por usuário e local
    List<Avaliacao> findByIdUsuarioAndIdLocal(Long idUsuario, Long idLocal);

    // Relatório: Busca todas as avaliações feitas em um local.
    List<Avaliacao> findByIdLocal(Long idLocal);

    // Relatório Temporal: Busca avaliações dentro de um período
    List<Avaliacao> findByDataAvaliacaoBetween(LocalDate dataInicio, LocalDate dataFim);
}