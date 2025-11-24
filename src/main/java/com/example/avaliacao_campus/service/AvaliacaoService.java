package com.example.avaliacao_campus.service;

import com.example.avaliacao_campus.dtos.AvaliacaoRequestDTO;
import com.example.avaliacao_campus.dtos.RespostaDTO;
import com.example.avaliacao_campus.models.*;
import com.example.avaliacao_campus.repositories.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoQuestaoRepository avaliacaoQuestaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final QuestaoRepository questaoRepository;
    private final JdbcTemplate jdbc;

    public AvaliacaoService(
            AvaliacaoRepository avaliacaoRepository,
            AvaliacaoQuestaoRepository avaliacaoQuestaoRepository,
            UsuarioRepository usuarioRepository,
            QuestaoRepository questaoRepository,
            JdbcTemplate jdbc) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoQuestaoRepository = avaliacaoQuestaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.questaoRepository = questaoRepository;
        this.jdbc = jdbc;
    }

    public List<Avaliacao> buscarTodas(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return avaliacaoRepository.findAllWithNames();
        } else {
            return avaliacaoRepository.findByNomeLocal(termo);
        }
    }

    public List<Avaliacao> buscarTodas() {
        return buscarTodas(null);
    }

    public Optional<Avaliacao> findById(Long id) {
        return avaliacaoRepository.findById(id);
    }

    // O método que faltava
    public List<Map<String, Object>> buscarTodasComUsuarioELocal() {
        return avaliacaoRepository.buscarTodasComUsuarioELocal();
    }

    @Transactional
    public Avaliacao registrarAvaliacao(AvaliacaoRequestDTO request) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(request.getEmail());
        Usuario usuario;

        if (usuarioExistente.isPresent()) {
            usuario = usuarioExistente.get();
        } else {
            Usuario novo = new Usuario();
            novo.setNome(request.getNome());
            novo.setEmail(request.getEmail());
            novo.setTipo(request.getTipo());
            novo.setCursoNome(request.getCursoNome());
            novo.setDepartamento(request.getDepartamento());
            usuario = usuarioRepository.save(novo);
        }

        var jaAvaliou = avaliacaoRepository.findByUsuario(usuario.getIdUsuario())
                .stream()
                .anyMatch(a -> a.getIdLocal().equals(request.getIdLocal()));

        if (jaAvaliou) {
            throw new IllegalArgumentException("Erro: o usuário já realizou uma avaliação para este local.");
        }

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setIdUsuario(usuario.getIdUsuario());
        avaliacao.setIdLocal(request.getIdLocal());
        avaliacao.setDataAvaliacao(LocalDate.now());

        Long idAvaliacao = avaliacaoRepository.saveAndReturnId(avaliacao);
        avaliacao.setIdAvaliacao(idAvaliacao);

        if (request.getRespostas() != null) {
            for (Map.Entry<Long, String> entry : request.getRespostas().entrySet()) {
                AvaliacaoQuestao resposta = new AvaliacaoQuestao();
                resposta.setIdAvaliacao(idAvaliacao);
                resposta.setIdQuestao(entry.getKey());
                resposta.setValor(entry.getValue());
                avaliacaoQuestaoRepository.save(resposta);
            }
        }

        return avaliacao;
    }
}