package com.example.avaliacao_campus.service;

import com.example.avaliacao_campus.repositories.AvaliacaoQuestaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AvaliacaoQuestaoService {

    private final AvaliacaoQuestaoRepository avaliacaoQuestaoRepository;

    public AvaliacaoQuestaoService(AvaliacaoQuestaoRepository avaliacaoQuestaoRepository) {
        this.avaliacaoQuestaoRepository = avaliacaoQuestaoRepository;
    }

    public List<Map<String, Object>> buscarRespostasPorAvaliacao(Long idAvaliacao) {
        return avaliacaoQuestaoRepository.buscarRespostasPorAvaliacao(idAvaliacao);
    }

    public List<Map<String, Object>> buscarRespostasPorUsuario(Long idUsuario) {
        return avaliacaoQuestaoRepository.buscarRespostasPorUsuario(idUsuario);
    }


}
