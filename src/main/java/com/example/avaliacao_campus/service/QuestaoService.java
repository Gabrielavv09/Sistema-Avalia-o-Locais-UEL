package com.example.avaliacao_campus.service;

import com.example.avaliacao_campus.models.Questao;
import com.example.avaliacao_campus.repositories.QuestaoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuestaoService {

    private final QuestaoRepository questaoRepository;

    public QuestaoService(QuestaoRepository questaoRepository) {
        this.questaoRepository = questaoRepository;
    }

    public List<Questao> buscarTodos(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return questaoRepository.findAll();
        } else {
            return questaoRepository.findByTextoLike(termo);
        }
    }

    public List<Questao> buscarTodos() {
        return buscarTodos(null);
    }

    public Optional<Questao> buscarPorId(Long id) {
        return questaoRepository.findById(id);
    }

    public List<Questao> buscarPorTipo(String tipo) {
        if (tipo == null || (!tipo.equalsIgnoreCase("padrao") && !tipo.equalsIgnoreCase("multipla"))) {
            throw new IllegalArgumentException("Erro: O tipo de questão deve ser 'padrao' ou 'multipla'.");
        }
        return questaoRepository.findByTipo(tipo.toLowerCase());
    }

    public Questao salvar(Questao questao) {
        String tipo = questao.getTipo();
        if (tipo == null || (!tipo.equalsIgnoreCase("padrao") && !tipo.equalsIgnoreCase("multipla"))) {
            throw new IllegalArgumentException("Erro: O tipo de questão deve ser 'padrao' ou 'multipla'.");
        }
        questao.setTipo(tipo.toLowerCase());

        if ("padrao".equalsIgnoreCase(questao.getTipo())) {
            questao.setIdUsuarioCriador(null);
            questao.setOpcoes(null);
        }

        if (questao.getIdQuestao() == null) {
            questaoRepository.save(questao);
        } else {
            questaoRepository.update(questao);
        }

        return questao;
    }

    public void deletar(Long id) {
        questaoRepository.delete(id);
    }
}