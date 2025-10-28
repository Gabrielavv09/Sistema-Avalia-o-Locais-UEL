package com.example.avaliacao_campus.repositories;

import com.example.avaliacao_campus.models.Avaliacao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AvaliacaoRepository {

    private final JdbcTemplate jdbc;

    public AvaliacaoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    // Retorna todas as avaliações
    public List<Avaliacao> findAll() {
        return jdbc.query("SELECT * FROM avaliacao", (rs, rowNum) -> {
            Avaliacao a = new Avaliacao();
            a.setIdAvaliacao(rs.getLong("id_avaliacao"));
            a.setIdUsuario(rs.getLong("id_usuario"));
            a.setIdLocal(rs.getLong("id_local"));
            a.setDataAvaliacao(rs.getDate("data_avaliacao").toLocalDate());
            return a;
        });
    }

    public List<Avaliacao> findByUsuario(Long idUsuario) {
        return jdbc.query("SELECT * FROM avaliacao WHERE id_usuario=?", (rs, rowNum) -> {
            Avaliacao a = new Avaliacao();
            a.setIdAvaliacao(rs.getLong("id_avaliacao"));
            a.setIdUsuario(rs.getLong("id_usuario"));
            a.setIdLocal(rs.getLong("id_local"));
            a.setDataAvaliacao(rs.getDate("data_avaliacao").toLocalDate());
            return a;
        }, idUsuario);
    }

    public List<Avaliacao> findByLocal(Long idLocal) {
        return jdbc.query("SELECT * FROM avaliacao WHERE id_local=?", (rs, rowNum) -> {
            Avaliacao a = new Avaliacao();
            a.setIdAvaliacao(rs.getLong("id_avaliacao"));
            a.setIdUsuario(rs.getLong("id_usuario"));
            a.setIdLocal(rs.getLong("id_local"));
            a.setDataAvaliacao(rs.getDate("data_avaliacao").toLocalDate());
            return a;
        }, idLocal);
    }


    public Long saveAndReturnId(Avaliacao a) {
        String sql = """
            INSERT INTO avaliacao (id_usuario, id_local, data_avaliacao)
            VALUES (?, ?, ?)
            RETURNING id_avaliacao
        """;

        return jdbc.queryForObject(sql, Long.class,
                a.getIdUsuario(), a.getIdLocal(), java.sql.Date.valueOf(a.getDataAvaliacao()));
    }

    public int delete(Long id) {
        return jdbc.update("DELETE FROM avaliacao WHERE id_avaliacao=?", id);
    }

    public List<Map<String, Object>> buscarTodasComUsuarioELocal() {
        String sql = """
        SELECT 
            a.id_avaliacao,
            a.data_avaliacao,
            u.nome AS usuario_nome,
            l.nome AS local_nome,
            l.url_image AS local_imagem,
            COALESCE(
                (SELECT aq.valor::numeric
                 FROM avaliacao_questao aq
                 JOIN questao q ON q.id_questao = aq.id_questao
                 WHERE aq.id_avaliacao = a.id_avaliacao
                   AND q.texto ILIKE '%%qual a sua nota geral%%'
                 LIMIT 1), 
                NULL
            ) AS nota_geral
        FROM avaliacao a
        JOIN usuario u ON u.id_usuario = a.id_usuario
        JOIN localcampus l ON l.id_local = a.id_local
        ORDER BY a.data_avaliacao DESC
        LIMIT 10
    """;

        return jdbc.queryForList(sql);
    }

}
