package com.example.avaliacao_campus.repositories;

import com.example.avaliacao_campus.models.AvaliacaoQuestao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
public class AvaliacaoQuestaoRepository {

    private final JdbcTemplate jdbc;

    public AvaliacaoQuestaoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public void save(AvaliacaoQuestao aq) {
        String sql = "INSERT INTO avaliacao_questao (id_avaliacao, id_questao, valor) VALUES (?, ?, ?)";
        jdbc.update(sql, aq.getIdAvaliacao(), aq.getIdQuestao(), aq.getValor());
    }

    // 🔍 Busca todas as respostas de uma avaliação, com o texto da questão
    public List<Map<String, Object>> buscarRespostasPorAvaliacao(Long idAvaliacao) {
        String sql = """
            SELECT 
                q.texto AS questao_texto,
                q.tipo AS questao_tipo,
                aq.valor AS resposta_valor
            FROM avaliacao_questao aq
            JOIN questao q ON q.id_questao = aq.id_questao
            WHERE aq.id_avaliacao = ?
            ORDER BY q.id_questao
        """;
        return jdbc.queryForList(sql, idAvaliacao);
    }

    // (opcional) buscar todas respostas de um usuário em todos os locais
    public List<Map<String, Object>> buscarRespostasPorUsuario(Long idUsuario) {
        String sql = """
            SELECT 
                l.nome AS local_nome,
                q.texto AS questao_texto,
                aq.valor AS resposta_valor,
                a.data_avaliacao
            FROM avaliacao_questao aq
            JOIN avaliacao a ON a.id_avaliacao = aq.id_avaliacao
            JOIN localcampus l ON l.id_local = a.id_local
            JOIN questao q ON q.id_questao = aq.id_questao
            WHERE a.id_usuario = ?
            ORDER BY a.data_avaliacao DESC
        """;
        return jdbc.queryForList(sql, idUsuario);
    }
}