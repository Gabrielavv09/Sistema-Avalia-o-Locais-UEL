package com.example.avaliacao_campus.repositories;

import com.example.avaliacao_campus.models.AvaliacaoQuestao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AvaliacaoQuestaoRepository {

    private final JdbcTemplate jdbc;

    public AvaliacaoQuestaoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Salva uma resposta
    public void save(AvaliacaoQuestao aq) {
        String sql = "INSERT INTO avaliacao_questao (id_avaliacao, id_questao, valor) VALUES (?, ?, ?)";
        jdbc.update(sql, aq.getIdAvaliacao(), aq.getIdQuestao(), aq.getValor());
    }

    // Busca respostas de uma avaliação
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

    // Busca respostas de um usuário (usado no Service)
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

    // --- MÉTODOS PARA RELATÓRIOS (NOVOS) ---

    public List<Map<String, Object>> getMediaPorLocal() {
        String sql = """
            SELECT 
                l.nome AS local, 
                CAST(AVG(CAST(aq.valor AS DECIMAL(10,2))) AS DECIMAL(10,1)) AS media
            FROM avaliacao_questao aq
            JOIN avaliacao a ON aq.id_avaliacao = a.id_avaliacao
            JOIN localcampus l ON a.id_local = l.id_local
            WHERE aq.valor ~ '^[0-9]+(\\\\.[0-9]+)?$' 
            GROUP BY l.nome
            ORDER BY media DESC
        """;
        return jdbc.queryForList(sql);
    }

    public List<Map<String, Object>> getMediaPorTipoUsuario() {
        String sql = """
            SELECT 
                u.tipo AS tipo, 
                CAST(AVG(CAST(aq.valor AS DECIMAL(10,2))) AS DECIMAL(10,1)) AS media
            FROM avaliacao_questao aq
            JOIN avaliacao a ON aq.id_avaliacao = a.id_avaliacao
            JOIN usuario u ON a.id_usuario = u.id_usuario
            WHERE aq.valor ~ '^[0-9]+(\\\\.[0-9]+)?$' 
            GROUP BY u.tipo
        """;
        return jdbc.queryForList(sql);
    }

    public List<Map<String, Object>> getEvolucaoAvaliacoes() {
        String sql = """
        SELECT 
            to_char(date_trunc('week', a.data_avaliacao), 'DD/MM/YYYY') AS semana,
            CAST(AVG(CAST(aq.valor AS DECIMAL(10,2))) AS DECIMAL(10,1)) AS media
        FROM avaliacao_questao aq
        JOIN avaliacao a ON aq.id_avaliacao = a.id_avaliacao
        WHERE aq.valor ~ '^[0-9]+(\\\\.[0-9]+)?$'
        GROUP BY date_trunc('week', a.data_avaliacao)
        ORDER BY date_trunc('week', a.data_avaliacao)
    """;

        return jdbc.queryForList(sql);
    }

}