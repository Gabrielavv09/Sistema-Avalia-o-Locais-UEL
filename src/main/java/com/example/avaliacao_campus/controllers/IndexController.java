package com.example.avaliacao_campus.controllers;

import com.example.avaliacao_campus.models.Avaliacao;
import com.example.avaliacao_campus.service.AvaliacaoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    private final AvaliacaoService avaliacaoService;

    public IndexController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Map<String, Object>> avaliacoes = avaliacaoService.buscarTodasComUsuarioELocal();
        model.addAttribute("avaliacoes", avaliacoes);
        return "index";
    }

}
