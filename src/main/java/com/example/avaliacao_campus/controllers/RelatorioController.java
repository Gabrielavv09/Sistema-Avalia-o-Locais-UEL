package com.example.avaliacao_campus.controllers;

import com.example.avaliacao_campus.repositories.AvaliacaoQuestaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/relatorios")
public class RelatorioController {

    @Autowired
    private AvaliacaoQuestaoRepository repo;

    @GetMapping
    public String exibirRelatorios(Model model) {
        List<Map<String, Object>> mediasLocais = repo.getMediaPorLocal();
        List<Map<String, Object>> mediasUsuarios = repo.getMediaPorTipoUsuario();

        model.addAttribute("mediasLocais", mediasLocais);
        model.addAttribute("mediasUsuarios", mediasUsuarios);

        return "relatorios/index";
    }
}
