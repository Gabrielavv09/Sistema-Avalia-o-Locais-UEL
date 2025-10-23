package com.example.avaliacao_campus.service;

import com.example.avaliacao_campus.models.Usuario;
import com.example.avaliacao_campus.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método para buscar todos os usuários (necessário para o CRUD)
    public List<Usuario> buscarTodos() {
        return usuarioRepository.findAll();
    }

    // Método para buscar um usuário por ID
    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    /**
     * Lógica de Negócio Essencial: Salva um novo usuário após validação.
     * @param usuario O objeto Usuario a ser salvo.
     * @return O objeto Usuario salvo, ou lança exceção se o e-mail já existir.
     */
    public Usuario salvar(Usuario usuario) {
        // Validação de E-mail Único (Requisito de Integridade)
        if (usuario.getEmail() != null && usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Erro: O e-mail " + usuario.getEmail() + " já está cadastrado.");
        }

        // Garante que o tipo seja válido
        String tipo = usuario.getTipo();
        if (tipo == null || (!tipo.equalsIgnoreCase("aluno") && !tipo.equalsIgnoreCase("professor"))) {
            throw new IllegalArgumentException("Erro: O tipo de usuário deve ser 'aluno' ou 'professor'.");
        }

        // Converte para minúsculas para padronização
        usuario.setTipo(tipo.toLowerCase());

        return usuarioRepository.save(usuario);
    }

    // Método para deletar um usuário (completa o CRUD)
    public void deletar(Long id) {
        usuarioRepository.deleteById(id);
    }
}