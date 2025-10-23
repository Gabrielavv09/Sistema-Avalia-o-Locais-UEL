package com.example.avaliacao_campus.repositories;

import com.example.avaliacao_campus.models.LocalCampus;
import org.springframework.data.repository.ListCrudRepository;
import java.util.List;
import java.util.Optional;

public interface LocalCampusRepository extends ListCrudRepository<LocalCampus, Long> {

    // Usada para verificar se o nome já existe antes de cadastrar.
    Optional<LocalCampus> findByNome(String nome);

    // Retorna todos os locais ordenados pelo nome.
    List<LocalCampus> findAllByOrderByNomeAsc();
}