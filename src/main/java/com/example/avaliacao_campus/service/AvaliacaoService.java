package com.example.avaliacao_campus.service;

import com.example.avaliacao_campus.dtos.AvaliacaoRequestDTO;
import com.example.avaliacao_campus.dtos.RespostaDTO;
import com.example.avaliacao_campus.models.Avaliacao;
import com.example.avaliacao_campus.models.AvaliacaoQuestao;
import com.example.avaliacao_campus.repositories.AvaliacaoQuestaoRepository;
import com.example.avaliacao_campus.repositories.AvaliacaoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoQuestaoRepository avaliacaoQuestaoRepository;

    public AvaliacaoService(
            AvaliacaoRepository avaliacaoRepository,
            AvaliacaoQuestaoRepository avaliacaoQuestaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoQuestaoRepository = avaliacaoQuestaoRepository;
    }

    public List<Avaliacao> buscarTodas() {
        return avaliacaoRepository.findAll();
    }

    @Transactional
    public Avaliacao registrarAvaliacao(AvaliacaoRequestDTO request) {

        // Verifica se o usuário já avaliou o local
        var avaliacoesExistentes = avaliacaoRepository.findByUsuario(request.getIdUsuario())
                .stream()
                .filter(a -> a.getIdLocal().equals(request.getIdLocal()))
                .toList();

        if (!avaliacoesExistentes.isEmpty()) {
            throw new IllegalArgumentException("Erro: o usuário já realizou uma avaliação para este local.");
        }

        //  Salvar a avaliação principal
        Avaliacao novaAvaliacao = new Avaliacao();
        novaAvaliacao.setIdUsuario(request.getIdUsuario());
        novaAvaliacao.setIdLocal(request.getIdLocal());
        novaAvaliacao.setDataAvaliacao(LocalDate.now());

        // Método save deve retornar o ID gerado (veja o ajuste abaixo no repository)
        Long idAvaliacaoGerado = avaliacaoRepository.saveAndReturnId(novaAvaliacao);
        novaAvaliacao.setIdAvaliacao(idAvaliacaoGerado);

        // Salvar respostas
        for (RespostaDTO respostaDTO : request.getRespostas()) {
            AvaliacaoQuestao resposta = new AvaliacaoQuestao();
            resposta.setIdAvaliacao(idAvaliacaoGerado);
            resposta.setIdQuestao(respostaDTO.getIdQuestao());
            resposta.setValor(respostaDTO.getValor());
            avaliacaoQuestaoRepository.save(resposta);
        }

        return novaAvaliacao;
    }
}
