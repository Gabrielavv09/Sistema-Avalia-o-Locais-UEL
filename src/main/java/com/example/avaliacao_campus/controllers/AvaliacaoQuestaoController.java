package com.example.avaliacao_campus.controllers;

import com.example.avaliacao_campus.service.AvaliacaoQuestaoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/respostas")
public class AvaliacaoQuestaoController {

    private final AvaliacaoQuestaoService avaliacaoQuestaoService;

    public AvaliacaoQuestaoController(AvaliacaoQuestaoService avaliacaoQuestaoService) {
        this.avaliacaoQuestaoService = avaliacaoQuestaoService;
    }

    @GetMapping("/avaliacao/{idAvaliacao}")
    public List<Map<String, Object>> getRespostasPorAvaliacao(@PathVariable Long idAvaliacao) {
        return avaliacaoQuestaoService.buscarRespostasPorAvaliacao(idAvaliacao);
    }

    @GetMapping("/usuario/{idUsuario}")
    public List<Map<String, Object>> getRespostasPorUsuario(@PathVariable Long idUsuario) {
        return avaliacaoQuestaoService.buscarRespostasPorUsuario(idUsuario);
    }
}
