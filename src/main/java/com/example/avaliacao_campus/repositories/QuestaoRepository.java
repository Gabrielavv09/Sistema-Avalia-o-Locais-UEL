package com.example.avaliacao_campus.repositories;

import com.example.avaliacao_campus.models.Questao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class QuestaoRepository {

    private final JdbcTemplate jdbc;

    public QuestaoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Questao> MAPPER = new RowMapper<>() {
        @Override
        public Questao mapRow(ResultSet rs, int rowNum) throws SQLException {
            Questao q = new Questao();
            q.setIdQuestao(rs.getLong("id_questao"));
            q.setTexto(rs.getString("texto"));
            q.setTipo(rs.getString("tipo"));
            q.setIdUsuarioCriador(rs.getLong("id_usuario_criador"));
            return q;
        }
    };

    public List<Questao> findAll() {
        return jdbc.query("SELECT * FROM questao", MAPPER);
    }

    public Optional<Questao> findById(Long id) {
        return jdbc.query("SELECT * FROM questao WHERE id_questao=?", MAPPER, id).stream().findFirst();
    }

    public List<Questao> findByTipo(String tipo) {
        return jdbc.query("SELECT * FROM questao WHERE tipo=?", MAPPER, tipo);
    }

    public List<Questao> findByUsuarioCriador(Long idUsuario) {
        return jdbc.query("SELECT * FROM questao WHERE id_usuario_criador=?", MAPPER, idUsuario);
    }

    public int save(Questao q) {
        String sql = "INSERT INTO questao (texto, tipo, id_usuario_criador) VALUES (?, ?, ?)";
        return jdbc.update(sql, q.getTexto(), q.getTipo(), q.getIdUsuarioCriador());
    }

    public int update(Questao q) {
        String sql = "UPDATE questao SET texto=?, tipo=?, id_usuario_criador=? WHERE id_questao=?";
        return jdbc.update(sql, q.getTexto(), q.getTipo(), q.getIdUsuarioCriador(), q.getIdQuestao());
    }

    public int delete(Long id) {
        return jdbc.update("DELETE FROM questao WHERE id_questao=?", id);
    }
}
