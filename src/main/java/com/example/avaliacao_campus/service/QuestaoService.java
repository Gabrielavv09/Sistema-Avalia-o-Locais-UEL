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

    public List<Questao> buscarTodos() {
        return questaoRepository.findAll();
    }

    public Optional<Questao> buscarPorId(Long id) {
        return questaoRepository.findById(id);
    }

    public List<Questao> buscarPorTipo(String tipo) {
        if (tipo == null || (!tipo.equalsIgnoreCase("padrao") && !tipo.equalsIgnoreCase("personalizada"))) {
            throw new IllegalArgumentException("Erro: O tipo de questão deve ser 'padrao' ou 'personalizada'.");
        }
        return questaoRepository.findByTipo(tipo.toLowerCase());
    }

    public Questao salvar(Questao questao) {
        // Validação do tipo
        String tipo = questao.getTipo();
        if (tipo == null || (!tipo.equalsIgnoreCase("padrao") && !tipo.equalsIgnoreCase("personalizada"))) {
            throw new IllegalArgumentException("Erro: O tipo de questão deve ser 'padrao' ou 'personalizada'.");
        }
        questao.setTipo(tipo.toLowerCase());

        // Validação de criador conforme o tipo
        if ("padrao".equalsIgnoreCase(questao.getTipo())) {
            questao.setIdUsuarioCriador(null);
        } else if (questao.getIdUsuarioCriador() == null) {
            throw new IllegalArgumentException("Erro: Questão personalizada deve ter um ID de usuário criador.");
        }

        // Decide entre inserir e atualizar
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
