package com.example.avaliacao_campus.service;

import com.example.avaliacao_campus.dtos.AvaliacaoRequestDTO;
import com.example.avaliacao_campus.dtos.NovaQuestaoDTO;
import com.example.avaliacao_campus.models.*;
import com.example.avaliacao_campus.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoQuestaoRepository avaliacaoQuestaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final QuestaoRepository questaoRepository;

    public AvaliacaoService(
            AvaliacaoRepository avaliacaoRepository,
            AvaliacaoQuestaoRepository avaliacaoQuestaoRepository,
            UsuarioRepository usuarioRepository,
            QuestaoRepository questaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoQuestaoRepository = avaliacaoQuestaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.questaoRepository = questaoRepository;
    }

    public java.util.List<Avaliacao> buscarTodas() {
        return avaliacaoRepository.findAll();
    }

    @Transactional
    public Avaliacao registrarAvaliacao(AvaliacaoRequestDTO request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseGet(() -> {
                    Usuario novo = new Usuario();
                    novo.setNome(request.getNome());
                    novo.setEmail(request.getEmail());
                    novo.setTipo(request.getTipo());
                    novo.setCursoNome(request.getCursoNome());
                    novo.setDepartamento(request.getDepartamento());
                    return usuarioRepository.save(novo);
                });

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

        if (request.getNovasQuestoes() != null) {
            for (NovaQuestaoDTO nova : request.getNovasQuestoes()) {
                Questao questao = new Questao();
                questao.setTexto(nova.getTexto());
                questao.setTipo(nova.getTipo());
                questao.setIdUsuarioCriador(usuario.getIdUsuario());
                questaoRepository.save(questao);
            }
        }

        return avaliacao;
    }
}
