package com.example.avaliacao_campus.controllers;

import com.example.avaliacao_campus.models.Avaliacao;
import com.example.avaliacao_campus.service.AvaliacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {

    private final AvaliacaoService avaliacaoService;

    @Autowired
    public IndexController(AvaliacaoService avaliacaoService) {
        this.avaliacaoService = avaliacaoService;
    }

    @GetMapping
    public String index(@RequestParam(value = "termo", required = false) String termo, Model model) {

        List<Avaliacao> avaliacoes = avaliacaoService.buscarTodas(termo);

        model.addAttribute("avaliacoes", avaliacoes);
        model.addAttribute("termo", termo);

        return "index";
    }
}
