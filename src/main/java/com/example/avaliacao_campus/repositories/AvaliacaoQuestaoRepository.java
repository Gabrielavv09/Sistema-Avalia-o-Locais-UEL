package com.example.avaliacao_campus.repositories;

import com.example.avaliacao_campus.models.AvaliacaoQuestao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AvaliacaoQuestaoRepository {

    private final JdbcTemplate jdbc;

    public AvaliacaoQuestaoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<AvaliacaoQuestao> MAPPER = new RowMapper<>() {
        @Override
        public AvaliacaoQuestao mapRow(ResultSet rs, int rowNum) throws SQLException {
            AvaliacaoQuestao aq = new AvaliacaoQuestao();
            aq.setIdAvaliacao(rs.getLong("id_avaliacao"));
            aq.setIdQuestao(rs.getLong("id_questao"));
            aq.setValor(rs.getString("valor"));
            return aq;
        }
    };

    public List<AvaliacaoQuestao> findByAvaliacao(Long idAvaliacao) {
        return jdbc.query("SELECT * FROM avaliacao_questao WHERE id_avaliacao=?", MAPPER, idAvaliacao);
    }

    public int save(AvaliacaoQuestao aq) {
        String sql = "INSERT INTO avaliacao_questao (id_avaliacao, id_questao, valor) VALUES (?, ?, ?)";
        return jdbc.update(sql, aq.getIdAvaliacao(), aq.getIdQuestao(), aq.getValor());
    }

    public int delete(Long idAvaliacao, Long idQuestao) {
        String sql = "DELETE FROM avaliacao_questao WHERE id_avaliacao=? AND id_questao=?";
        return jdbc.update(sql, idAvaliacao, idQuestao);
    }
}
