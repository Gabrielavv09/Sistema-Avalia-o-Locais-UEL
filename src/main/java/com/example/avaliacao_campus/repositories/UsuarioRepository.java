package com.example.avaliacao_campus.repositories;

import com.example.avaliacao_campus.models.Usuario;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {

    private final JdbcTemplate jdbc;

    public UsuarioRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Usuario> MAPPER = new RowMapper<>() {
        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            Usuario u = new Usuario();
            u.setIdUsuario(rs.getLong("id_usuario"));
            u.setNome(rs.getString("nome"));
            u.setEmail(rs.getString("email"));
            u.setTipo(rs.getString("tipo"));
            u.setCursoNome(rs.getString("curso_nome"));
            u.setDepartamento(rs.getString("departamento"));
            return u;
        }
    };

    public List<Usuario> findAll() {
        return jdbc.query("SELECT * FROM usuario", MAPPER);
    }

    public List<Usuario> findByNome(String termo) {
        String sql = "SELECT * FROM usuario WHERE nome ILIKE ?";
        String termoBusca = "%" + termo + "%";
        return jdbc.query(sql, MAPPER, termoBusca);
    }

    public Optional<Usuario> findById(Long id) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        return jdbc.query(sql, MAPPER, id).stream().findFirst();
    }

    public Optional<Usuario> findByEmail(String email) {
        String sql = "SELECT * FROM usuario WHERE email = ?";
        return jdbc.query(sql, MAPPER, email).stream().findFirst();
    }

    public Usuario save(Usuario u) {
        String sql = """
        INSERT INTO usuario (nome, email, tipo, curso_nome, departamento)
        VALUES (?, ?, ?, ?, ?)
    """;
        var keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
        jdbc.update(con -> {
            var ps = con.prepareStatement(sql, new String[]{"id_usuario"});
            ps.setString(1, u.getNome());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getTipo());
            ps.setString(4, u.getCursoNome());
            ps.setString(5, u.getDepartamento());
            return ps;
        }, keyHolder);
        Number key = keyHolder.getKey();
        if (key != null) {
            u.setIdUsuario(key.longValue());
        }
        return u;
    }

    public int update(Usuario u) {
        String sql = """
            UPDATE usuario SET nome=?, email=?, tipo=?, curso_nome=?, departamento=? 
            WHERE id_usuario=?
        """;
        return jdbc.update(sql, u.getNome(), u.getEmail(), u.getTipo(), u.getCursoNome(), u.getDepartamento(), u.getIdUsuario());
    }

    public int delete(Long id) {
        return jdbc.update("DELETE FROM usuario WHERE id_usuario=?", id);
    }
}