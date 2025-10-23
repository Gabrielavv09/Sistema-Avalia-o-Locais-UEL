package com.example.avaliacao_campus.service;

import com.example.avaliacao_campus.models.Questao;
import com.example.avaliacao_campus.repositories.QuestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class QuestaoService {

    private final QuestaoRepository questaoRepository;

    @Autowired
    public QuestaoService(QuestaoRepository questaoRepository) {
        this.questaoRepository = questaoRepository;
    }

    public List<Questao> buscarTodos() {
        return questaoRepository.findAll();
    }

    public Optional<Questao> buscarPorId(Long id) {
        return questaoRepository.findById(id);
    }

    // Método de serviço para listar questões por tipo ('padrão' ou 'personalizada')
    public List<Questao> buscarPorTipo(String tipo) {
        // Garante que o tipo seja válido antes de buscar
        if (tipo == null || (!tipo.equalsIgnoreCase("padrao") && !tipo.equalsIgnoreCase("personalizada"))) {
            throw new IllegalArgumentException("Erro: O tipo de questão deve ser 'padrao' ou 'personalizada'.");
        }
        return questaoRepository.findByTipo(tipo.toLowerCase());
    }

    /**
     * Salva uma nova questão e aplica a lógica de negócio.
     */
    public Questao salvar(Questao questao) {
        String tipo = questao.getTipo();
        if (tipo == null || (!tipo.equalsIgnoreCase("padrao") && !tipo.equalsIgnoreCase("personalizada"))) {
            throw new IllegalArgumentException("Erro: O tipo de questão deve ser 'padrao' ou 'personalizada'.");
        }
        questao.setTipo(tipo.toLowerCase());

        if ("padrao".equalsIgnoreCase(questao.getTipo())) {
            questao.setIdUsuarioCriador(null);
        } else {
            if (questao.getIdUsuarioCriador() == null) {
                throw new IllegalArgumentException("Erro: Questão personalizada deve ter um ID de usuário criador.");
            }
        }

        return questaoRepository.save(questao);
    }

    public void deletar(Long id) {
        questaoRepository.deleteById(id);
    }
}