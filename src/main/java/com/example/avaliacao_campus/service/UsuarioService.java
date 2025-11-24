package com.example.avaliacao_campus.service;

import com.example.avaliacao_campus.models.Usuario;
import com.example.avaliacao_campus.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> buscarTodos(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return usuarioRepository.findAll();
        } else {
            return usuarioRepository.findByNome(termo);
        }
    }

    public List<Usuario> buscarTodos() {
        return buscarTodos(null);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario salvar(Usuario usuario) {
        Optional<Usuario> existente = usuarioRepository.findByEmail(usuario.getEmail());
        if (existente.isPresent() &&
                (usuario.getIdUsuario() == null || !existente.get().getIdUsuario().equals(usuario.getIdUsuario()))) {
            throw new IllegalArgumentException("Erro: O e-mail " + usuario.getEmail() + " já está cadastrado.");
        }
        String tipo = usuario.getTipo();
        if (tipo == null || (!tipo.equalsIgnoreCase("aluno") && !tipo.equalsIgnoreCase("professor"))) {
            throw new IllegalArgumentException("Erro: O tipo de usuário deve ser 'aluno' ou 'professor'.");
        }
        usuario.setTipo(tipo.toLowerCase());
        if (usuario.getIdUsuario() == null) {
            usuarioRepository.save(usuario);
        } else {
            usuarioRepository.update(usuario);
        }
        return usuario;
    }

    public void deletar(Long id) {
        usuarioRepository.delete(id);
    }
}
