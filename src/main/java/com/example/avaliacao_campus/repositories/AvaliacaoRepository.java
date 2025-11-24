package com.example.avaliacao_campus.repositories;

import com.example.avaliacao_campus.models.Avaliacao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class AvaliacaoRepository {

    private final JdbcTemplate jdbc;

    public AvaliacaoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Avaliacao> MAPPER_COM_NOMES = new RowMapper<>() {
        @Override
        public Avaliacao mapRow(ResultSet rs, int rowNum) throws SQLException {
            Avaliacao a = new Avaliacao();
            a.setIdAvaliacao(rs.getLong("id_avaliacao"));
            a.setIdUsuario(rs.getLong("id_usuario"));
            a.setIdLocal(rs.getLong("id_local"));
            a.setDataAvaliacao(rs.getDate("data_avaliacao").toLocalDate());

            try {
                a.setNomeUsuario(rs.getString("usuario_nome"));
                a.setNomeLocal(rs.getString("local_nome"));
                a.setImagemLocal(rs.getString("local_imagem"));
            } catch (SQLException e) {
            }
            return a;
        }
    };


    public List<Avaliacao> findAllWithNames() {
        // O SQL inclui 'l.url_image'
        String sql = """
            SELECT a.*, u.nome AS usuario_nome, l.nome AS local_nome, l.url_image AS local_imagem
            FROM avaliacao a
            JOIN usuario u ON a.id_usuario = u.id_usuario
            JOIN localcampus l ON a.id_local = l.id_local
            ORDER BY a.data_avaliacao DESC
        """;
        return jdbc.query(sql, MAPPER_COM_NOMES);
    }

    public List<Avaliacao> findByNomeLocal(String termo) {
        String sql = """
            SELECT a.*, u.nome AS usuario_nome, l.nome AS local_nome, l.url_image AS local_imagem
            FROM avaliacao a
            JOIN usuario u ON a.id_usuario = u.id_usuario
            JOIN localcampus l ON a.id_local = l.id_local
            WHERE l.nome ILIKE ? 
            ORDER BY a.data_avaliacao DESC
        """;
        return jdbc.query(sql, MAPPER_COM_NOMES, "%" + termo + "%");
    }

    public List<Map<String, Object>> buscarTodasComUsuarioELocal() {
        String sql = """
        SELECT 
            a.id_avaliacao,
            a.data_avaliacao,
            u.nome AS usuario_nome,
            l.nome AS local_nome,
            l.url_image AS local_imagem,
            (SELECT aq.valor 
             FROM avaliacao_questao aq 
             JOIN questao q ON q.id_questao = aq.id_questao 
             WHERE aq.id_avaliacao = a.id_avaliacao 
               AND q.texto ILIKE '%nota geral%' 
             LIMIT 1) AS nota_geral
        FROM avaliacao a
        JOIN usuario u ON u.id_usuario = a.id_usuario
        JOIN localcampus l ON l.id_local = a.id_local
        ORDER BY a.data_avaliacao DESC
        LIMIT 10
        """;
        return jdbc.queryForList(sql);
    }

    // --- MÉTODOS CRUD BÁSICOS ---

    public Long saveAndReturnId(Avaliacao a) {
        String sql = """
            INSERT INTO avaliacao (id_usuario, id_local, data_avaliacao)
            VALUES (?, ?, ?)
            RETURNING id_avaliacao
        """;
        return jdbc.queryForObject(sql, Long.class,
                a.getIdUsuario(), a.getIdLocal(), java.sql.Date.valueOf(a.getDataAvaliacao()));
    }

    public Optional<Avaliacao> findById(Long id) {
        String sql = "SELECT * FROM avaliacao WHERE id_avaliacao = ?";
        return jdbc.query(sql, (rs, rowNum) -> {
            Avaliacao a = new Avaliacao();
            a.setIdAvaliacao(rs.getLong("id_avaliacao"));
            a.setIdUsuario(rs.getLong("id_usuario"));
            a.setIdLocal(rs.getLong("id_local"));
            a.setDataAvaliacao(rs.getDate("data_avaliacao").toLocalDate());
            return a;
        }, id).stream().findFirst();
    }

    public List<Avaliacao> findByUsuario(Long idUsuario) {
        String sql = "SELECT * FROM avaliacao WHERE id_usuario = ?";
        return jdbc.query(sql, (rs, rowNum) -> {
            Avaliacao a = new Avaliacao();
            a.setIdAvaliacao(rs.getLong("id_avaliacao"));
            a.setIdUsuario(rs.getLong("id_usuario"));
            a.setIdLocal(rs.getLong("id_local"));
            a.setDataAvaliacao(rs.getDate("data_avaliacao").toLocalDate());
            return a;
        }, idUsuario);
    }

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
}