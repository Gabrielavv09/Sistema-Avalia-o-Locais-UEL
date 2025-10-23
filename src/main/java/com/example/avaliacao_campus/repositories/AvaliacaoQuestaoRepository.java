package com.example.avaliacao_campus.repositories;

import com.example.avaliacao_campus.models.AvaliacaoQuestao;
import com.example.avaliacao_campus.models.AvaliacaoQuestaoKey;
import org.springframework.data.repository.ListCrudRepository;
import java.util.List;

// Importação necessária para consultas nativas avançadas (parte da entrega C)
// import org.springframework.data.jdbc.repository.query.Query;
// import java.util.List;

public interface AvaliacaoQuestaoRepository extends ListCrudRepository<AvaliacaoQuestao, AvaliacaoQuestaoKey> {
}