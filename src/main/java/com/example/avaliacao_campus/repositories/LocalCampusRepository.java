package com.example.avaliacao_campus.repositories;

import com.example.avaliacao_campus.models.LocalCampus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class LocalCampusRepository {

    private final JdbcTemplate jdbc;

    public LocalCampusRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<LocalCampus> MAPPER = new RowMapper<>() {
        @Override
        public LocalCampus mapRow(ResultSet rs, int rowNum) throws SQLException {
            LocalCampus l = new LocalCampus();
            l.setIdLocal(rs.getLong("id_local"));
            l.setNome(rs.getString("nome"));
            l.setDescricao(rs.getString("descricao"));
            l.setLocalizacao(rs.getString("localizacao"));
            l.setUrlImage(rs.getString("url_image"));
            return l;
        }
    };

    public List<LocalCampus> findAll() {
        return jdbc.query("SELECT * FROM localcampus ORDER BY nome ASC", MAPPER);
    }

    public List<LocalCampus> findByNomeLike(String termo) {
        String sql = "SELECT * FROM localcampus WHERE nome ILIKE ? ORDER BY nome ASC";
        String termoBusca = "%" + termo + "%";
        return jdbc.query(sql, MAPPER, termoBusca);
    }

    public Optional<LocalCampus> findById(Long id) {
        return jdbc.query("SELECT * FROM localcampus WHERE id_local=?", MAPPER, id).stream().findFirst();
    }

    public Optional<LocalCampus> findByNome(String nome) {
        return jdbc.query("SELECT * FROM localcampus WHERE nome=?", MAPPER, nome).stream().findFirst();
    }

    // --- CORREÇÃO: Método save agora usa KeyHolder para garantir o salvamento e retorno do ID ---
    public LocalCampus save(LocalCampus l) {
        String sql = "INSERT INTO localcampus (nome, descricao, localizacao, url_image) VALUES (?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbc.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id_local"});
            ps.setString(1, l.getNome());
            ps.setString(2, l.getDescricao());
            ps.setString(3, l.getLocalizacao());
            ps.setString(4, l.getUrlImage());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            l.setIdLocal(key.longValue());
        }

        return l;
    }

    public int update(LocalCampus l) {
        String sql = "UPDATE localcampus SET nome=?, descricao=?, localizacao=?, url_image=? WHERE id_local=?";
        return jdbc.update(sql, l.getNome(), l.getDescricao(), l.getLocalizacao(), l.getUrlImage(), l.getIdLocal());
    }

    public int delete(Long id) {
        return jdbc.update("DELETE FROM localcampus WHERE id_local=?", id);
    }
}