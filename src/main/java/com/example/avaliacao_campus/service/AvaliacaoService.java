package com.example.avaliacao_campus.service;

import com.example.avaliacao_campus.dtos.AvaliacaoRequestDTO;
import com.example.avaliacao_campus.dtos.RespostaDTO;
import com.example.avaliacao_campus.models.Avaliacao;
import com.example.avaliacao_campus.models.AvaliacaoQuestao;
import com.example.avaliacao_campus.models.AvaliacaoQuestaoKey;
import com.example.avaliacao_campus.repositories.AvaliacaoQuestaoRepository;
import com.example.avaliacao_campus.repositories.AvaliacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AvaliacaoService {

    private final AvaliacaoRepository avaliacaoRepository;
    private final AvaliacaoQuestaoRepository avaliacaoQuestaoRepository;

    @Autowired
    public AvaliacaoService(
            AvaliacaoRepository avaliacaoRepository,
            AvaliacaoQuestaoRepository avaliacaoQuestaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.avaliacaoQuestaoRepository = avaliacaoQuestaoRepository;
    }

    public List<Avaliacao> buscarTodas() {
        return avaliacaoRepository.findAll();
    }

    /**
     * Lógica Essencial: Registra uma avaliação completa (transação em duas tabelas).
     * @param request O DTO com a avaliação e as respostas.
     * @return A Avaliacao salva.
     */
    public Avaliacao registrarAvaliacao(AvaliacaoRequestDTO request) {

        // Não permitir que o mesmo usuário avalie o mesmo local duas vezes
        List<Avaliacao> avaliacoesExistentes = avaliacaoRepository.findByIdUsuarioAndIdLocal(
                request.getIdUsuario(),
                request.getIdLocal());

        if (!avaliacoesExistentes.isEmpty()) {
            throw new IllegalArgumentException("Erro: O usuário já realizou uma avaliação para este local.");
        }

        // Salvar a entidade AVALIACAO
        Avaliacao novaAvaliacao = new Avaliacao();
        novaAvaliacao.setIdUsuario(request.getIdUsuario());
        novaAvaliacao.setIdLocal(request.getIdLocal());
        novaAvaliacao.setDataAvaliacao(LocalDate.now()); // Registra a data de hoje

        // Salva a avaliação no BD para obter o ID gerado
        Avaliacao avaliacaoSalva = avaliacaoRepository.save(novaAvaliacao);

        // Salvar as Respostas na AVALIACAO_QUESTAO
        Long novoIdAvaliacao = avaliacaoSalva.getIdAvaliacao();

        for (RespostaDTO respostaDTO : request.getRespostas()) {
            AvaliacaoQuestao resposta = new AvaliacaoQuestao();

            // Cria a chave composta
            AvaliacaoQuestaoKey key = new AvaliacaoQuestaoKey(
                    novoIdAvaliacao,
                    respostaDTO.getIdQuestao()
            );

            // Define a chave e o valor
            resposta.setId(key); // Define o objeto de chave composta
            resposta.setValor(respostaDTO.getValor());

            avaliacaoQuestaoRepository.save(resposta);
        }

        return avaliacaoSalva;
    }
}